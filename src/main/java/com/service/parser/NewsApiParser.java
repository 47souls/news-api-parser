package com.service.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.Article;
import com.service.model.NewsApiModel;
import com.service.validator.ArticlesValidator;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Data
public class NewsApiParser {

    private final Logger logger;

    private final ArticlesValidator articlesValidator;

    /**
     * Constructor with logger implementation provided
     *
     * @param logger the logger implementation to init parser with
     */
    public NewsApiParser(Logger logger) {
        this.logger = logger;
        this.articlesValidator = new ArticlesValidator(logger);
    }

    /**
     * Parses the news api input json. Firstly, converts string value to the objects,
     * then calls #ArticlesValidator validateArticles to only return valid articles
     *
     * @param newsApiJson news api json object to be processed
     * @return the list of processed articles from the input json
     */
    public List<Article> parseArticlesJson(String newsApiJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        NewsApiModel newsApiModel = objectMapper.readValue(newsApiJson, new TypeReference<>() { });
        return articlesValidator.validateArticles(newsApiModel.getArticles());
    }
}
