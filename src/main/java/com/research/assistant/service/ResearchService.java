package com.research.assistant.service;

import java.net.URI;
import java.util.Map;
import com.research.assistant.model.ResearchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.DTOcontracts.GeminiResponse;

@Service
public class ResearchService {

  private final RagService ragService;
  private final ChatClient chatClient;
  private static final Logger logger = LoggerFactory.getLogger(ResearchService.class);
  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final String geminiApiUrl;
  private final String geminiApiKey;

  public ResearchService(WebClient webClient,
      ObjectMapper objectMapper,
      @Value("${gemini.api.url:${GEMINI_API_URL:}}") String geminiApiUrl,
      @Value("${gemini.api.key:${GEMINI_API_KEY:${GEMINI_KEY:}}}") String geminiApiKey,
      ChatClient chatClient,
      RagService ragService,
      AdvancedRAGService advancedRAGService) {

    this.webClient = webClient;
    this.objectMapper = objectMapper;
    this.geminiApiUrl = geminiApiUrl;
    this.chatClient = chatClient;
    this.ragService = ragService;

    this.geminiApiKey = (geminiApiKey != null && !geminiApiKey.isBlank()) ? geminiApiKey.trim() : null;

    logger.info("ResearchService initialized");
    if (this.geminiApiUrl != null && !this.geminiApiUrl.isEmpty()) {
      logger.info("Gemini API URL configured");
    }
    if (this.geminiApiKey != null && !this.geminiApiKey.isEmpty()) {
      logger.info("Gemini API key configured");
    }
  }

  public String processContent(ResearchContext context) {
    long totalStart = System.nanoTime();

    logger.info("Processing request - Operation: {}, Content length: {}",
        context.getOperation(),
        context.getContent() == null ? 0 : context.getContent().length());

    try {
      String basePrompt = buildPrompt(context);
      String ragContext = ragService.retrieveContext(context.getContent());

      String prompt = """
          You are an AI research assistant.

          Retrieved Knowledge:
          %s

          User Task:
          %s
          """.formatted(ragContext, basePrompt);

      logger.info("Prompt prepared - Size: {} chars", prompt.length());

      if (geminiApiKey == null || geminiApiKey.isBlank()) {
        logger.error("Gemini API key missing");
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Gemini API key missing");
      }

      Map<String, Object> requestBody = Map.of(
          "contents",
          new Object[] {
              Map.of(
                  "parts",
                  new Object[] {
                      Map.of("text", prompt)
                  })
          });

      boolean useBearerAuth = geminiApiKey.toLowerCase().startsWith("bearer ");
      URI uri = URI.create(geminiApiUrl);

      if (!useBearerAuth) {
        uri = UriComponentsBuilder
            .fromUri(uri)
            .queryParam("key", geminiApiKey)
            .build(true)
            .toUri();
      }

      logger.info("Sending request to Gemini - Auth mode: {}",
          useBearerAuth ? "BEARER" : "API_KEY");

      WebClient.RequestBodySpec spec = webClient.post()
          .uri(uri)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

      if (useBearerAuth) {
        spec = spec.header(HttpHeaders.AUTHORIZATION, geminiApiKey);
      }

      long geminiStart = System.nanoTime();

      String response = spec
          .bodyValue(requestBody)
          .retrieve()
          .onStatus(
              status -> status.value() == 429,
              clientResponse -> clientResponse.bodyToMono(String.class)
                  .flatMap(body -> {
                    logger.error("Gemini rate limited - Status: {}",
                        clientResponse.statusCode());
                    return reactor.core.publisher.Mono
                        .error(new RuntimeException("Gemini quota exceeded"));
                  }))
          .onStatus(
              status -> status.is5xxServerError(),
              clientResponse -> clientResponse.bodyToMono(String.class)
                  .flatMap(body -> {
                    logger.error("Gemini server error - Status: {}, Body: {}",
                        clientResponse.statusCode(), body);
                    return reactor.core.publisher.Mono
                        .error(new RuntimeException("Gemini server error"));
                  }))
          .bodyToMono(String.class)
          .block(java.time.Duration.ofSeconds(90));

      logger.info("Gemini response received - Duration: {} ms, Size: {} chars",
          (System.nanoTime() - geminiStart) / 1_000_000,
          response == null ? 0 : response.length());

      if (response == null || response.isBlank()) {
        logger.error("Empty Gemini response");
        throw new RuntimeException("Empty Gemini response");
      }

      String geminiContent = extractRelevantInfo(response);

      String finalPrompt = """
          You are an enterprise AI research assistant.

          INITIAL GEMINI RESPONSE:
          %s

          USER QUERY:
          %s
          """.formatted(geminiContent, context.getContent());

      var promptSpec = chatClient.prompt()
          .user(finalPrompt);

      if (context.getConversationId() != null
          && !context.getConversationId().isBlank()) {
        promptSpec = promptSpec.advisors(
            spec2 -> spec2.param(
                ChatMemory.CONVERSATION_ID,
                context.getConversationId()));
      }

      long llmStart = System.nanoTime();
      String result = promptSpec.call().content();

      logger.info("Processing complete - Total time: {} ms",
          (System.nanoTime() - totalStart) / 1_000_000);

      return result;

    } catch (Exception e) {
      logger.error("Error processing research request - Operation: {}, Message: {}",
          context.getOperation(), e.getMessage(), e);
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Error processing research request: " + e.getMessage(),
          e);
    }
  }

  private String extractRelevantInfo(String response) {
    try {
      GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);

      if (geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()) {
        var candidate = geminiResponse.getCandidates().get(0);
        if (candidate.getContent() != null &&
            candidate.getContent().getParts() != null &&
            !candidate.getContent().getParts().isEmpty()) {
          return candidate.getContent().getParts().get(0).getText();
        }
      }

      logger.warn("No valid content found in Gemini response");
      return "No content found";

    } catch (Exception e) {
      logger.error("Failed to parse Gemini response", e);
      return "Error processing response";
    }
  }

  private String buildPrompt(ResearchContext context) {

    if (context.getOperation() == null || context.getOperation().isEmpty()) {
      logger.error("Operation is null/empty");
      throw new IllegalArgumentException("Operation cannot be null or empty");
    }

    if (context.getContent() == null || context.getContent().isEmpty()) {
      logger.error("Content is null/empty");
      throw new IllegalArgumentException("Content cannot be null or empty");
    }

    StringBuilder promptBuilder = new StringBuilder();

    switch (context.getOperation()) {
      case "summarize":
        promptBuilder.append("Summarize the following content: ");
        break;
      case "analyze":
        promptBuilder.append("Analyze the following content: ");
        break;
      case "suggest":
        promptBuilder.append("Suggest related topic links for the following content: ");
        break;
      default:
        logger.error("Unsupported operation: {}", context.getOperation());
        throw new IllegalArgumentException("Unsupported operation: " + context.getOperation());
    }

    promptBuilder.append(context.getContent());
    return promptBuilder.toString();
  }
}
