package com.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * Default constructor used by Jackson API
     */
    public LocalDateTimeSerializer() {
        this(null);
    }

    /**
     * Customized constructor that accepts specific class. Used by Jackson API.
     *
     * @param clazz class interface to be used in serialization
     */
    public LocalDateTimeSerializer(Class clazz) {
        super(clazz);
    }

    /**
     * Overrides the serialize method with custom implementation. Writes
     * supplied LocalDateTime object as a string using simple DateTimeFormatter
     *
     * @param localDateTime LocalDateTime object to serialized
     * @param jsonGenerator json generator implementation used for serialization
     * @param serializerProvider serializer provider implementation used for serialization
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(localDateTime));
    }
}