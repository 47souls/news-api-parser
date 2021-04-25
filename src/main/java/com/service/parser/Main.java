package com.service.parser;

import com.service.parser.api.NewsApiParser;
import com.service.parser.model.Article;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Main entry to the program. Demonstrates the ability to parse news api responses
     * @param args input arguments of the program
     */
    public static void main(String[] args) throws IOException {

        // Retrieve each file name from program arguments
        for (String fileName: args) {
            String newsApiJson = readJsonFromFile(fileName);
            NewsApiParser newsApiParser = new NewsApiParser(LOGGER);
            printResults(newsApiParser.parseArticlesJson(newsApiJson), fileName);
        }
    }

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

    private static String readJsonFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(new File("files/" + fileName));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder newsApiJsonBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            newsApiJsonBuilder.append(line);
        }

        return newsApiJsonBuilder.toString();
    }
}
