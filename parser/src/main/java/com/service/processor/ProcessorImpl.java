package com.service.processor;

import com.service.model.Article;
import com.service.parser.ParserImpl;
import com.service.source.Format;
import com.service.source.Source;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.service.util.JsonUtils.readJsonFromFile;
import static com.service.util.JsonUtils.readJsonFromUrl;

@RequiredArgsConstructor
public class ProcessorImpl implements Processor {

    private final Source source;
    private final Format format;
    private final String location;
    private final Logger logger;

    @Override
    public List<Article> process() throws IOException {
        if (source.equals(Source.FILE)) {
            return processFromFile(location, format);
        } if (source.equals(Source.URL)) {
            return processFromUrl(location, format);
        }

        throw new IllegalArgumentException("Source " + source + " not supported");
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
