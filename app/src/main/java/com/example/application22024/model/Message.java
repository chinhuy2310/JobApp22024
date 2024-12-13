package com.example.application22024.model;
public class Message {
    private String type; // "Sent" hoặc "Received"
    private String content;
    private int senderId;
    public Message(String type, String content,int senderId) {
        this.type = type;
        this.content = content;
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return "Messages{" +
                "type=" + type +
                ", content=" + content +
                '}';
    }

    public int getSenderId() {
        return senderId;
    }
}