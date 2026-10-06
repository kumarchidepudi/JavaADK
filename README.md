# JavaADK

JavaADK is a Spring Boot application that exposes a simple chat API backed by Google ADK (Agent Development Kit) and the Gemini model. It demonstrates how to create an LLM-powered agent, manage per-user sessions, and expose the interaction through a REST endpoint.

## Overview

The application does the following:

- Starts a Spring Boot web application
- Builds a Gemini-based `LlmAgent`
- Wraps it in an in-memory ADK runner
- Creates and reuses user-specific sessions
- Accepts chat messages via HTTP and returns the model response

## Project Structure

```text
src/
├── main/
│   ├── java/org/kumar/
│   │   ├── JavaAdkApplication.java
│   │   ├── agent/
│   │   │   └── GeminiChatAgent.java
│   │   ├── chat/
│   │   │   └── GeminiChat.java
│   │   ├── model/
│   │   │   ├── UserMessage.java
│   │   │   └── ChatResponse.java
│   │   ├── runner/
│   │   │   └── GeminiChatRunner.java
│   │   ├── service/
│   │   │   └── GeminiSessionService.java
│   │   └── web/
│   │       └── GeminiChatController.java
│   └── resources/
│       └── application.properties (if added later)
└── test/
    └── java/org/kumar/JavaAdkApplicationTests.java
```

## Core Components

### 1. JavaAdkApplication
The entry point of the application. It boots the Spring application.

### 2. GeminiChatAgent
Creates the LLM agent with:

- name: `Gemini Chat Agent`
- description: `Gemini Chat Agent used to chat`
- instruction: a simple system prompt telling the model to answer clearly and precisely
- model: `gemini-3.8-flash`

This is the agent that handles chat prompts.

### 3. GeminiChatRunner
Creates the ADK runner using `InMemoryRunner`, which is responsible for executing agent operations.

### 4. GeminiSessionService
Maintains sessions in a `ConcurrentHashMap<String, Session>` keyed by `userId`.

- If a session for the user does not exist, it creates one using the runner session service.
- Sessions are reused for future messages from the same user.
- This keeps the conversation state in memory while the application is running.

### 5. GeminiChat
Handles the actual user query flow.

Execution flow:

1. Gets or creates the session for the provided `userId`
2. Builds a `Content` object from the incoming user message
3. Calls the runner asynchronously with the user session and prompt
4. Collects the final response from emitted `Event`s
5. Returns the final model output as a string

### 6. GeminiChatController
Exposes the chat API.

Endpoint:

- `POST /chat`

Request body model:

`UserMessage`

```java
public class UserMessage {
    String userId;
    String message;
}
```

Response model:

`ChatResponse`

```java
public record ChatResponse(String modelResponse) {}
```

## API Usage

### Endpoint

```http
POST /chat
Content-Type: application/json
```

### Example Request

```json
{
  "userId": "user-123",
  "message": "What is the capital of France?"
}
```

### Example Response

```json
{
  "modelResponse": "The capital of France is Paris."
}
```

## How it Works

When a request is sent to `/chat`:

1. The controller receives the incoming JSON body.
2. The controller calls `chat.ask(userId, message)`.
3. The chat service fetches or creates a session for that user.
4. The message is sent to the configured Gemini agent through the ADK runner.
5. The final response is extracted from the generated events.
6. The response is wrapped in `ChatResponse` and returned to the client.

## In-Memory Session Behavior

This application stores conversation sessions in memory using a `ConcurrentHashMap`.

Implications:

- Conversations are not persisted across application restarts.
- Session lifetimes are tied to the running JVM process.
- Each `userId` maps to its own conversation session.

## Prerequisites

- Java 21
- Maven or the included Maven wrapper
- Valid Google ADK / Gemini access configuration available to the runtime environment

## Run the Application

From the project root:

```bash
./mvnw spring-boot:run
```

The app starts on the default Spring Boot port:

```text
http://localhost:8080
```

## Example with curl

```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user-123",
    "message": "Explain Spring Boot in 2 sentences."
  }'
```

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Google ADK (`com.google.adk:google-adk`)
- Gemini model (`gemini-3.8-flash`)
- Lombok

## Notes

This project is a lightweight demo for integrating Google ADK with a Java Spring application. It is intentionally simple, with in-memory session management and a single chat endpoint.
