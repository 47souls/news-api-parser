package com.service.parser.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

@RunWith(JUnit4.class)
public class LocalDateTimeDeserializerTest {

    @Test
    public void deserialize_Test() throws IOException {
        // arrange
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);

        // act
        HashMap<String, LocalDateTime> localDateTime = mapper.readValue("{\"dateTime\":\"2021-07-29T19:30:40Z\"}", new TypeReference<>() {});

        // assert
        Assert.assertEquals(localDateTime.get("dateTime"), LocalDateTime.of(2021, Month.JULY, 29, 19, 30, 40));
    }
}
