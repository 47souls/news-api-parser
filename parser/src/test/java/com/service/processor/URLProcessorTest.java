package com.service.processor;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.service.model.NewsApiArticle;
import com.service.parser.NewsApiParser;
import com.service.parser.Parser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.logging.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URLProcessor.class})
@PowerMockIgnore("javax.net.ssl.*")
public class URLProcessorTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

    @Test
    public void processSuccess_Test() throws Exception {
        // arrange
        String json = "json";
        String location = "http://localhost:8089/getEntity";
        Logger logger = Logger.getLogger(URLProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        URLProcessor<NewsApiArticle> urlProcessor = spy(new URLProcessor<>(location, logger, parser));

        PowerMockito.doReturn(json).when(urlProcessor, "readJsonFromUrl", location);

        // act
        urlProcessor.process();

        // assert
        verifyPrivate(urlProcessor, times(1)).invoke("readJsonFromUrl", location);
        Mockito.verify(parser, times(1)).setArticlesJson(json);
        Mockito.verify(parser, times(1)).parse();
    }

    @Test(expected=Exception.class)
    public void processException_Test() throws Exception {
        // arrange
        String location = "http://localhost:8089/getEntity";
        Logger logger = Logger.getLogger(URLProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        URLProcessor<NewsApiArticle> urlProcessor = spy(new URLProcessor<>(location, logger, parser));

        PowerMockito.doThrow(new Exception("Fail")).when(urlProcessor, "readJsonFromUrl", location);

        // act
        urlProcessor.process();

        // assert
        verifyPrivate(urlProcessor, times(1)).invoke("readJsonFromUrl", location);
    }

    @Test
    public void readJsonFromUrl_Test() throws Exception {
        // arrange
        String expectedJson = "{ \"key\": \"value\"}";
        String location = "http://localhost:8089/getEntity";
        Logger logger = Logger.getLogger(URLProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        stubFor(get(urlEqualTo("/getEntity"))
                .willReturn(aResponse().withBody(expectedJson).withStatus(200)));

        URLProcessor<NewsApiArticle> urlProcessor = spy(new URLProcessor<>(location, logger, parser));

        // act
        String actualJson = Whitebox.invokeMethod(urlProcessor, "readJsonFromUrl", location);

        // assert
        Assert.assertEquals(expectedJson, actualJson);
    }
}
