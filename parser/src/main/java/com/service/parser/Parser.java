package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.model.Article;

import java.util.List;

/**
 * Interface that defines a contract method for all Parser implementations
 */
public interface Parser {

    /**
     * Performs parsing of articles. The details of parsing
     * is up to decide for implementations
     *
     * @return a list of parsed articles
     */
    List<Article> parse() throws JsonProcessingException;
}
