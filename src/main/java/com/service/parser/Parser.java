package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.model.Article;

import java.util.List;

public interface Parser {

    List<Article> parse() throws JsonProcessingException;
}
