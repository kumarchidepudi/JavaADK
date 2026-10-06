package org.kumar.web;

import org.kumar.chat.GeminiChat;
import org.kumar.model.ChatResponse;
import org.kumar.model.UserMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiChatController {

    private final GeminiChat chat;

    public GeminiChatController(GeminiChat chat) {
        this.chat = chat;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody UserMessage message) {
        return new ChatResponse(chat.ask(message.getUserId(), message.getMessage()));
    }
}
