package com.service;

import com.service.model.Article;
import com.service.model.NewsApiArticle;
import com.service.model.SimpleApiArticle;
import com.service.parser.ParserApiFacade;
import com.service.source.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.service.format.Format.NEWSAPI;
import static com.service.format.Format.SIMPLE;
import static com.service.source.Source.FILE;
import static com.service.source.Source.URL;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String NEWS_API_KEY = "136a81fcc8e8433f8be20587cf40a7da";

    private static Map<Source, String> newsApiFormatMap = new HashMap<>() {{
        put(URL, "http://newsapi.org/v2/top-headlines?country=us&apiKey=" + NEWS_API_KEY);
        put(FILE, "newsapi.json");
    }};

    private static Map<Source, String> simpleApiFormatMap = new HashMap<>() {{
        put(URL, "http://users.csc.calpoly.edu/~akeen/courses/csc305/simple.json");
        put(FILE, "simple.json");
    }};

    /**
     * Main entry to the program. Demonstrates the ability to parse NEWS API responses
     * @param args input arguments of the program
     */
    public static void main(String[] args) {
        // Retrieving news api format json
        for (Map.Entry<Source, String> urlEntrySet: newsApiFormatMap.entrySet()) {
            Source source = urlEntrySet.getKey();
            String url = urlEntrySet.getValue();

            LOGGER.info("Parsing articles using provided location " + url + "\n\n");

            ParserApiFacade<NewsApiArticle> parserApiFacade = new ParserApiFacade<>(LOGGER, url, NEWSAPI, source);
            List<NewsApiArticle> newsApiArticles = parserApiFacade.processArticles();
            printResults(newsApiArticles, url);
        }

        // Retrieving simple api format json
        for (Map.Entry<Source, String> urlEntrySet: simpleApiFormatMap.entrySet()) {
            Source source = urlEntrySet.getKey();
            String url = urlEntrySet.getValue();

            LOGGER.info("Parsing articles using provided location " + url + "\n\n");

            ParserApiFacade<SimpleApiArticle> parserApiFacade = new ParserApiFacade<>(LOGGER, url, SIMPLE, source);
            List<SimpleApiArticle> simpleApiArticles = parserApiFacade.processArticles();
            printResults(simpleApiArticles, url);
        }
    }

    private static void printResults(List<? extends Article> newsApiArticleList, String url) {
        if (!newsApiArticleList.isEmpty()) {
            LOGGER.info("Valid articles using provided url " + url + "\n");
            newsApiArticleList.forEach(article ->
                    LOGGER.info("Valid article: \nTitle: " + article.getTitle()
                            + " , \nDescription : " + article.getDescription()
                            + " , \nUrl : " + article.getUrl()
                            + " , \nDate published : " + article.getPublishedAt() + "\n"));
            LOGGER.info("Finished processing articles from file " + url + "\n\n");
        }
    }
}
