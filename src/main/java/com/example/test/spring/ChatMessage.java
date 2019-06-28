package com.example.test.spring;

import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

public class ChatMessage {
    private String from;
    private String message;

    ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
    }

    String getFrom() {
        return from;
    }

    String getMessage() {
        return message;
    }
}
