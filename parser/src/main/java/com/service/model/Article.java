package com.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private final String title;
    private final String description;
    private final String url;
    private final LocalDateTime publishedAt;

    public Article(String title, String description, String url, LocalDateTime publishedAt) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
    }
}
