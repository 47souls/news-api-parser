package com.service.processor;

import com.service.model.Article;
import java.io.IOException;
import java.util.List;

/**
 * TODO
 */
public interface Processor {

    /**
     * TODO
     */
    List<Article> process() throws IOException;
}
