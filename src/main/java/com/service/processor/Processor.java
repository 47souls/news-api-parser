package com.service.processor;

import com.service.model.Article;
import com.service.parser.Parser;

import java.util.List;

public interface Processor {

    List<Article> process(Parser parser);
}
