package com.service.processor;

import com.service.model.Article;
import com.service.parser.ParserImpl;
import com.service.source.Format;
import com.service.source.Source;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static com.service.util.JsonUtils.readJsonFromFile;
import static com.service.util.JsonUtils.readJsonFromUrl;

/**
 * Implementation class that defines logic for processing articles. Processing means
 * retrieving articles from provided source and passing those into parser
 *
 * @see Processor
 */
@RequiredArgsConstructor
public class ProcessorImpl implements Processor {

    private final Source source;
    private final Format format;
    private final String location;
    private final Logger logger;

    /**
     * Starts processing of articles by attempting to read news api model
     * representation from provided location and source (URL or FILE)
     * and assuming format source (SIMPLE or NEWSAPI)
     *
     * @return list of processed articles
     * @see com.service.util.JsonUtils
     */
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
