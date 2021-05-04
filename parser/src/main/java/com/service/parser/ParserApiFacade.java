package com.service.parser;

import com.service.format.Format;
import com.service.model.Article;
import com.service.processor.FileProcessor;
import com.service.processor.Processor;
import com.service.processor.URLProcessor;
import com.service.source.Source;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Main entry to the parser library. Acts like a facade so hides implementation details
 * and makes it easy to use parsing functionality
 *
 */
public class ParserApiFacade<T extends Article> {

    private Processor<T> processor;

    /**
     * Constructor for ParserApiFacade class. Includes mandatory params needed for creation
     * of valid ParserApiFacade object. Additionally, instantiates required implementations
     * of #Parser and #Processor interfaces
     *
     * @param logger logger
     * @param location location
     * @param format format
     * @param source source
     */
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

    /**
     * Starting point to processing functionality. Delegates to #Processor implementation
     *
     * @return list of processed articles
     */
    public List<T> processArticles() {
        return processor.process();
    }
}
