package com.research.assistant.model;

public class ResearchContext {

    private String content;

    private String operation;

    private String conversationId;

    private String source;

    private boolean citationRequired;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isCitationRequired() {
        return citationRequired;
    }

    public void setCitationRequired(boolean citationRequired) {
        this.citationRequired = citationRequired;
    }
}