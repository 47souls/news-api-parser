package com.service.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.time.*;
import java.util.HashMap;

@RunWith(JUnit4.class)
public class LocalDateTimeSimpleFormatDeserializerTest {

    @Test
    public void deserialize_Test() throws IOException {
        // arrange
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeSimpleFormatDeserializer());
        mapper.registerModule(module);

        // act
        HashMap<String, LocalDateTime> localDateTime = mapper.readValue("{\"dateTime\":\"2021-07-22 09:53:23.709229\"}", new TypeReference<>() {});

        // assert
        System.out.println(LocalTime.of(9, 53, 23, 300));

        Assert.assertEquals(localDateTime.get("dateTime"), LocalDateTime.of(2021, Month.JULY, 22, 9, 53, 23, 709229000));
    }
}
