package com.service.parser;

import com.service.model.Article;
import com.service.processor.ProcessorImpl;
import com.service.processor.ProcessorImplTest;
import com.service.source.Format;
import com.service.util.TestLogHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.service.source.Format.SIMPLE;
import static com.service.source.Source.URL;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProcessorImpl.class)
public class ParserImplTest {

    private TestLogHandler testLogHandler;

    private Logger logger;

    private ParserImpl parserImpl;

    @Before
    public void init() {
        logger = Logger.getLogger(ParserImplTest.class.getName());
        logger.setUseParentHandlers(false);
        testLogHandler = new TestLogHandler();
        logger.addHandler(testLogHandler);
    }

    @Test
    public void parseArticlesFormatNewsApi_Test() throws Exception {
        // arrange
        parserImpl = new ParserImpl(logger, "", Format.NEWSAPI);

        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, "location", Logger.getLogger(ProcessorImplTest.class.getName())));
        ArrayList<Article> arrayList = new ArrayList<>() {{
            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        }};

        PowerMockito.doReturn(arrayList).when(processor, "processFromUrl", "location", SIMPLE);

        // act
        List<Article> result = parserImpl.parse();

        // assert
        Assert.assertEquals(arrayList, result);
    }

    @Test
    public void parseArticlesFormatSimple_Test() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void parseCalledUnknownFormatIllegalArgumentExceptionThrown_Test() throws Exception {
        // arrange
        parserImpl = new ParserImpl(logger, "", Format.valueOf("Unknown format"));

        // act
        parserImpl.parse();
    }

    @Test
    public void parseArticleValidArticle_Test() {

    }

    @Test
    public void parseArticleInvalidTitleArticle_Test() {

    }

    @Test
    public void parseArticleInvalidDescriptionArticle_Test() {

    }

    @Test
    public void parseArticleInvalidUrlArticle_Test() {

    }

    @Test
    public void parseArticleInvalidPublishedArticle_Test() {

    }

}
