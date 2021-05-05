package com.service.parser;

import com.service.model.Article;
import com.service.model.NewsApiArticle;
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
        NewsApiArticle article2 = new NewsApiArticle(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        ArrayList<NewsApiArticle> articlesList = new ArrayList<>() {{
            add(article1);
            add(article2);
        }};

        PowerMockito.doReturn(articlesList).when(parserImpl, "readNewsApiModelFromJson", articlesJson);

        // act
        List<NewsApiArticle> result = parserImpl.parse();

        // assert
        // contains valid
        Assert.assertTrue(result.contains(article1));
        // does not contain invalid
        Assert.assertFalse(result.contains(article2));
        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article with title \"Title1\" is valid\n", Level.INFO);
        logRecordsListContainsMessageWithLogLevel(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING);
    }

    @Test
    public void readArticlesFromJsonSuccess_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        List<NewsApiArticle> expectedArticleList = Collections.singletonList(new NewsApiArticle("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));

        PowerMockito.doReturn(expectedArticleList).when(parserImpl, "readNewsApiModelFromJson", articlesJson);

        // act
        List<Article> actualArticleList = Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson);

        // assert
        Assert.assertEquals(expectedArticleList, actualArticleList);
    }

    @Test(expected=Exception.class)
    public void readArticlesFromJsonException_Test() throws Exception {
        // arrange
        String articlesJson = "TestArticlesJson";

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));

        PowerMockito.doThrow(new Exception("Fail")).when(parserImpl, "readNewsApiModelFromJson", articlesJson);

        // act
        Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson);
    }

    @Test
    public void readNewsApiModelFromJson_Test() throws Exception {
        // arrange
        String newsApiJson = "{\n" +
            "  \"status\": \"ok\",\n" +
            "  \"totalResults\": 38,\n" +
            "  \"articles\": [\n" +
            "    {\n" +
            "      \"source\": {\n" +
            "        \"id\": \"cnn\",\n" +
            "        \"name\": \"CNN\"\n" +
            "      },\n" +
            "      \"author\": \"By <a href=\\\"/profiles/julia-hollingsworth\\\">Julia Hollingsworth</a>, CNN\",\n" +
            "      \"title\": \"The latest\",\n" +
            "      \"description\": \"The coronavirus\",\n" +
            "      \"url\": \"http://example.com\",\n" +
            "      \"urlToImage\": \"http://example.com\",\n" +
            "      \"publishedAt\": \"2021-03-24T22:32:00Z\",\n" +
            "      \"content\": \"A senior European diplomat is urging caution over the use of proposed new rules that would govern exports of Covid-19 vaccines to outside of the EU. The rules were announced by the European Commissio… [+2476 chars]\"\n" +
            "    }]\n" +
            "}";

        List<NewsApiArticle> expectedArticleList = Collections.singletonList(new NewsApiArticle("The latest", "The coronavirus", "http://example.com", LocalDateTime.of(2021, Month.MARCH, 24, 22, 32, 0)));

        NewsApiParser parserImpl = spy(new NewsApiParser(logger));

        // act
        List<NewsApiArticle> actualArticlesList = Whitebox.invokeMethod(parserImpl, "readNewsApiModelFromJson", newsApiJson);

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
