package org.kumar.agent;

import com.google.adk.agents.LlmAgent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Component
@Slf4j
public class GeminiChatAgent {
    private final LlmAgent agent;
    public GeminiChatAgent() {
        agent = LlmAgent.builder()
                .name("Gemini Chat Agent")
                .description("Gemini Chat Agent used to chat")
                .instruction(
                        """
                                You're an helpful chat agent, answers the questions clearly and precisely.
                                """
                )
                .model("gemini-3.8-flash")
                .build();
    }

}



