package com.service.processor;

import com.service.model.Article;
import com.service.parser.Parser;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class URLProcessor <T extends Article> extends Processor<T> {

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
            parser.setArticlesJson(readJsonFromUrl(location));
        } catch (Exception e) {
            logger.warning("Unable to read json content for provided source file "
                    + " and format URL and location " + location
                    + ". Exception: " + e.getMessage() + "\n");
            return Collections.emptyList();
        }

        return parser.parse();
    }


    /**
     * Does the REST api call to the provided url and retrieves it's body as a string
     *
     * @param url url which will be used to make a REST api call
     * @return parsed string json
     */
    private static String readJsonFromUrl(String url) throws IOException {
        URL targetUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) targetUrl.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }
}
