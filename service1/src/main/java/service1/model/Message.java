package main.java.service1.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String content;
    private String sender;
    private Date timestamp;

    public Message() {
    }

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}