package com.service.processor;

import com.service.model.Article;

import java.util.List;

/**
 * Base class that defines a contract method for all Processor implementations
 */
public abstract class Processor<T extends Article> {

    /**
     * Performs general processing of articles. The details of processing
     * is up to decide for implementations
     *
     * @return a list of processed articles
     */
    public abstract List<T> process();
}
