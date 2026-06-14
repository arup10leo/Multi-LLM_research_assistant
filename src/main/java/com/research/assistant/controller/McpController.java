package com.research.assistant.controller;

import com.research.assistant.DTOcontracts.ResearchRequest;
import com.research.assistant.tools.ResearchMcpTools;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mcp")
public class McpController {

    private final ResearchMcpTools mcpTools;

    public McpController(ResearchMcpTools mcpTools) {
        this.mcpTools = mcpTools;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processMcp(@RequestBody ResearchRequest request) {

        String content = request.getContent();

        String operation = request.getOperations();

        String conversationId = request.getConversationId();

        // If client didn't provide an explicit operation, do a tiny keyword-based
        // detection
        if (operation == null || operation.isBlank()) {
            String sample = content == null ? "" : content.toLowerCase();

            if (sample.contains("summar") || sample.contains("summary") || sample.contains("brief")) {
                operation = "summarize";
            } else if (sample.contains("suggest") || sample.contains("recommend") || sample.contains("related")) {
                operation = "suggest";
            } else {
                // default to analyze for general prompts
                operation = "analyze";
            }
        }

        String result;

        switch (operation.toLowerCase()) {
            case "summarize", "summary", "sum" ->
                result = mcpTools.summarizeContent(content, conversationId, null);
            case "suggest", "recommend", "suggestions" ->
                result = mcpTools.suggestTopics(content, conversationId, null);
            default ->
                result = mcpTools.analyzeContent(content, conversationId, null);
        }

        return ResponseEntity.ok(result);
    }
}
