package io.github.rogerfilipes.domain.article.model;

import io.github.rogerfilipes.domain.authorization.Ownership;

import java.time.LocalDateTime;
import java.util.UUID;

public class ArticleData implements Ownership {
    private UUID id;

    private UUID author;
    private String slug;
    private String title;
    private String description;
    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public UUID getId() {
        return id;
    }

    public ArticleData setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public ArticleData setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArticleData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ArticleData setBody(String body) {
        this.body = body;
        return this;
    }

    public UUID getAuthor() {
        return author;
    }

    public ArticleData setAuthor(UUID author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ArticleData setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ArticleData setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public UUID getOwner() {
        return author;
    }
}
