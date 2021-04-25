package com.service.parser.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TestLogHandler extends Handler {

    @Getter
    private List<LogRecord> logList = new ArrayList<>();

    @Override
    public void publish(LogRecord record) {
        this.logList.add(record);
    }

    @Override
    public void flush() {
        // omitted
    }

    @Override
    public void close() throws SecurityException {
        // omitted
    }
}
