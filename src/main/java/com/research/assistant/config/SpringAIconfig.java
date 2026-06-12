package com.research.assistant.config;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.ai.chat.memory.MessageWindowChatMemory;

import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;

import org.springframework.ai.tool.ToolCallbackProvider;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import com.research.assistant.service.AdvancedRAGService;

@Configuration
public class SpringAIconfig {
        // Chat client configuration
        @Bean
        public ChatClient chatClient(
                        ChatClient.Builder builder,
                        ChatMemory chatMemory,
                        ToolCallbackProvider toolCallbackProvider,
                        AdvancedRAGService advancedRAGService) {

                var memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
                var retrievalAdvisor = advancedRAGService.getRetrievalAugmentationAdvisor();

                return builder
                                .defaultAdvisors(memoryAdvisor, retrievalAdvisor)
                                .defaultToolCallbacks(toolCallbackProvider)
                                .build();
        }

        // Chat memory backed by JDBC, keeping a small recent-message window
        @Bean
        public ChatMemory chatMemory(JdbcChatMemoryRepository repository) {
                return MessageWindowChatMemory.builder()
                                .chatMemoryRepository(repository)
                                .maxMessages(5)
                                .build();
        }
}