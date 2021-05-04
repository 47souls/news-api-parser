package com.service.parser;

import com.service.model.Article;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Base class that defines a contract method for all Parser implementations
 */
public abstract class Parser<T extends Article> {

    private final Logger logger;

    @Setter
    private String articlesJson;

    Parser(Logger logger) {
        this.logger = logger;
    }

    /**
     * Performs parsing of articles. The details of parsing
     * is up to decide for implementations
     *
     * @return a list of parsed articles
     */
    public abstract List<T> parse();

    protected abstract List<T> readArticlesFromJson(String articlesJson);

    /**
     * Checks article on the subject of validity. Article is considered to be
     * valid if it has 4 required fields: title, description, url and publishedAt.
     * All others articles are considered to be invalid. Prints a log message for passed article.
     * In case of valid article log level is INFO. In case of invalid article log level is WARN.
     * Result list consists only from valid articles and invalid articles are excluded
     *
     * @param article article to be parsed
     * @return list of valid articles
     */
    boolean parseArticle(T article) {
        if (Objects.isNull(article.getDescription())) {
            logger.warning("An article description is invalid. Article is skipped\n");
            return false;
        } else if (Objects.isNull(article.getTitle())) {
            logger.warning("An article title is invalid. Article is skipped\n");
            return false;
        } else if (Objects.isNull(article.getUrl())) {
            logger.warning("An article url is invalid. Article is skipped\n");
            return false;
        } else if (Objects.isNull(article.getPublishedAt())) {
            logger.warning("An article published date is invalid. Article is skipped\n");
            return false;
        } else {
            logger.info("An article with title \"" + article.getTitle() + "\" is valid\n");
            return true;
        }
    }
}
