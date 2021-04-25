package com.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.Article;
import com.service.model.NewsApiModel;
import com.service.validator.ArticlesValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsApiParserTest {
// TODO
//    private NewsApiParser newsApiParser;
//
//    @Mock
//    private ArticlesValidator articlesValidator;
//
//    @Before
//    public void init() throws NoSuchFieldException, IllegalAccessException {
//        Logger logger = Logger.getLogger(NewsApiParserTest.class.getName());
//        newsApiParser = new NewsApiParser(logger);
//        setClassField(newsApiParser, articlesValidator);
//    }
//
//    @Test
//    public void parseArticles() throws IOException {
//        // arrange
//        List<Article> articles = new ArrayList<>();
//        articles.add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
//        NewsApiModel newsApiModel = new NewsApiModel("ok", 38, articles);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String newsApiJson = objectMapper.writeValueAsString(newsApiModel);
//
//        // act
//        newsApiParser.parseArticlesJson(newsApiJson);
//
//        // assert
//        verify(articlesValidator, times(1)).validateArticles(articles);
//    }
//
//    private void setClassField(NewsApiParser newsApiParser, ArticlesValidator articlesValidator) throws NoSuchFieldException, IllegalAccessException {
//        Field nameField = newsApiParser.getClass()
//                .getDeclaredField("articlesValidator");
//        nameField.setAccessible(true);
//
//        nameField.set(newsApiParser, articlesValidator);
//    }
}
