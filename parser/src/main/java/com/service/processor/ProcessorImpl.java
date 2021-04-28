package com.service.processor;

import com.service.model.Article;
import com.service.parser.ParserImpl;
import com.service.source.Format;
import com.service.source.Source;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collections;
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
    public List<Article> process() {
        String jsonString = "";

        try {
            if (source.equals(Source.FILE)) {
                jsonString = readJsonFromFile(location);
            }
            if (source.equals(Source.URL)) {
                jsonString = readJsonFromUrl(location);
            }
        } catch (Exception e) {
            logger.warning("Unable to read json content for provided source " + source
                    + " and format " + format + " and location " + location
                    + ". Exception: " + e.getMessage() + "\n");
            return Collections.emptyList();
        }

        return new ParserImpl(logger, jsonString, format).parse();
    }
}
