package com.service.util;

import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.Objects;

@UtilityClass
public class JsonUtils {

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

    public static String readJsonFromUrl(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity);
    }
}
