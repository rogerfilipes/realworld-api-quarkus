package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class NewArticleRequest {
    private NewArticle article = null;


    @Schema(required = true)
    @JsonProperty("article")
    @NotNull
    public NewArticle getArticle() {
        return article;
    }

    public void setArticle(NewArticle article) {
        this.article = article;
    }

}
