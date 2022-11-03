package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class UpdateArticleRequest {
    private UpdateArticle article = null;


    @Schema(required = true)
    @JsonProperty("article")
    @NotNull
    public UpdateArticle getArticle() {
        return article;
    }

    public void setArticle(UpdateArticle article) {
        this.article = article;
    }


}
