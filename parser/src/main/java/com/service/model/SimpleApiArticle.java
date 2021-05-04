package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.service.serializer.LocalDateTimeSimpleFormatDeserializer;
import com.service.serializer.LocalDateTimeSimpleFormatSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representation of SIMPLE API article model
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApiArticle extends Article {

    private final String title;
    private final String description;
    private final String url;
    @JsonDeserialize(using = LocalDateTimeSimpleFormatDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSimpleFormatSerializer.class)
    private final LocalDateTime publishedAt;

    /**
     * Constructor for NEWS API Article class. Includes mandatory params needed for creation
     * of valid Article object
     *
     * @param title title
     * @param description description
     * @param url url
     * @param publishedAt published date
     */
    public SimpleApiArticle(@JsonProperty("title") String title, @JsonProperty("description") String description,
                            @JsonProperty("url") String url, @JsonProperty("publishedAt") LocalDateTime publishedAt) {
        super(title, description, url, publishedAt);
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
    }
}
