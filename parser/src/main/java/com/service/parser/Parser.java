package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.model.Article;

import java.util.List;

/**
 * TODO
 */
public interface Parser {

    /**
     * TODO
     */
    List<Article> parse() throws JsonProcessingException;
}
