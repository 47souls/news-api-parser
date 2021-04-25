package com.service.parser.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    private DateTimeFormatter formatter =  DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Default constructor used by Jackson API
     */
    public LocalDateTimeDeserializer() {
        this(null);
    }

    /**
     * Customized constructor that accepts specific class. Used by Jackson API.
     *
     * @param clazz class interface to be used in serialization
     */
    private LocalDateTimeDeserializer(Class<?> clazz) {
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
        String date = jsonParser.getText();
        return LocalDateTime.parse(date, formatter);
    }
}
