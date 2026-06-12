package com.research.assistant.config;

import com.research.assistant.tools.CitationMcpTool;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

        @Bean
        public ToolCallbackProvider toolCallbackProvider(
                        CitationMcpTool citationMcpTool) {

                return MethodToolCallbackProvider
                                .builder()
                                .toolObjects(
                                                citationMcpTool)
                                .build();
        }
}