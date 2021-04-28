package com.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.service.model.Article;
import com.service.model.NewsApiModel;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(JUnit4.class)
public class JsonUtilsTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

    @Test
    public void readJsonFromFile_Test() throws IOException {
        // arrange
        String filename = "simple.json";
        ObjectMapper objectMapper = new ObjectMapper();

        // act
        String json = JsonUtils.readJsonFromFile(filename);
        objectMapper.readValue(json, new TypeReference<>() {});

        // no exception thrown
    }

    @Test
    public void readJsonFromUrl_Test() throws IOException {
        // arrange
        String expectedJson = "{ \"key\": \"value\"}";

        stubFor(get(urlEqualTo("/getEntity"))
                .willReturn(aResponse().withBody(expectedJson).withStatus(200)));

        // act
        String actualJson = JsonUtils.readJsonFromUrl("http://localhost:8089/getEntity");

        // assert
        Assert.assertEquals(expectedJson, actualJson);
    }

    @Test
    public void readNewsApiModelFromJson_Test() throws JsonProcessingException {
        // arrange
        List<Article> articleList = Collections.singletonList(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        NewsApiModel expectedNewsApiModel = new NewsApiModel("ok", 38,  articleList);
        String newsApiJson = new ObjectMapper().writeValueAsString(expectedNewsApiModel);

        // act
        NewsApiModel actualNewsApiModel = JsonUtils.readNewsApiModelFromJson(newsApiJson);

        // assert
        Assert.assertEquals(expectedNewsApiModel, actualNewsApiModel);
    }

    @Test
    public void readArticleModelFromJson_Test() throws JsonProcessingException {
        // arrange
        Article expectedArticle = new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
        String newsApiJson = new ObjectMapper().writeValueAsString(expectedArticle);

        // act
        Article actualArticle = JsonUtils.readArticleModelFromJson(newsApiJson);

        // assert
        Assert.assertEquals(expectedArticle, actualArticle);
    }
}
