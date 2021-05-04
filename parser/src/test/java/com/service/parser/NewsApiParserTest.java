package com.service.parser;

import com.service.format.Format;
import com.service.model.Article;
import com.service.model.NewsApiArticle;
import com.service.model.NewsApiModel;
import com.service.util.TestLogHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NewsApiParser.class)
public class NewsApiParserTest {

    private TestLogHandler testLogHandler;

    private Logger logger;

    @Before
    public void init() {
        logger = Logger.getLogger(ParserImplTest.class.getName());
        logger.setUseParentHandlers(false);
        testLogHandler = new TestLogHandler();
        logger.addHandler(testLogHandler);
    }

    @Test
    public void parseFormatNewsApi_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));
        parserImpl.setArticlesJson(articlesJson);

        NewsApiArticle article1 = new NewsApiArticle("Title1", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        NewsApiArticle article2 = new NewsApiArticle("Title2", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        ArrayList<NewsApiArticle> articlesList = new ArrayList<>() {{
            add(article1);
            add(article2);
        }};

        PowerMockito.doReturn(articlesList).when(parserImpl, "readNewsApiModelFromJson", articlesJson);
        // valid article
        PowerMockito.doReturn(true).when(parserImpl, "parseArticle", article1);
        // invalid article
        PowerMockito.doReturn(false).when(parserImpl, "parseArticle", article2);

        // act
        List<NewsApiArticle> result = parserImpl.parse();

        // assert
        // contains valid
        Assert.assertTrue(result.contains(article1));
        // does not contain invalid
        Assert.assertFalse(result.contains(article2));
    }

    @Test
    public void readArticlesFromJsonFormatNewsApi_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        String status = "ok";
        int totalResult = 38;
        List<NewsApiArticle> expectedArticleList = Collections.singletonList(new NewsApiArticle("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        NewsApiModel newsApiModel = new NewsApiModel(status, totalResult, expectedArticleList);

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));

        // act
        List<Article> actualArticleList = Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson);

        // assert
        Assert.assertEquals(expectedArticleList, actualArticleList);
    }
//
//    @Test
//    public void parseArticleValidArticle_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, SIMPLE));
//
//        Article article = new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        // act
//        boolean isValidArticle = Whitebox.invokeMethod(parserImpl, "parseArticle", article);
//
//        // assert
//        Assert.assertTrue(isValidArticle);
//        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article with title \"Title\" is valid\n", Level.INFO);
//    }
//
//    @Test
//    public void parseArticleInvalidTitleArticle_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, SIMPLE));
//
//        Article article = new Article(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        // act
//        boolean isValidArticle = Whitebox.invokeMethod(parserImpl, "parseArticle", article);
//
//        // assert
//        Assert.assertFalse(isValidArticle);
//        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING);
//    }
//
//    @Test
//    public void parseArticleInvalidDescriptionArticle_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, SIMPLE));
//
//        Article article = new Article("Title", null, "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        // act
//        boolean isValidArticle = Whitebox.invokeMethod(parserImpl, "parseArticle", article);
//
//        // assert
//        Assert.assertFalse(isValidArticle);
//        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article description is invalid. Article is skipped\n", Level.WARNING);
//    }
//
//    @Test
//    public void parseArticleInvalidUrlArticle_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, SIMPLE));
//
//        Article article = new Article("Title", "Description", null, LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        // act
//        boolean isValidArticle = Whitebox.invokeMethod(parserImpl, "parseArticle", article);
//
//        // assert
//        Assert.assertFalse(isValidArticle);
//        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article url is invalid. Article is skipped\n", Level.WARNING);
//    }
//
//    @Test
//    public void parseArticleInvalidPublishedArticle_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, SIMPLE));
//
//        Article article = new Article("Title", "Description", "http://example.com", null);
//
//        // act
//        boolean isValidArticle = Whitebox.invokeMethod(parserImpl, "parseArticle", article);
//
//        // assert
//        Assert.assertFalse(isValidArticle);
//        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article published date is invalid. Article is skipped\n", Level.WARNING);
//    }
//
//    private void logRecordsListContainsMessageWithLogLevel(TestLogHandler testLogHandler, String message, Level level) {
//        Assert.assertEquals(testLogHandler.getLogList()
//                .stream()
//                .filter(logRecord -> logRecord.getMessage().equals(message) && logRecord.getLevel().equals(level))
//                .count(), 1);
//    }
}
