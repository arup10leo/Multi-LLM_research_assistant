package com.research.assistant.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.tool.annotation.Tool;

import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CitationMcpTool {

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    @Tool(description = "Generate IEEE academic citations using CrossRef API for research papers and technical topics")
    public String generateCitations(String topic) {

        try {

            String response = webClientBuilder
                    .build()
                    .get()
                    .uri(
                            "https://api.crossref.org/works?query="
                                    + topic
                                    + "&rows=5")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(response);

            JsonNode items = root.path("message")
                    .path("items");

            StringBuilder citations = new StringBuilder();

            citations.append("""
                    IEEE References:

                    """);

            int count = 1;

            for (JsonNode item : items) {

                String title = item.path("title").size() > 0
                        ? item.path("title")
                                .get(0)
                                .asText()
                        : "Unknown Title";

                String journal = item.path("container-title").size() > 0
                        ? item.path("container-title")
                                .get(0)
                                .asText()
                        : "Unknown Journal";

                String year = "Unknown Year";

                if (item.has("published-print")) {

                    year = item.path("published-print")
                            .path("date-parts")
                            .get(0)
                            .get(0)
                            .asText();
                }

                String doi = item.path("DOI")
                        .asText("DOI Not Available");

                citations.append(
                        "[" + count + "] "
                                + "\"" + title + "\", "
                                + journal + ", "
                                + year + ". "
                                + "DOI: "
                                + doi
                                + "\n\n");

                count++;
            }

            return citations.toString();

        } catch (Exception e) {

            return "Error generating citations: "
                    + e.getMessage();
        }
    }
}