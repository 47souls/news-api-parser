package com.service.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Base class for all news api articles
 */
@Data
public class Article {
    private final String title;
    private final String description;
    private final String url;
    private final LocalDateTime publishedAt;

    /**
     * Constructor for base Article class. Includes mandatory params needed for creation
     * of valid Article object
     *
     * @param title title
     * @param description description
     * @param url url
     * @param publishedAt published date
     */
    public Article(String title, String description, String url, LocalDateTime publishedAt) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
    }
}
