package com.service.processor;

import com.service.model.Article;
import com.service.parser.Parser;
import com.service.source.Source;

import java.util.List;

public class UrlProcessor implements Processor {

    private static final Source SOURCE = Source.URL;

    @Override
    public List<Article> process(Parser parser) {
        return null;
    }
}
