package com.service.parser;

import com.service.model.Article;
import com.service.model.NewsApiModel;
import com.service.source.Format;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.service.util.JsonUtils.readArticleModelFromJson;
import static com.service.util.JsonUtils.readNewsApiModelFromJson;

/**
 * Implementation class that defines logic for parsing articles. Parsing means
 * checking on the subject of validity
 *
 * @see Parser
 */
@RequiredArgsConstructor
public class ParserImpl implements Parser {

    private final Logger logger;
    private final String articlesJson;
    private final Format format;

    /**
     * Parses each article from class level articles list in the following order
     * 1) Read articles from json assuming provided format
     * 2) Only find valid articles among them and return
     *
     * @return list of valid articles
     */
    @Override
    public List<Article> parse() {
        return readArticlesFromJson(articlesJson, format)
            .stream()
            .filter(this::parseArticle)
            .collect(Collectors.toList());
    }

    /**
     * Reads articles from json assuming provided format. 2 supported formats are
     * NEWSAPI and SIMPLE. Depending on provided format, constructs related model -
     * NewsApiModel or Article
     *
     * @param articlesJson string representing articles json
     * @param format format of the articles json
     * @return list of valid articles
     * @see Format
     */
    private List<Article> readArticlesFromJson(String articlesJson, Format format) {
        List<Article> articles = new ArrayList<>();

        try {
            if (format.equals(Format.NEWSAPI)) {
                NewsApiModel newsApiModel = readNewsApiModelFromJson(articlesJson);
                articles = newsApiModel.getArticles();
            } else if (format.equals(Format.SIMPLE)) {
                Article article = readArticleModelFromJson(articlesJson);
                articles = Collections.singletonList(article);
            }
        } catch (Exception e) {
            logger.warning("Unable to read articles from json " + articlesJson + " for format " + format + "\n");
        }

        return articles;
    }

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
    private boolean parseArticle(Article article) {
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
