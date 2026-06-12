package com.research.assistant.tools;

import java.util.UUID;

import com.research.assistant.model.ResearchContext;
import com.research.assistant.service.ResearchService;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class ResearchMcpTools {

    private final ResearchService researchService;

    public ResearchMcpTools(ResearchService researchService) {
        this.researchService = researchService;
    }

    // =====================================================
    // SHARED CONTEXT BUILDER
    // =====================================================

    private ResearchContext createContext(

            String content,

            String operation,

            String conversationId,

            Boolean citationRequired

    ) {

        ResearchContext context = new ResearchContext();

        // -----------------------------------------
        // CONTENT
        // -----------------------------------------

        context.setContent(content);

        // -----------------------------------------
        // OPERATION
        // -----------------------------------------

        context.setOperation(operation);

        // -----------------------------------------
        // CONVERSATION ID
        // -----------------------------------------

        String finalConversationId;

        if (conversationId != null
                && !conversationId.isBlank()) {

            finalConversationId = conversationId;

        } else {

            finalConversationId = "mcp-" + UUID.randomUUID();
        }

        context.setConversationId(finalConversationId);

        // -----------------------------------------
        // SOURCE
        // -----------------------------------------

        context.setSource("MCP");

        // -----------------------------------------
        // CITATION FLAG
        // -----------------------------------------

        context.setCitationRequired(

                citationRequired != null
                        ? citationRequired
                        : false);

        return context;
    }

    // =====================================================
    // SUMMARIZE TOOL
    // =====================================================

    @Tool(description = """
            Summarize selected webpage,
            documentation,
            article,
            enterprise data,
            or research content.
            """)
    public String summarizeContent(

            String content,

            String conversationId,

            Boolean citationRequired

    ) {

        ResearchContext context = createContext(

                content,

                "summarize",

                conversationId,

                citationRequired);

        return researchService.processContent(context);
    }

    // =====================================================
    // ANALYZE TOOL
    // =====================================================

    @Tool(description = """
            Analyze selected technical,
            enterprise,
            AI,
            software engineering,
            or research content.
            """)
    public String analyzeContent(

            String content,

            String conversationId,

            Boolean citationRequired

    ) {

        ResearchContext context = createContext(

                content,

                "analyze",

                conversationId,

                citationRequired);

        return researchService.processContent(context);
    }

    // =====================================================
    // SUGGEST TOOL
    // =====================================================

    @Tool(description = """
            Suggest related research topics,
            frameworks,
            learning paths,
            technologies,
            references,
            and engineering concepts.
            """)
    public String suggestTopics(

            String content,

            String conversationId,

            Boolean citationRequired

    ) {

        ResearchContext context = createContext(

                content,

                "suggest",

                conversationId,

                citationRequired);

        return researchService.processContent(context);
    }
}