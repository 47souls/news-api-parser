package com.service;

import com.service.facade.ProcessorFacade;
import com.service.model.Article;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.service.source.Format.NEWSAPI;
import static com.service.source.Source.FILE;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Main entry to the program. Demonstrates the ability to parse news api responses
     * @param args input arguments of the program
     */
    public static void main(String[] args) throws IOException {

        // TODO: this key get from application.properties
        //  "http://newsapi.org/v2/top-headlines?country=us&apiKey=136a81fcc8e8433f8be20587cf40a7da"

        // Retrieve each file name from program arguments
        for (String fileName: args) {
            ProcessorFacade processorFacade = new ProcessorFacade(FILE, NEWSAPI, fileName, LOGGER);
            List<Article> articles = processorFacade.process();
            printResults(articles, fileName);
        }
    }

    // TODO: this should be hidden for the user
    private static void printResults(List<Article> articleList, String fileName) {
        LOGGER.info("Parsing articles from provided file " + fileName + "\n\n");
        LOGGER.info("Valid articles found from file " + fileName + "\n");
        articleList.forEach(article ->
                LOGGER.info("Valid article: \nTitle: " + article.getTitle()
                        + " , \nDescription : " + article.getDescription()
                        + " , \nUrl : " + article.getUrl()
                        + " , \nDate published : " + article.getPublishedAt() + "\n"));
        LOGGER.info("Finished processing articles from file " + fileName + "\n\n");
    }
}
