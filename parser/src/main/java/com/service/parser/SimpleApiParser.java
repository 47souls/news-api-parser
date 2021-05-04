package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.SimpleApiArticle;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation class that defines logic for parsing SIMPLE API articles. Parsing means
 * checking on the subject of validity
 *
 */
public class SimpleApiParser extends Parser<SimpleApiArticle> {

    private final Logger logger;

    @Setter
    private String articlesJson;

    public SimpleApiParser(Logger logger) {
        super(logger);
        this.logger = logger;
    }

    /**
     * Performs parsing of articles assuming SIMPLE API model
     *
     * @return a list of parsed articles
     */
    @Override
    public List<SimpleApiArticle> parse() {
        return readArticlesFromJson(articlesJson)
            .stream()
            .filter(this::parseArticle)
            .collect(Collectors.toList());
    }

    /**
     * Reads articles from json assuming SIMPLE API format.
     *
     * @param articlesJson string representing articles json
     * @return list of valid articles
     */
    protected List<SimpleApiArticle> readArticlesFromJson(String articlesJson) {
        try {
            return Collections.singletonList(new ObjectMapper().readerFor(SimpleApiArticle.class)
                    .readValue(articlesJson));
        } catch (JsonProcessingException e) {
            logger.warning("Unable to read articles from json " + articlesJson + " for format SIMPLE API\n");
        }
        return Collections.emptyList();
    }
}
