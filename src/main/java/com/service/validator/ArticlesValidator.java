package com.service.validator;

import com.service.model.Article;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArticlesValidator {

    private final Logger logger;

    /**
     * Checks articles on the subject of validity. Article considered to be
     * valid if it has 4 required fields: title, description, url and publishedAt.
     * All others articles are considered to be invalid. For each article prints a log message.
     * In case of valid article log level is INFO. In case of invalid article log level is WARN.
     * Result list consists only from valid articles and invalid articles are excluded
     *
     * @param articles LocalDateTime object to serialized
     * @return list of valid articles
     */
    List<Article> validateArticles(List<Article> articles) {
        return articles.stream().filter(this::validateArticle).collect(Collectors.toList());
    }

    private boolean validateArticle(Article article) {
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
