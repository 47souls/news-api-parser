package com.service.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(JUnit4.class)
public class NewsApiArticleTest {

    @Test
    public void ableToCreateArticleObject_Test() {
        // arrange

        // act
        NewsApiArticle newsApiArticle = new NewsApiArticle("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        // assert
        Assert.assertEquals(newsApiArticle.getTitle(), "Title");
        Assert.assertEquals(newsApiArticle.getDescription(), "Description");
        Assert.assertEquals(newsApiArticle.getUrl(), "http://example.com");
        Assert.assertEquals(newsApiArticle.getPublishedAt(), LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
    }
}
