package com.service.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class NewsApiModelTest {

    @Test
    public void ableToCreateNewsApiModelObject_Test() {
        // arrange
        String status = "ok";
        int totalResult = 38;
        List<NewsApiArticle> newsApiArticleList = Collections.singletonList(new NewsApiArticle("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));

        // act
        NewsApiModel newsApiModel = new NewsApiModel(status, totalResult, newsApiArticleList);

        Assert.assertEquals(newsApiModel.getStatus(), status);
        Assert.assertEquals(newsApiModel.getTotalResults(), totalResult);
        Assert.assertEquals(newsApiModel.getNewsApiArticles(), newsApiArticleList);
    }
}
