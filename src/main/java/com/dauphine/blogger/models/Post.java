package com.dauphine.blogger.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Category category;

    public Post() {}

    public Post(UUID id, String title, String content, LocalDateTime createdDate, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.category = category;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}