package com.service.processor;

import com.service.model.NewsApiArticle;
import com.service.parser.NewsApiParser;
import com.service.parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileProcessor.class})
public class FileProcessorTest {

    @Test
    public void processSuccess_Test() throws Exception {
        // arrange
        String json = "json";
        String location = "location";
        Logger logger = Logger.getLogger(FileProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        FileProcessor<NewsApiArticle> fileProcessor = spy(new FileProcessor<>(location, logger, parser));

        PowerMockito.doReturn(json).when(fileProcessor, "readJsonFromFile", location);

        // act
        fileProcessor.process();

        // assert
        verifyPrivate(fileProcessor, times(1)).invoke("readJsonFromFile", location);
        Mockito.verify(parser, times(1)).setArticlesJson(json);
        Mockito.verify(parser, times(1)).parse();
    }

    @Test(expected=Exception.class)
    public void processException_Test() throws Exception {
        // arrange
        String location = "location";
        Logger logger = Logger.getLogger(FileProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        FileProcessor<NewsApiArticle> fileProcessor = spy(new FileProcessor<>(location, logger, parser));

        PowerMockito.doThrow(new Exception("Fail")).when(fileProcessor, "readJsonFromFile", location);

        // act
        fileProcessor.process();

        // assert
        verifyPrivate(fileProcessor, times(1)).invoke("readJsonFromFile", location);
    }

    @Test
    public void readJsonFromFile_Test() throws Exception {
        // arrange
        String expectedJson = "{  \"description\": \"Extend Assignment #1 to support multiple sources and to introduce source processor.\",  \"publishedAt\": \"2021-04-16 09:53:23.709229\",  \"title\": \"Assignment #2\",  \"url\": \"https://canvas.calpoly.edu/courses/55411/assignments/274503\"}";

        String location = "simple.json";
        Logger logger = Logger.getLogger(FileProcessorTest.class.getName());
        Parser<NewsApiArticle> parser = mock(NewsApiParser.class);

        FileProcessor<NewsApiArticle> fileProcessor = spy(new FileProcessor<>(location, logger, parser));

        // act
        String actualJson = Whitebox.invokeMethod(fileProcessor, "readJsonFromFile", location);

        // assert
        Assert.assertEquals(expectedJson, actualJson);
    }
}
