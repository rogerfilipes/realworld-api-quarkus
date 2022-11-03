package io.github.rogerfilipes.domain.article.model;

import java.util.UUID;

public class ArticleTagsData {
    private UUID article;

    private UUID tag;

    public UUID getArticle() {
        return article;
    }

    public ArticleTagsData setArticle(UUID article) {
        this.article = article;
        return this;
    }

    public UUID getTag() {
        return tag;
    }

    public ArticleTagsData setTag(UUID tag) {
        this.tag = tag;
        return this;
    }
}
