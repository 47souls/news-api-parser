package com.service.processor;

//import com.service.util.JsonUtils;
import org.junit.runner.RunWith;
        import org.powermock.modules.junit4.PowerMockRunner;

//import static com.service.source.Format.SIMPLE;
        import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
//@PrepareForTest({ProcessorImpl.class})
public class ProcessorImplTest {

//    private static MockedStatic<JsonUtils> mockedJsonUtils;
//
//    @Before
//    public void init() {
//        mockedJsonUtils = Mockito.mockStatic(JsonUtils.class);
//    }
//
//    @After
//    public void close() {
//        mockedJsonUtils.close();
//    }
//
//    @Test
//    public void processInputSourceUrl_Test() throws Exception {
//        // arrange
//        String json = "ArticlesJsonTest";
//        String location = "location";
//        ArrayList<Article> articlesList = new ArrayList<>() {{
//            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
//        }};
//
//        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
//
//        mockedJsonUtils.when(() -> JsonUtils.readJsonFromUrl(location)).thenReturn(json);
//
//        ParserImpl parserImpl = mock(ParserImpl.class);
//        whenNew(ParserImpl.class).withArguments(any(), anyString(), any()).thenReturn(parserImpl);
//        when(parserImpl.parse()).thenReturn(articlesList);
//
//        // act
//        List<Article> result = processor.process();
//
//        // assert
//        Assert.assertEquals(articlesList, result);
//    }
//
//    @Test
//    public void processInputSourceUrlExceptionReturnsEmptyList_Test() {
//        // arrange
//        String location = "location";
//        ProcessorImpl processor = spy(new ProcessorImpl(URL, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
//        mockedJsonUtils.when(() -> JsonUtils.readJsonFromUrl(location)).thenThrow(new IOException("Unknown reason"));
//
//        // act
//        List<Article> result = processor.process();
//
//        // assert
//        Assert.assertEquals(0, result.size());
//    }
//
//    @Test
//    public void processInputSourceFile_Test() throws Exception {
//        // arrange
//        String json = "ArticlesJsonTest";
//        String location = "location";
//        ArrayList<Article> articlesList = new ArrayList<>() {{
//            add(new Article("Title", "Description", "http://example.com", LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40)));
//        }};
//
//        ProcessorImpl processor = spy(new ProcessorImpl(FILE, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
//
//        mockedJsonUtils.when(() -> JsonUtils.readJsonFromFile(location)).thenReturn(json);
//
//        ParserImpl parserImpl = mock(ParserImpl.class);
//        whenNew(ParserImpl.class).withArguments(any(), anyString(), any()).thenReturn(parserImpl);
//        when(parserImpl.parse()).thenReturn(articlesList);
//
//        // act
//        List<Article> result = processor.process();
//
//        // assert
//        Assert.assertEquals(articlesList, result);
//    }
//
//    @Test
//    public void processInputSourceFileExceptionReturnsEmptyList_Test() {
//        // arrange
//        String location = "location";
//        ProcessorImpl processor = spy(new ProcessorImpl(FILE, SIMPLE, location, Logger.getLogger(ProcessorImplTest.class.getName())));
//        mockedJsonUtils.when(() -> JsonUtils.readJsonFromFile(location)).thenThrow(new IOException("Unknown reason"));
//
//        // act
//        List<Article> result = processor.process();
//
//        // assert
//        Assert.assertEquals(0, result.size());
//    }
}
