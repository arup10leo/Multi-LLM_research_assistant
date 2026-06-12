package com.research.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;

    public String retrieveContext(String query) {

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(1)
                        .build());

        return documents.stream()
                .map(doc -> doc.getText())
                .collect(Collectors.joining("\n"));
    }
}