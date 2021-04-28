package com.service.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RunWith(JUnit4.class)
public class LocalDateTimeSerializerTest {

    @Test
    public void serializeZonedDateTime_Test() throws IOException {
        // arrange
        String expectedIsoOffsetDateTimeString = "{\"dateTime\":\"2021-07-29T19:30:40Z\"}";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);
        HashMap<String, LocalDateTime> localDateTimeMap = mapper.readValue(expectedIsoOffsetDateTimeString, new TypeReference<>() {});

        // act
        ObjectMapper mapper2 = new ObjectMapper();
        SimpleModule module2 = new SimpleModule();
        module2.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper2.registerModule(module2);
        String actualIsoOffsetDateTimeString = mapper2.writeValueAsString(localDateTimeMap);

        // assert
        Assert.assertEquals(expectedIsoOffsetDateTimeString, actualIsoOffsetDateTimeString);
    }

//    @Test
//    public void serializeSimpleDateTime_Test() throws IOException {
//        // arrange
//        String expectedIsoOffsetDateTimeString = "{\"dateTime\":\"2021-04-16 09:53:23.709229\"}";
//        ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//        mapper.registerModule(module);
//        HashMap<String, LocalDateTime> localDateTimeMap = mapper.readValue(expectedIsoOffsetDateTimeString, new TypeReference<>() {});
//
//        System.out.println(localDateTimeMap.get("dateTime"));
//
//        // act
//        ObjectMapper mapper2 = new ObjectMapper();
//        SimpleModule module2 = new SimpleModule();
//        module2.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//        mapper2.registerModule(module2);
//        String actualIsoOffsetDateTimeString = mapper2.writeValueAsString(localDateTimeMap);
//
//        // assert
//        Assert.assertEquals(expectedIsoOffsetDateTimeString, actualIsoOffsetDateTimeString);
//    }
}
