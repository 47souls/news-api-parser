package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Representation of news api model
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiModel {

    private final String status;
    private final int totalResults;
    private final List<Article> articles;

    /**
     * Constructor for NewsApiModel class. Includes mandatory params needed for creation
     * of valid NewsApiModel object
     *
     * @param status status
     * @param totalResults total results
     * @param articles articles
     */
    public NewsApiModel(@JsonProperty("status") String status, @JsonProperty("totalResults") int totalResults,
                        @JsonProperty("articles") List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }
}
