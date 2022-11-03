package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
    private String slug = null;
    private String title = null;
    private String description = null;
    private String body = null;
    private List<String> tagList = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private Date createdAt = null;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private Date updatedAt = null;
    private Boolean favorited = Boolean.FALSE;
    private Integer favoritesCount = 0;
    private Profile author = null;

    @Schema(required = true)
    @JsonProperty("slug")
    @NotNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Schema(required = true)
    @JsonProperty("title")
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Schema(required = true)
    @JsonProperty("description")
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Schema(required = true)
    @JsonProperty("body")
    @NotNull
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Schema(required = true)
    @JsonProperty("tagList")
    @NotNull
    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }


    @Schema(required = true)
    @JsonProperty("createdAt")
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Schema(required = true)
    @JsonProperty("updatedAt")
    @NotNull
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Schema(required = true)
    @JsonProperty("favorited")
    @NotNull
    public Boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }


    @Schema(required = true)
    @JsonProperty("favoritesCount")
    @NotNull
    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }


    @Schema(required = true)
    @JsonProperty("author")
    @NotNull
    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

}
