package com.service.processor;

import com.service.model.Article;
import com.service.parser.ParserImpl;
import com.service.util.JsonUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.service.source.Format.SIMPLE;
import static com.service.source.Source.FILE;
import static com.service.source.Source.URL;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProcessorImpl.class})
public class ProcessorImplTest {

    private static MockedStatic<JsonUtils> mockedJsonUtils;

    @Before
    public void init() {
        mockedJsonUtils = Mockito.mockStatic(JsonUtils.class);
    }

    @After
    public void close() {
        mockedJsonUtils.close();
    }

    @Test
    public void processInputSourceUrl_Test() throws Exception {
        // arrange
        String json = "ArticlesJsonTest";
        String location = "location";
        ArrayList<Article> articlesList = new ArrayList<>() {{
            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        }};

        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));

        mockedJsonUtils.when(() -> JsonUtils.readJsonFromUrl(location)).thenReturn(json);

        ParserImpl parserImpl = mock(ParserImpl.class);
        whenNew(ParserImpl.class).withArguments(any(), anyString(), any()).thenReturn(parserImpl);
        when(parserImpl.parse()).thenReturn(articlesList);

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(articlesList, result);
    }

    @Test
    public void processInputSourceUrlExceptionReturnsEmptyList_Test() {
        // arrange
        String location = "location";
        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
        mockedJsonUtils.when(() -> JsonUtils.readJsonFromUrl(location)).thenThrow(new IOException("Unknown reason"));

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void processInputSourceFile_Test() throws Exception {
        // arrange
        String json = "ArticlesJsonTest";
        String location = "location";
        ArrayList<Article> articlesList = new ArrayList<>() {{
            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        }};

        ProcessorImpl processor = spy(new ProcessorImpl(FILE, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));

        mockedJsonUtils.when(() -> JsonUtils.readJsonFromFile(location)).thenReturn(json);

        ParserImpl parserImpl = mock(ParserImpl.class);
        whenNew(ParserImpl.class).withArguments(any(), anyString(), any()).thenReturn(parserImpl);
        when(parserImpl.parse()).thenReturn(articlesList);

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(articlesList, result);
    }

    @Test
    public void processInputSourceFileExceptionReturnsEmptyList_Test() {
        // arrange
        String location = "location";
        ProcessorImpl processor = spy(new ProcessorImpl(FILE, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
        mockedJsonUtils.when(() -> JsonUtils.readJsonFromFile(location)).thenThrow(new IOException("Unknown reason"));

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(0, result.size());
    }
}
