package com.service.parser;

import com.service.model.Article;
import com.service.processor.FileProcessor;
import com.service.processor.Processor;
import com.service.processor.URLProcessor;
import com.service.format.Format;
import com.service.source.Source;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ParserApiFacade<T extends Article> {

    private Processor<T> processor;

    public ParserApiFacade(Logger logger, String location, Format format, Source source) {
        Parser parser = null;

        if (format.equals(Format.NEWSAPI)) {
            parser = new NewsApiParser(logger);
        } else if (format.equals(Format.SIMPLE)) {
            parser = new SimpleApiParser(logger);
        }

        Optional.ofNullable(parser).orElseThrow(IllegalArgumentException::new);

        if (source.equals(Source.FILE)) {
            processor = new FileProcessor<T>(location, logger, parser);
        } else if (source.equals(Source.URL)) {
            processor = new URLProcessor<T>(location, logger, parser);
        }

        Optional.ofNullable(processor).orElseThrow(IllegalArgumentException::new);
    }

    public List<T> processArticles() {
        return processor.process();
    }
}
