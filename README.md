<img width="1536" height="800" alt="1000449964" src="https://github.com/user-attachments/assets/7d63f5d6-66a9-4557-98bf-c0d0bf66e615" />


# 🚀 Multi-LLM Research Assistant

<p align="center">

**Enterprise-Grade AI Research Platform powered by Spring AI, Advanced RAG, MCP, Gemini, Groq, Ollama, and MariaDB Vector Search**

</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-green)
![Spring AI](https://img.shields.io/badge/Spring_AI-1.0-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![MariaDB](https://img.shields.io/badge/MariaDB-Vector_Store-blue)
![MCP](https://img.shields.io/badge/MCP-Enabled-purple)

</p>

---

## 📖 Overview

**Multi-LLM Research Assistant** is an enterprise-grade AI research platform designed to demonstrate modern AI engineering practices using **Spring Boot**, **Spring AI**, **Advanced Retrieval-Augmented Generation (RAG)**, **Model Context Protocol (MCP)**, **Multi-LLM Orchestration**, **Conversation Memory**, and **Tool Calling**.

The platform supports multiple AI interaction channels while converging into a unified intelligent processing pipeline.

### Supported Interaction Channels

* 🌐 REST API
* 🔌 Custom MCP Endpoint
* 🤖 Standard MCP Server Protocol

### Core Capabilities

* Advanced RAG
* Multi-LLM Processing
* Conversation Memory
* Tool Calling
* Citation Generation
* Vector Search
* MCP Integration
* Docker Deployment

---

# 🚀 Multi-LLM Research Assistant

<p align="center">

### Enterprise-Grade Multi-LLM Research Platform powered by Spring AI, Advanced RAG, MCP (Model Context Protocol), Gemini, Groq, Ollama Embeddings, MariaDB Vector Store, Conversation Memory, and Dynamic Tool Calling

</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-green)
![Spring AI](https://img.shields.io/badge/Spring_AI-1.0-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![MariaDB](https://img.shields.io/badge/MariaDB-Vector_Store-blue)
![MCP](https://img.shields.io/badge/MCP-Enabled-purple)

</p>

---

# 📖 Overview

Multi-LLM Research Assistant is an enterprise-grade AI research platform built using Spring Boot, Spring AI, Advanced Retrieval Augmented Generation (RAG), Model Context Protocol (MCP), Multi-LLM orchestration, Conversation Memory, and Dynamic Tool Calling.

The platform supports multiple AI interaction channels while converging into a unified intelligent processing pipeline.

## Core Capabilities

* Advanced RAG
* Multi-LLM Processing
* Conversation Memory
* MCP Integration
* Tool Calling
* Citation Generation
* Vector Search
* Docker Deployment
* Academic Research Support

---

# 🎯 Problem Statement

Most AI assistants rely on a single model and lack:

* Contextual Memory
* Retrieval Capabilities
* Tool Integration
* Citation Support
* Multi-Model Processing

This project addresses these limitations by combining:

* Retrieval-Augmented Generation (RAG)
* Multi-LLM Orchestration
* Conversational Memory
* MCP Protocol Support
* Dynamic Tool Calling
* Academic Citation Generation

into a single extensible AI platform.

---

# 🏗️ Architecture & Workflow

<p align="center">
<img src="docs/images/research-assistant-architecture.png" width="100%">
</p>

## Three Entry Paths → One Unified AI Pipeline

The platform supports three independent entry mechanisms:

| Flow   | Entry Point         | Usage                                       |
| ------ | ------------------- | ------------------------------------------- |
| Flow 1 | REST API            | Application Clients                         |
| Flow 2 | Custom MCP Endpoint | Custom UI / Browser Extensions / Postman    |
| Flow 3 | MCP Server Protocol | Claude Desktop, Gemini Desktop, MCP Clients |

All flows converge into a common AI orchestration pipeline powered by Spring AI.

---

# 🔄 Flow 1 – REST API

Standard application flow.

```http
POST /api/research/process
```

Used by:

* Web Applications
* Mobile Applications
* External Systems
* Internal Enterprise Integrations

The request is validated and transformed into a ResearchContext before entering the AI pipeline.

---

# 🔄 Flow 2 – Custom MCP Endpoint

The application exposes a simplified MCP-style endpoint.

```http
POST /api/mcp/process
```

Unlike traditional MCP clients, users do not need to manually select a tool.

The controller automatically determines which MCP tool should be executed based on the query content.

## Auto Tool Detection

Example:

```text
Summarize this article
        ↓
summarizeContent()
```

```text
Suggest related topics on RAG
        ↓
suggestTopics()
```

```text
Explain Advanced RAG
        ↓
analyzeContent()
```

## Tool Selection Logic

```java
if(query contains summary keywords)
    summarizeContent()

else if(query contains suggest keywords)
    suggestTopics()

else
    analyzeContent()
```

This enables a lightweight MCP experience for:

* Browser Extensions
* Custom AI Portals
* Internal Enterprise UIs
* Postman
* Third-party Integrations

without requiring users to explicitly choose a tool.

---

# 🔌 Flow 3 – MCP Server Capability

The application also exposes tools through a standard MCP Server implementation.

Compatible with:

* Claude Desktop
* Gemini Desktop
* MCP Clients
* Agentic AI Platforms

The following tool are automatically exposed:

```text
citationTool()
```

---

# 🔌 Model Context Protocol (MCP)

The application supports both MCP integration patterns.

## Custom MCP Endpoint

```http
POST /api/mcp/process
```

## Standard MCP Server

Compatible with:

* Claude Desktop
* Gemini Desktop
* MCP Clients
* AI Agent Platforms

### MCP Configuration

```properties
spring.ai.mcp.server.enabled=true
spring.ai.mcp.server.name=research-assistant
spring.ai.mcp.server.version=1.0.0
```

---

# ⚡ Understanding STDIO Transport

Current MCP implementation uses:

```text
STDIO (Standard Input / Output)
```

Communication occurs through process streams.

```text
Claude Desktop
        │
        ▼
STDIN
        │
        ▼
Research Assistant MCP Server
        │
        ▼
STDOUT
        │
        ▼
Claude Desktop
```

No REST endpoint is required.

The MCP client launches the Spring Boot MCP Server process and communicates directly through standard input and output streams.

## Benefits

* Fast Local Communication
* No Exposed Ports
* Native Claude Desktop Support
* Native Gemini Desktop Support
* Simplified Configuration
* Secure Local Execution

## Future Enhancement

```text
STDIO
   ↓

SSE (Server Sent Events)

   ↓

Remote MCP Hosting
```

---

# 🤖 Using Claude Desktop with MCP

## Step 1 – Build Application

```bash
mvn clean install
```

---

## Step 2 – Locate Generated Jar

```text
target/research-assistant-0.0.1-SNAPSHOT.jar
```

---

## Step 3 – Configure Claude Desktop

Update Claude Desktop MCP configuration.

Example:

```json
{
  "mcpServers": {
    "research-assistant": {
      "command": "java",
      "args": [
        "-jar",
        "C:\\Projects\\Multi-LLM_research_assistant\\target\\research-assistant-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```



# 🧠 Common AI Processing Pipeline

```text
1. User Request

        ↓

2. Simple RAG
   Vector Search (TopK = 1)

        ↓

3. Prompt Builder

        ↓

4. Primary LLM
   Gemini 2.5 Flash
   (Content Extraction)

        ↓

5. Structured Output

        ↓

6. Advanced RAG

   • Query Rewrite
   • Multi Query
   • Vector Search (TopK = 5)
   • Context Augmentation

        ↓

7. Secondary LLM
   Groq Qwen3-32B

        ↓

8. Groq Decision

   • Answer Directly
   • Invoke Citation Tool

        ↓

9. CitationMcpTool

   CrossRef API

        ↓

10. Final Response
```

---

# 🤖 Multi-LLM Strategy

| Model                | Responsibility                        |
| -------------------- | ------------------------------------- |
| Gemini 2.5 Flash     | Content Extraction & Structuring      |
| Groq Qwen3-32B       | Reasoning & Final Response Generation |
| Ollama Nomic         | Embedding Generation                  |
| Spring AI ChatClient | AI Orchestration Layer                |

## Why Multiple Models?

Instead of forcing one model to handle every task:

* Gemini extracts and structures information.
* Groq performs deep reasoning and answer generation.
* Ollama Nomic generates vector embeddings.
* Spring AI orchestrates the complete workflow.

### Benefits

* Better Response Quality
* Scalability
* Vendor Independence
* Cost Optimization
* Specialized Processing

---

# 🔧 Internal Tool Calling

The platform supports dynamic tool invocation during AI execution.

## CitationMcpTool

### Capabilities

* Research Citation Generation
* Academic References
* IEEE Citations
* CrossRef Integration

### Workflow

```text
LLM
 │
 ▼

Tool Decision
 │
 ▼

CitationMcpTool
 │
 ▼

CrossRef API
 │
 ▼

Citation Data
 │
 ▼

LLM Response
```

Groq decides whether a citation is required and invokes the CitationMcpTool when necessary.

---

# 🛠️ Technology Stack

| Layer            | Technology        |
| ---------------- | ----------------- |
| Language         | Java 17           |
| Framework        | Spring Boot 3.3.5 |
| AI Framework     | Spring AI 1.0     |
| LLM Extraction   | Gemini 2.5 Flash  |
| LLM Reasoning    | Groq Qwen3-32B    |
| Embeddings       | Ollama Nomic      |
| Vector Store     | MariaDB           |
| Memory           | JDBC Chat Memory  |
| MCP              | Spring AI MCP     |
| Containerization | Docker            |
| Build Tool       | Maven             |

---

# 🗂️ Project Structure

```text
src/main/java/com/research/assistant

├── controller
│   ├── ResearchController.java
│   └── McpController.java
│
├── service
│   ├── ResearchService.java
│   ├── RagService.java
│   ├── AdvancedRAGService.java
│   └── DocumentLoaderService.java
│
├── tools
│   ├── ResearchMcpTools.java
│   └── CitationMcpTool.java
│
├── config
│   ├── ChatModelConfig.java
│   ├── EmbeddingConfig.java
│   ├── SpringAIConfig.java
│   ├── McpToolConfig.java
│   └── WebClientConfig.java
│
├── model
│   └── ResearchContext.java
│
└── DTOcontracts
    ├── ResearchRequest.java
    ├── McpResearchRequest.java
    └── GeminiResponse.java

src/main/resources

├── application.yml
├── schema.sql
├── spring-logback.xml
└── prompts/
```

---

# 🐳 Development Deployment Architecture

The project follows a hybrid deployment model.

```text
Research Assistant
(Spring Boot Local)

        │ JDBC : 3310

        ▼

MariaDB
(Docker Container)

        │ HTTP : 11434

        ▼

Ollama
(Docker Container)
```

## Why Hybrid?

Spring Boot runs locally for:

* Fast Development
* Easier Debugging
* Hot Reload
* Faster Iteration

Docker hosts:

* MariaDB
* Ollama

providing a consistent AI infrastructure environment.

---

# 📄 API Contracts

## Flow 1 – ResearchController

```http
POST /api/research/process
```

### Sample Request

```json
{
  "content": "Explain Advanced RAG",
  "topic": "RAG",
  "conversationId": "conv-001",
  "citationRequired": true
}
```

---

## Flow 2 – McpController

```http
POST /api/mcp/process
```

### Summarization

```json
{
  "content": "Summarize the uploaded research paper",
  "conversationId": "conv-001"
}
```

Auto-detected:

```text
summarizeContent()
```

### Topic Suggestions

```json
{
  "content": "Suggest related topics on MCP",
  "conversationId": "conv-001"
}
```

Auto-detected:

```text
suggestTopics()
```

### Analysis

```json
{
  "content": "Explain Model Context Protocol",
  "conversationId": "conv-001"
}
```

Auto-detected:

```text
analyzeContent()
```

### Explicit Tool Selection

```json
{
  "content": "Explain Retrieval Augmented Generation",
  "operations": "analyze",
  "conversationId": "conv-001"
}
```

Supported Operations:

```text
summarize
suggest
analyze
```

---

# 🚀 Getting Started

## Prerequisites

* Java 17
* Maven 3.9+
* Docker Desktop
* Git

## Start Infrastructure

```bash
docker compose up -d mariadb ollama
```

## Pull Embedding Model

```bash
docker exec -it research-ollama ollama pull nomic-embed-text
```

## Build Project

```bash
mvn clean install
```

## Run Application

```bash
java -jar target/research-assistant-0.0.1-SNAPSHOT.jar
```

---

# 🎯 Key Features

✅ Three Independent Entry Flows

✅ Unified AI Processing Pipeline

✅ Advanced RAG (Rewrite + MultiQuery + Augmentation)

✅ Multi-LLM Architecture

✅ MCP Integration

✅ Tool Calling

✅ Citation Generation

✅ Conversation Memory

✅ MariaDB Vector Store

✅ Ollama Embeddings

✅ Docker Support

✅ Claude Desktop Integration

✅ Gemini Desktop Integration

---

