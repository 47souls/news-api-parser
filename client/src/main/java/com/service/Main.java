package com.service;

import com.service.model.Article;
import com.service.processor.ProcessorImpl;
import com.service.source.Format;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.service.source.Format.NEWSAPI;
import static com.service.source.Format.SIMPLE;
import static com.service.source.Source.FILE;
import static com.service.source.Source.URL;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String NEWS_API_KEY = "136a81fcc8e8433f8be20587cf40a7da";

    private static Map<Format, String> formatUrlMap = new HashMap<>() {{
        put(SIMPLE, "http://users.csc.calpoly.edu/~akeen/courses/csc305/simple.json");
        put(NEWSAPI, "http://newsapi.org/v2/top-headlines?country=us&apiKey=" + NEWS_API_KEY);
    }};

    private static Map<Format, String> formatFileMap = new HashMap<>() {{
        put(SIMPLE, "simple.json");
        put(NEWSAPI, "newsapi.json");
    }};

    /**
     * Main entry to the program. Demonstrates the ability to parse news api responses
     * @param args input arguments of the program
     */
    public static void main(String[] args) {

        // Retrieving json from url
        for (Map.Entry<Format, String> urlEntrySet: formatUrlMap.entrySet()) {
            Format format = urlEntrySet.getKey();
            String url = urlEntrySet.getValue();

            LOGGER.info("Parsing articles using provided location " + url + "\n\n");

            ProcessorImpl processor = new ProcessorImpl(URL, format, url, LOGGER);
            List<Article> articles = processor.process();
            printResults(articles, url);
        }

        // Retrieving json from files
        for (Map.Entry<Format, String> urlEntrySet: formatFileMap.entrySet()) {
            Format format = urlEntrySet.getKey();
            String url = urlEntrySet.getValue();

            LOGGER.info("Parsing articles using provided location " + url + "\n\n");

            ProcessorImpl processor = new ProcessorImpl(FILE, format, url, LOGGER);
            List<Article> articles = processor.process();
            printResults(articles, url);
        }
    }

    private static void printResults(List<Article> articleList, String url) {
        if (!articleList.isEmpty()) {
            LOGGER.info("Valid articles using provided url " + url + "\n");
            articleList.forEach(article ->
                    LOGGER.info("Valid article: \nTitle: " + article.getTitle()
                            + " , \nDescription : " + article.getDescription()
                            + " , \nUrl : " + article.getUrl()
                            + " , \nDate published : " + article.getPublishedAt() + "\n"));
            LOGGER.info("Finished processing articles from file " + url + "\n\n");
        }
    }
}
