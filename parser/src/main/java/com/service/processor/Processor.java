package com.service.processor;

import com.service.model.Article;
import java.io.IOException;
import java.util.List;

/**
 * Interface that defines a contract method for all Processor implementations
 */
public interface Processor {

    /**
     * Performs general processing of articles. The details of processing
     * is up to decide for implementations
     *
     * @return a list of processed articles
     */
    List<Article> process() throws IOException;
}
