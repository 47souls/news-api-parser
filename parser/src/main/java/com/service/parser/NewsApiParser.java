package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.NewsApiArticle;
import com.service.model.NewsApiModel;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation class that defines logic for parsing NEWS API articles. Parsing means
 * checking on the subject of validity
 *
 */
public class NewsApiParser extends Parser<NewsApiArticle> {

    private final Logger logger;

    @Setter
    private String articlesJson;

    NewsApiParser(Logger logger) {
        super(logger);
        this.logger = logger;
    }

    /**
     * Performs parsing of articles assuming NEWS API model
     *
     * @return a list of parsed articles
     */
    @Override
    public List<NewsApiArticle> parse() {
        return readArticlesFromJson(articlesJson)
            .stream()
            .filter(this::parseArticle)
            .collect(Collectors.toList());
    }

    /**
     * Reads articles from json assuming NEWS API format.
     *
     * @param articlesJson string representing articles json
     * @return list of valid articles
     */
    protected List<NewsApiArticle> readArticlesFromJson(String articlesJson) {
        try {
            return readNewsApiModelFromJson(articlesJson);
        } catch (JsonProcessingException e) {
            logger.warning("Unable to read articles from json " + articlesJson + " for format NEWS API\n");
        }
        return Collections.emptyList();
    }

    /**
     * Converts provided json string to the NEWS API model
     *
     * @param jsonString json string to be parsed
     * @return parsed NEWS API model
     */
    private static List<NewsApiArticle> readNewsApiModelFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        NewsApiModel newsApiModel = new ObjectMapper().readerFor(NewsApiModel.class).readValue(jsonString);
        return newsApiModel.getNewsApiArticles();
    }
}
