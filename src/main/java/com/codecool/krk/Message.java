package com.codecool.krk;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public Message(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
