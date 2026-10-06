package org.kumar.agent;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.kumar.tool.WeatherTools;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@Slf4j
public class GeminiChatAgent {
    private final LlmAgent agent;
    public GeminiChatAgent(WeatherTools tools) {

        List<FunctionTool> functionTools = new ArrayList<>();
        functionTools.add(FunctionTool.create(tools,"getWeather"));

        agent = LlmAgent.builder()
                .name("Gemini Chat Agent")
                .description("Gemini Chat Agent used to chat")
                .instruction(
                        """
                                You're an helpful chat agent, answers the questions clearly and precisely.
                                The user you're talking to is {user_name?}
                                """
                )
                .model("gemini-3.5-flash-lite")
                .tools(functionTools)
                .build();
    }

}



