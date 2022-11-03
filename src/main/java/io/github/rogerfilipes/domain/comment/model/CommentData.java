package io.github.rogerfilipes.domain.comment.model;

import io.github.rogerfilipes.domain.authorization.Ownership;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentData implements Ownership {
    private int id;
    private UUID author;

    private UUID article;
    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() {
        return id;
    }

    public CommentData setId(int id) {
        this.id = id;
        return this;
    }

    public UUID getAuthor() {
        return author;
    }

    public CommentData setAuthor(UUID author) {
        this.author = author;
        return this;
    }

    public UUID getArticle() {
        return article;
    }

    public CommentData setArticle(UUID article) {
        this.article = article;
        return this;
    }

    public String getBody() {
        return body;
    }

    public CommentData setBody(String body) {
        this.body = body;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CommentData setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public CommentData setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public UUID getOwner() {
        return author;
    }
}
