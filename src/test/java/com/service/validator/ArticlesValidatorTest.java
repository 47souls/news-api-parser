package com.service.validator;

import com.service.model.Article;
import com.service.util.TestLogHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class ArticlesValidatorTest {

    private ArticlesValidator articlesValidator;

    private TestLogHandler testLogHandler;

    @Before
    public void init() {
        Logger logger = Logger.getLogger(ArticlesValidator.class.getName());
        logger.setUseParentHandlers(false);
        testLogHandler = new TestLogHandler();
        logger.addHandler(testLogHandler);
        articlesValidator = new ArticlesValidator(logger);
    }

    @Test
    public void allSuccessArticles_Test() {
        // arrange
        Article article1 = new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        List<Article> articleList = Arrays.asList(article1, article2, article3);

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertEquals(articleList, articlesReturned);

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void allSuccessExceptOneThatMissesTitle_Test() {
        // arrange
        Article missingTitleArticle = new Article(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        List<Article> articleList = Arrays.asList(missingTitleArticle, article2, article3);

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.contains(missingTitleArticle));
        Assert.assertTrue(articlesReturned.contains(article2));
        Assert.assertTrue(articlesReturned.contains(article3));

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void allSuccessExceptOneThatMissesDescription_Test() {
        // arrange
        Article missingDescriptionArticle = new Article("Title", null, "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        List<Article> articleList = Arrays.asList(missingDescriptionArticle, article2, article3);

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.contains(missingDescriptionArticle));
        Assert.assertTrue(articlesReturned.contains(article2));
        Assert.assertTrue(articlesReturned.contains(article3));

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article description is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void allSuccessExceptOneThatMissesUrl_Test() {
        Article missingUrlArticle = new Article("Title", "Description", null, LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        List<Article> articleList = Arrays.asList(missingUrlArticle, article2, article3);

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.contains(missingUrlArticle));
        Assert.assertTrue(articlesReturned.contains(article2));
        Assert.assertTrue(articlesReturned.contains(article3));

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article url is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void allSuccessExceptOneThatMissesDatePublished_Test() {
        Article missingPublishedAtArticle = new Article("Title", "Description", "http://example.com", null);
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        List<Article> articleList = Arrays.asList(missingPublishedAtArticle, article2, article3);

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.contains(missingPublishedAtArticle));
        Assert.assertTrue(articlesReturned.contains(article2));
        Assert.assertTrue(articlesReturned.contains(article3));

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article published date is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void articlesListContainsDifferentTypesOfInvalidArticles_Test() {
        // invalid articles
        Article missingTitleArticle = new Article(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingDescriptionArticle = new Article("Title", null, "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingUrlArticle = new Article("Title", "Description", null, LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingPublishedAtArticle = new Article("Title", "Description", "http://example.com", null);

        // valid articles
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        List<Article> invalidArticles = Arrays.asList(missingTitleArticle, missingDescriptionArticle, missingUrlArticle, missingPublishedAtArticle);
        List<Article> validArticles =  Arrays.asList(article2, article3);
        List<Article> articleList = Stream.concat(invalidArticles.stream(), validArticles.stream()).collect(Collectors.toList());

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.containsAll(invalidArticles));
        Assert.assertTrue(articlesReturned.containsAll(validArticles));

        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());

        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article description is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article url is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article published date is invalid. Article is skipped\n", Level.WARNING, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    @Test
    public void articlesListContainsDifferentTypesOfRepeatingInvalidArticles_Test() {
        // invalid articles
        Article missingTitleArticle = new Article(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingTitle2Article = new Article(null, "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingDescriptionArticle = new Article("Title", null, "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingDescription2Article = new Article("Title", null, "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingUrlArticle = new Article("Title", "Description", null, LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingUrl2Article = new Article("Title", "Description", null, LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article missingPublishedAtArticle = new Article("Title", "Description", "http://example.com", null);
        Article missingPublished2AtArticle = new Article("Title", "Description", "http://example.com", null);

        // valid articles
        Article article2 = new Article("Title2", "Description2", "http://example2.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        Article article3 = new Article("Title3", "Description3", "http://example3.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        List<Article> invalidArticles = Arrays.asList(missingTitleArticle, missingTitle2Article, missingDescriptionArticle, missingDescription2Article,
                                                      missingUrlArticle, missingUrl2Article, missingPublishedAtArticle, missingPublished2AtArticle);
        List<Article> validArticles =  Arrays.asList(article2, article3);
        List<Article> articleList = Stream.concat(invalidArticles.stream(), validArticles.stream()).collect(Collectors.toList());

        // act
        List<Article> articlesReturned = articlesValidator.validateArticles(articleList);

        // assert
        Assert.assertFalse(articlesReturned.containsAll(invalidArticles));
        Assert.assertTrue(articlesReturned.containsAll(validArticles));

        // all articles info was logged
        Assert.assertEquals(testLogHandler.getLogList().size(), articleList.size());

        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article title is invalid. Article is skipped\n", Level.WARNING, 2);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article description is invalid. Article is skipped\n", Level.WARNING, 2);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article url is invalid. Article is skipped\n", Level.WARNING, 2);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article published date is invalid. Article is skipped\n", Level.WARNING, 2);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title2\" is valid\n", Level.INFO, 1);
        logRecordsListContainsMessageWithLogLevelTimes(testLogHandler,"An article with title \"Title3\" is valid\n", Level.INFO, 1);
    }

    private void logRecordsListContainsMessageWithLogLevelTimes(TestLogHandler testLogHandler, String message, Level level, int times) {
        Assert.assertEquals(testLogHandler.getLogList()
                .stream()
                .filter(logRecord -> logRecord.getMessage().equals(message) && logRecord.getLevel().equals(level))
                .count(), times);
    }
}
