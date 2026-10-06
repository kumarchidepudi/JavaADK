package org.kumar.chat;

import com.google.adk.events.Event;
import com.google.adk.runner.Runner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import org.kumar.runner.GeminiChatRunner;
import org.kumar.service.GeminiSessionService;
import org.springframework.stereotype.Component;

@Component
public class GeminiChat {

    private final GeminiSessionService sessionService;
    private final Runner runner;

    private GeminiChat(GeminiSessionService sessionService, GeminiChatRunner chatRunner) {
        this.sessionService = sessionService;
        this.runner = chatRunner.getRunner();
    }

    public String ask(String userId, String question){
        Session session = sessionService.getSession(userId);

        Content content = Content.fromParts(Part.fromText(question));

        Flowable<Event> events = runner.runAsync(session.userId(), session.id(), content);

        StringBuilder builder = new StringBuilder();
        events.blockingForEach(event -> {
            if(event.finalResponse()){
                builder.append(event.stringifyContent());
            }
        });
        return builder.toString();
    }
}
