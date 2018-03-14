package com.codecool.krk.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private String room;

    public Message(String content, String author, String room) {
        this.content = content;
        this.author = author;
        this.room = room;

        this.createdAt = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return String.format("Message{content='%s', author='%s', room='%s', createdAt='%s'",
                this.content, this.author, this.room, this.createdAt);
    }
}