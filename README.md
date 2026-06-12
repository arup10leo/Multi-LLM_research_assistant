<img width="1000" height="540" alt="ChatGPT Image Jun 12, 2026, 11_11_57 PM" src="https://github.com/user-attachments/assets/a61c7fed-925b-4077-8d58-e541f05eb260" />

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

# 🏗️ Architecture & Workflow



### Three Entry Paths → One Intelligent AI Pipeline

1. REST API Clients
2. Custom MCP Endpoint
3. Standard MCP Server Clients

All requests are routed through a common orchestration layer powered by **Spring AI**.

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

# 🧠 Internal AI Processing Pipeline

```text
User Request
     │
     ▼

Simple RAG Retrieval
(Vector Search)
     │
     ▼

Prompt Builder
     │
     ▼

Gemini 2.5 Flash
(Content Extraction)
     │
     ▼

Response Parsing
     │
     ▼

Spring AI ChatClient
     │
     ├── Memory Advisor
     ├── Advanced RAG Advisor
     └── Tool Calling
              │
              ▼

Citation Tool
(CrossRef API)
              │
              ▼

Groq Qwen3-32B
(Reasoning Layer)
              │
              ▼

Final Response
```

---

# 🔍 Advanced RAG Workflow

```text
User Query
    │
    ▼

Query Rewriter
    │
    ▼

Multi Query Generator
    │
    ▼

Embedding Generation
(Nomic Embeddings)
    │
    ▼

MariaDB Vector Search
    │
    ▼

Top-K Context Retrieval
    │
    ▼

Context Augmentation
    │
    ▼

Prompt Enrichment
    │
    ▼

Groq Qwen3-32B
(Reasoning Engine)
    │
    ▼

Final Answer
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

* **Gemini** extracts and structures information.
* **Groq** performs deep reasoning and answer generation.
* **Ollama Nomic** generates vector embeddings.
* **Spring AI** orchestrates the entire workflow.

This approach improves:

* Response Quality
* Scalability
* Vendor Independence
* Cost Optimization

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

### Future Extensions

* Web Search Tool
* Knowledge Graph Tool
* Enterprise APIs
* Database Retrieval Tools

---

# 🔌 Model Context Protocol (MCP)

The application supports both MCP integration patterns.

## Custom MCP Endpoint

```http
POST /mcp
```

## Standard MCP Server

Compatible with:

* Claude Desktop
* Gemini Desktop
* MCP Clients
* AI Agent Platforms

### Configuration

```properties
spring.ai.mcp.server.enabled=true
spring.ai.mcp.server.name=research-assistant
spring.ai.mcp.server.version=1.0.0
```

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

# 📂 Project Structure

```text
src/main/java/com/research/assistant

├── controller
│   └── ResearchController
│
├── service
│   ├── ResearchService
│   ├── RagService
│   ├── AdvancedRAGService
│   └── DocumentLoaderService
│
├── config
│   ├── ChatModelConfig
│   ├── EmbeddingConfig
│   ├── SpringAIConfig
│   ├── McpToolConfig
│   └── WebClientConfig
│
├── tools
│   ├── ResearchMcpTools
│   └── CitationMcpTool
│
├── model
│   └── ResearchContext
│
└── DTOcontracts
    ├── ResearchRequest
    ├── McpResearchRequest
    └── GeminiResponse
```

---

# 🐳 Docker Architecture

```text
Docker Compose
│
├── research-assistant
│      Spring Boot Application
│
├── research-mariadb
│      Vector Store
│      Chat Memory
│      Metadata
│
└── research-ollama
       Nomic Embeddings
```

## Containers

### research-assistant

Main Spring Boot Application

**Port:** `8080`

### research-mariadb

Stores:

* Embeddings
* Chat Memory
* Metadata

**Port:** `3310`

### research-ollama

Provides:

```text
nomic-embed-text
```

**Port:** `11434`

---

# 🚀 Getting Started

## Prerequisites

Install the following:

* Java 17
* Maven 3.9+
* Docker Desktop
* Git

---

## Clone Repository

```bash
git clone https://github.com/arup10leo/Multi-LLM_research_assistant.git

cd Multi-LLM_research_assistant
```

---

## Build Application

```bash
mvn clean install
```

Generated Artifact:

```text
target/assistant-0.0.1-SNAPSHOT.jar
```

---

## Pull Embedding Model

Start Ollama:

```bash
docker compose up ollama
```

Install Nomic Embeddings:

```bash
docker exec -it research-ollama ollama pull nomic-embed-text
```

Verify Installation:

```bash
docker exec -it research-ollama ollama list
```

---

## Configure API Keys

### Gemini

```properties
GEMINI_API_KEY=<your-key>
```

### Groq

```properties
SPRING_AI_OPENAI_API_KEY=<your-groq-key>
```

---

## Start Complete Platform

```bash
docker compose up --build
```

### Startup Sequence

```text
MariaDB
   ↓
Ollama
   ↓
Spring Boot
```

---

# 📡 API Usage

## Research Endpoint

```http
POST /api/research/process
```

### Example Request

```json
{
  "content": "Explain Retrieval Augmented Generation",
  "topic": "RAG"
}
```

---

# ✅ Production Features

* Multi-LLM Orchestration
* Advanced RAG
* MCP Protocol Support
* Tool Calling
* Citation Generation
* Vector Search
* Conversation Memory
* Docker Deployment
* Local Embeddings
* Spring AI Integration

---

# 🔮 Roadmap

* Streaming Responses (SSE)
* Hybrid Search
* Re-Ranking Models
* Agentic Workflows
* OpenTelemetry
* Prometheus Monitoring
* Grafana Dashboards
* LLM Routing Strategy
* Multi-Agent Architecture

---

# 👨‍💻 Author

**Arup Sarkar**

Enterprise AI Research Platform built using:

**Spring AI • Advanced RAG • MCP • Gemini • Groq • Ollama • MariaDB Vector Search**

---

⭐ If you found this project useful, consider giving it a star on GitHub.

