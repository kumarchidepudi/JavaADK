package org.kumar.runner;

import com.google.adk.runner.InMemoryRunner;
import com.google.adk.runner.Runner;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.kumar.agent.GeminiChatAgent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeminiChatRunner {

    @Getter
    private final Runner runner;

    public GeminiChatRunner(GeminiChatAgent agent) {
        this.runner = new InMemoryRunner(agent.getAgent());
    }

}
