package com.service.parser;

import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

//import static com.service.source.Format.NEWSAPI;
//import static com.service.source.Format.SIMPLE;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
//@PrepareForTest(ParserImpl.class)
public class ParserImplTest {

//    private TestLogHandler testLogHandler;
//
//    private Logger logger;
//
//    private static MockedStatic<JsonUtils> mockedJsonUtils;
//
//    @Before
//    public void init() {
//        mockedJsonUtils = Mockito.mockStatic(JsonUtils.class);
//        logger = Logger.getLogger(ParserImplTest.class.getName());
//        logger.setUseParentHandlers(false);
//        testLogHandler = new TestLogHandler();
//        logger.addHandler(testLogHandler);
//    }
//
//    @After
//    public void close() {
//        mockedJsonUtils.close();
//    }
//
//    @Test
//    public void parseFormatNewsApi_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        Format format = Format.NEWSAPI;
//
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, format));
//
//        Article article1 = new Article("Title1", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//        Article article2 = new Article("Title2", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        ArrayList<Article> articlesList = new ArrayList<>() {{
//            add(article1);
//            add(article2);
//        }};
//
//        PowerMockito.doReturn(articlesList).when(parserImpl, "readArticlesFromJson", articlesJson, format);
//        // valid article
//        PowerMockito.doReturn(true).when(parserImpl, "parseArticle", article1);
//        // invalid article
//        PowerMockito.doReturn(false).when(parserImpl, "parseArticle", article2);
//
//        // act
//        List<Article> result = parserImpl.parse();
//
//        // assert
//        // contains valid
//        Assert.assertTrue(result.contains(article1));
//        // does not contain invalid
//        Assert.assertFalse(result.contains(article2));
//    }
//
//    @Test
//    public void parseArticlesFormatSimple_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        Format format = SIMPLE;
//
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, format));
//
//        Article article1 = new Article("Title1", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//        Article article2 = new Article("Title2", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        ArrayList<Article> articlesList = new ArrayList<>() {{
//            add(article1);
//            add(article2);
//        }};
//
//        PowerMockito.doReturn(articlesList).when(parserImpl, "readArticlesFromJson", articlesJson, format);
//        // valid article
//        PowerMockito.doReturn(true).when(parserImpl, "parseArticle", article1);
//        // invalid article
//        PowerMockito.doReturn(false).when(parserImpl, "parseArticle", article2);
//
//        // act
//        List<Article> result = parserImpl.parse();
//
//        // assert
//        // contains valid
//        Assert.assertTrue(result.contains(article1));
//        // does not contain invalid
//        Assert.assertFalse(result.contains(article2));
//    }
//
//    @Test
//    public void readArticlesFromJsonFormatNewsApi_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        Format format = NEWSAPI;
//
//        String status = "ok";
//        int totalResult = 38;
//        List<Article> expectedArticleList = Collections.singletonList(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
//        NewsApiModel newsApiModel = new NewsApiModel(status, totalResult,  expectedArticleList);
//
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, format));
//
//        mockedJsonUtils.when(() -> JsonUtils.readNewsApiModelFromJson(articlesJson)).thenReturn(newsApiModel);
//
//        // act
//        List<Article> actualArticleList = Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson, format);
//
//        // assert
//        Assert.assertEquals(expectedArticleList, actualArticleList);
//    }
//
//    @Test
//    public void readArticlesFromJsonFormatSimple_Test() throws Exception {
//        // arrange
//        String articlesJson = "TestArticlesJson";
//        Format format = SIMPLE;
//
//        Article expectedArticle = new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
//
//        ParserImpl parserImpl = spy(new ParserImpl(logger, articlesJson, format));
//
//        mockedJsonUtils.when(() -> JsonUtils.readArticleModelFromJson(articlesJson)).thenReturn(expectedArticle);
//
//        // act
//        List<Article> actualArticleList = Whitebox.invokeMethod(parserImpl, "readArticlesFromJson", articlesJson, format);
//
//        // assert
//        Assert.assertEquals(Collections.singletonList(expectedArticle), actualArticleList);
//    }
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
