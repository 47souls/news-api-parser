package com.service.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(JUnit4.class)
public class ArticleTest {

    @Test
    public void ableToCreateArticleObject_Test() {
        // arrange

        // act
        Article article = new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        // assert
        Assert.assertEquals(article.getTitle(), "Title");
        Assert.assertEquals(article.getDescription(), "Description");
        Assert.assertEquals(article.getUrl(), "http://example.com");
        Assert.assertEquals(article.getPublishedAt(), LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
    }
}
