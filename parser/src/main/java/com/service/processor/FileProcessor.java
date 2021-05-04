package com.service.processor;

import com.service.model.Article;
import com.service.parser.Parser;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class FileProcessor <T extends Article> extends Processor<T> {

    private final String location;
    private final Logger logger;
    private final Parser<T> parser;

    /**
     * TODO: comments update
     * Performs general processing of articles. The details of processing
     * is up to decide for implementations
     *
     * @return a list of processed articles
     */
    @Override
    public List<T> process() {
        try {
            parser.setArticlesJson(readJsonFromFile(location));
        } catch (Exception e) {
            logger.warning("Unable to read json content for provided source file "
                    + " and format FILE and location " + location
                    + ". Exception: " + e.getMessage() + "\n");
            return Collections.emptyList();
        }

        return parser.parse();
    }

    /**
     * Reads a json from provided file
     *
     * @param fileName name of the file where json
     * @return parsed string json
     */
    private static String readJsonFromFile(String fileName) throws IOException {
        ClassLoader classLoader = FileProcessor.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        return jsonBuilder.toString();
    }
}
