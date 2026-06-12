package com.research.assistant.service;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;

import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;

import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;

import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvancedRAGService {
        private final ChatClient.Builder chatClientBuilder;
        private final VectorStore vectorStore;

        /**
         * Build a configured {@link RetrievalAugmentationAdvisor} used to
         * enhance conversational retrieval with query rewriting, expansion,
         * vector-based document retrieval and contextual augmentation.
         *
         * Breaking the builder chains into local variables improves readability
         * and makes the configuration easier to adjust.
         */
        public RetrievalAugmentationAdvisor getRetrievalAugmentationAdvisor() {

                var queryTransformer = RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClientBuilder)
                                .build();

                var queryExpander = MultiQueryExpander.builder()
                                .chatClientBuilder(chatClientBuilder)
                                .numberOfQueries(3)
                                .build();

                var documentRetriever = VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .topK(5)
                                .similarityThreshold(0.7)
                                .build();

                var queryAugmenter = ContextualQueryAugmenter.builder()
                                .allowEmptyContext(true)
                                .build();

                return RetrievalAugmentationAdvisor.builder()
                                .queryTransformers(queryTransformer)
                                .queryExpander(queryExpander)
                                .documentRetriever(documentRetriever)
                                .queryAugmenter(queryAugmenter)
                                .build();
        }
}