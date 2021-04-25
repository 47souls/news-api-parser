package com.service.processor;

import com.service.model.Article;
import com.service.parser.ParserImpl;
import com.service.source.Format;
import com.service.source.Source;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.service.util.JsonUtils.readJsonFromFile;
import static com.service.util.JsonUtils.readJsonFromUrl;

public class ProcessorImpl implements Processor {

    private final Source source;
    private final Format format;
    private final Logger logger;
    private final String location;

    public ProcessorImpl(Source source, Format format, Logger logger, String location) {
        this.source = source;
        this.format = format;
        this.logger = logger;
        this.location = location;
    }

    @Override
    public List<Article> process() throws IOException {
        if (source.equals(Source.FILE)) {
            return processFromFile(location, format);
        } if (source.equals(Source.URL)) {
            return processFromUrl(location, format);
        }

        throw new RuntimeException("Source " + source + " not supported");
    }

    private List<Article> processFromFile(String location, Format format) throws IOException {
        String jsonString = readJsonFromFile(location);
        return new ParserImpl(logger, jsonString, format).parse();
    }

    private List<Article> processFromUrl(String location, Format format) throws IOException {
        String jsonString = readJsonFromUrl(location);
        return new ParserImpl(logger, jsonString, format).parse();
    }
}
