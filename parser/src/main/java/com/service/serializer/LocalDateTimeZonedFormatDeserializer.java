package com.service.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson helper deserializer class. Helps to convert NEWS API model's published date
 *
 * @see StdDeserializer
 * @see DateTimeFormatter
 */
public class LocalDateTimeZonedFormatDeserializer extends StdDeserializer<LocalDateTime> {

    private DateTimeFormatter zonedDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * Default constructor used by Jackson API
     */
    public LocalDateTimeZonedFormatDeserializer() {
        this(null);
    }

    /**
     * Customized constructor that accepts specific class. Used by Jackson API.
     *
     * @param clazz class interface to be used in serialization
     */
    private LocalDateTimeZonedFormatDeserializer(Class<?> clazz) {
        super(clazz);
    }

    /**
     *  Overrides the deserialize method with custom implementation. Retrieves
     *  the test and parses this text as LocalDateTime using DateTimeFormatter.ISO_OFFSET_DATE_TIME
     *  formatter
     *
     * @param jsonParser json parser implementation used for deserialization
     * @param context deserialization context used for deserialization
     * @return parsed LocalDateTime object
     */
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        return LocalDateTime.parse(jsonParser.getText(), zonedDateFormatter);
    }
}
