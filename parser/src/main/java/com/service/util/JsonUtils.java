package com.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.Article;
import com.service.model.NewsApiModel;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Utility class used to make basic json manipulations. Those include
 * 1) Reading json from file
 * 2) Retrieving json for provided url
 * 3) Convert JSON to common news api models
 *
 */
@UtilityClass
public class JsonUtils {

    /**
     * Reads a json from provided file
     *
     * @param fileName name of the file where json
     * @return parsed string json
     */
    public static String readJsonFromFile(String fileName) throws IOException {
        ClassLoader classLoader = JsonUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        return jsonBuilder.toString();
    }

    /**
     * Does the REST api call to the provided url and retrieves it's body as a string
     *
     * @param url url which will be used to make a REST api call
     * @return parsed string json
     */
    public static String readJsonFromUrl(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity);
    }

    /**
     * Converts provided json string to the news api model
     *
     * @param jsonString json string to be parsed
     * @return parsed news api model
     */
    public static NewsApiModel readNewsApiModelFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, new TypeReference<>() { });
    }

    /**
     * Converts provided json string to the article model
     *
     * @param jsonString json string to be parsed
     * @return parsed article model
     */
    public static Article readArticleModelFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, new TypeReference<>() { });
    }
}
