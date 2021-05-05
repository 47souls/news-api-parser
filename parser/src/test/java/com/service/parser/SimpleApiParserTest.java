package com.service.parser;

import com.service.model.SimpleApiArticle;
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
public class SimpleApiParserTest {

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
    public void parseFormatSimpleApi_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        SimpleApiParser parserImpl = spy(new SimpleApiParser(logger));
        parserImpl.setArticlesJson(articlesJson);

        SimpleApiArticle article1 = new SimpleApiArticle("Title1", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        SimpleApiArticle article2 = new SimpleApiArticle(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        ArrayList<SimpleApiArticle> articlesList = new ArrayList<>() {{
            add(article1);
            add(article2);
        }};

        PowerMockito.doReturn(articlesList).when(parserImpl, "readArticlesFromJson", articlesJson);

        // act
        List<SimpleApiArticle> result = parserImpl.parse();

        // assert
        // contains valid
        Assert.assertTrue(result.contains(article1));
        // does not contain invalid
        Assert.assertFalse(result.contains(article2));
        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article with title \"Title1\" is valid\n", Level.INFO);
        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING);
    }

    @Test(expected=Exception.class)
    public void readArticlesFromJsonException_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));

        PowerMockito.doThrow(new Exception("Fail")).when(parserImpl, "readArticlesFromJson", articlesJson);

        // act
        Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson);
    }

    @Test
    public void readNewsApiModelFromJson_Test() throws Exception {
        // arrange
        String simpleApiJson = "{\n" +
                "  \"description\": \"The coronavirus\",\n" +
                "  \"publishedAt\": \"2021-07-22 09:53:23.709229\",\n" +
                "  \"title\": \"The latest\",\n" +
                "  \"url\": \"http://example.com\"\n" +
                "}";

        List<SimpleApiArticle> expectedArticleList = Collections.singletonList(new SimpleApiArticle("The latest", "The coronavirus", "http://example.com", LocalDateTime.of(2021, Month.JULY, 22, 9, 53, 23, 709229000)));

        SimpleApiParser parserImpl = spy(new SimpleApiParser(logger));
        parserImpl.setArticlesJson(simpleApiJson);

        // act
        List<SimpleApiArticle> actualArticlesList = Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", simpleApiJson);

        // assert
        Assert.assertEquals(expectedArticleList, actualArticlesList);
    }

    private void logRecordsListContainsMessageWithLogLevel(TestLogHandler testLogHandler, String message, Level level) {
        Assert.assertEquals(testLogHandler.getLogList()
                .stream()
                .filter(logRecord -> logRecord.getMessage().equals(message) && logRecord.getLevel().equals(level))
                .count(), 1);
    }
}
