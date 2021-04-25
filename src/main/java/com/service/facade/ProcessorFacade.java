package com.service.facade;

import com.service.model.Article;
import com.service.processor.Processor;
import com.service.processor.ProcessorImpl;
import com.service.source.Format;
import com.service.source.Source;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ProcessorFacade {

    private final Processor processor;

    public ProcessorFacade(Source source, Format format, String location, Logger logger) {
        this.processor = new ProcessorImpl(source, format, logger, location);
    }

    public List<Article> process() throws IOException {
        return processor.process();
    }
}
