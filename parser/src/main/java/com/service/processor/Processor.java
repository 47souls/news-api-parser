package com.service.processor;

import com.service.model.Article;
import java.io.IOException;
import java.util.List;

public interface Processor {

    List<Article> process() throws IOException;
}
