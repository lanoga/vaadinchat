package hu.lanoga.chat.entity;


import java.sql.Timestamp;

public class ChatMessage {
    private Long id;
    private String from;
    private String message;
    private Timestamp created;

    public ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
