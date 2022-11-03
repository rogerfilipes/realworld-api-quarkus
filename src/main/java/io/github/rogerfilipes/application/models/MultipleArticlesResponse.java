package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MultipleArticlesResponse {
    private List<Article> articles = new ArrayList<>();
    private Integer articlesCount = null;

    @Schema(required = true)
    @JsonProperty("articles")
    @NotNull
    public List<Article> getArticles() {
        return articles;
    }

    public MultipleArticlesResponse setArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }


    @Schema(required = true)
    @JsonProperty("articlesCount")
    @NotNull
    public Integer getArticlesCount() {
        return articlesCount;
    }

    public MultipleArticlesResponse setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
        return this;
    }

}
