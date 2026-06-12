package com.research.assistant.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class DocumentLoaderService {

    private final VectorStore vectorStore;

    @PostConstruct
    public void loadDocuments() {

        try {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            Resource[] resources = resolver.getResources("classpath*:docs/*");

            for (Resource resource : resources) {

                String content = new String(
                        resource.getInputStream().readAllBytes(),
                        StandardCharsets.UTF_8);

                // Skip empty files
                if (content.isBlank()) {
                    continue;
                }

                // Metadata
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("filename", resource.getFilename());

                Document document = new Document(content, metadata);

                vectorStore.add(List.of(document));

                System.out.println(
                        "Loaded document: " + resource.getFilename());
            }

            System.out.println("All documents loaded into vector store.");

        } catch (Exception e) {

            System.err.println("Failed loading documents.");

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}