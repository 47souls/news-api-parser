package com.service.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(JUnit4.class)
public class SimpleApiArticleTest {

    @Test
    public void ableToCreateArticleObject_Test() {
        // arrange

        // act
        SimpleApiArticle simpleApiArticle = new SimpleApiArticle("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));

        // assert
        Assert.assertEquals(simpleApiArticle.getTitle(), "Title");
        Assert.assertEquals(simpleApiArticle.getDescription(), "Description");
        Assert.assertEquals(simpleApiArticle.getUrl(), "http://example.com");
        Assert.assertEquals(simpleApiArticle.getPublishedAt(), LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
    }
}
