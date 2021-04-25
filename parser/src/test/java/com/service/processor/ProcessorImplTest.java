package com.service.processor;

import com.service.model.Article;
import com.service.source.Format;
import org.junit.Assert;
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
import static com.service.source.Source.FILE;
import static com.service.source.Source.URL;
import static org.powermock.api.mockito.PowerMockito.spy;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ProcessorImpl.class)
public class ProcessorImplTest {

    @Test
    public void processInputSourceUrl_Test() throws Exception {
        // arrange
        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, "location", Logger.getLogger(ProcessorImplTest.class.getName())));
        ArrayList<Article> arrayList = new ArrayList<>() {{
            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        }};

        PowerMockito.doReturn(arrayList).when(processor, "processFromUrl", "location", SIMPLE);

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(arrayList, result);
    }

    @Test
    public void processInputSourceFile_Test() throws Exception {
        // arrange
        ProcessorImpl processor = spy(new ProcessorImpl(FILE, SIMPLE, "location", Logger.getLogger(ProcessorImplTest.class.getName())));
        ArrayList<Article> arrayList = new ArrayList<>() {{
            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
        }};

        PowerMockito.doReturn(arrayList).when(processor, "processFromFile", "location", SIMPLE);

        // act
        List<Article> result = processor.process();

        // assert
        Assert.assertEquals(arrayList, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processCalledUnknownSourceIllegalArgumentExceptionThrown_Test() throws Exception {
        // arrange
        ProcessorImpl processor = new ProcessorImpl(URL, Format.valueOf("Unknown source"), "location", Logger.getLogger(ProcessorImplTest.class.getName()));

        // act
        processor.process();
    }
}
