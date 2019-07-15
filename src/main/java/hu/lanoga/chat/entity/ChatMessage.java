package hu.lanoga.chat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // TODO add spring data jpa dependency, postgresql driver,
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long msg_id;

    @Column(name = "msg_from", nullable = false)
    private String from;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    public ChatMessage() {
    }

    //TODO "created" implementation
    public ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
        //this.created = created;
    }

    public Long getId() {
        return msg_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
