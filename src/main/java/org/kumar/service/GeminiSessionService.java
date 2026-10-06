package org.kumar.service;

import com.google.adk.runner.Runner;
import com.google.adk.sessions.Session;
import org.kumar.runner.GeminiChatRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeminiSessionService {

    private final ConcurrentHashMap<String, Session> sessions;

    private final Runner runnner;

    public GeminiSessionService(GeminiChatRunner chatRunner) {
        this.sessions = new ConcurrentHashMap<>();
        this.runnner = chatRunner.getRunner();
    }

    public Session getSession(String userId) {
        Session session = sessions.get(userId);
        if (session == null) {
            session = runnner
                    .sessionService()
                    .createSession(runnner.appName(), userId)
                    .blockingGet();
            sessions.put(userId, session);
        }
        return session;
    }
}
