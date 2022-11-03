package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class SingleArticleResponse {
    private Article article = null;


    @Schema(required = true)
    @JsonProperty("article")
    @NotNull
    public Article getArticle() {
        return article;
    }

    public SingleArticleResponse setArticle(Article article) {
        this.article = article;
        return this;
    }

}
