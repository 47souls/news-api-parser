package com.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.Article;
import com.service.model.NewsApiModel;
import com.service.source.Format;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ParserImpl implements Parser {

    private final Logger logger;
    private final String articlesJson;
    private final Format format;

    /**
     * Checks articles on the subject of validity. Article considered to be
     * valid if it has 4 required fields: title, description, url and publishedAt.
     * All others articles are considered to be invalid. For each article prints a log message.
     * In case of valid article log level is INFO. In case of invalid article log level is WARN.
     * Result list consists only from valid articles and invalid articles are excluded
     *
     * @param .... TODO
     * @return list of valid articles
     */
    @Override
    public List<Article> parse() throws JsonProcessingException {
        List<Article> articles;

        if (format.equals(Format.NEWSAPI)) {
            NewsApiModel newsApiModel = readNewsApiModelFromJson(articlesJson);
            articles = newsApiModel.getArticles();
        } else if (format.equals(Format.SIMPLE)) {
            Article article = readArticleModelFromJson(articlesJson);
            articles = Collections.singletonList(article);
        } else {
            throw new RuntimeException("Format " + format + " not supported");
        }

        return articles.stream().filter(this::parseArticle).collect(Collectors.toList());
    }

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

    // TODO could this be simplied
    private static NewsApiModel readNewsApiModelFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, new TypeReference<>() { });
    }

    private static Article readArticleModelFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, new TypeReference<>() { });
    }
}
