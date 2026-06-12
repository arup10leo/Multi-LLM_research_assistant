package com.research.assistant.DTOcontracts;

import lombok.Data;

@Data
public class ResearchRequest {

    // Define fields for the research request we need from the curl json
    public String content;
    public String operations;
    public String conversationId;

}
