package com.research.assistant.controller;

import com.research.assistant.DTOcontracts.ResearchRequest;

import com.research.assistant.model.ResearchContext;

import com.research.assistant.service.ResearchService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/research")
public class ResearchController {

        private final ResearchService researchService;

        // =========================================
        // CONSTRUCTOR INJECTION
        // =========================================

        public ResearchController(
                        ResearchService researchService) {
                this.researchService = researchService;
        }

        // =========================================
        // REST ENDPOINT
        // =========================================

        @PostMapping("/process")
        public ResponseEntity<String> processResearch(

                        @RequestBody ResearchRequest request

        ) {

                // =====================================
                // DTO → INTERNAL DOMAIN MODEL
                // =====================================

                ResearchContext context = new ResearchContext();

                context.setContent(
                                request.getContent());

                context.setOperation(
                                request.getOperations());

                context.setConversationId(
                                request.getConversationId());

                context.setSource("REST");

                context.setCitationRequired(false);

                // =====================================
                // MAIN ORCHESTRATION PIPELINE
                // =====================================

                String response = researchService.processContent(context);

                return ResponseEntity.ok(response);
        }
}