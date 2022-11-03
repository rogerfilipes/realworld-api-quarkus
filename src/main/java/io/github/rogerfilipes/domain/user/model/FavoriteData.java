package io.github.rogerfilipes.domain.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FavoriteData {
    private UUID article;
    private UUID user;
    private LocalDateTime createdAt;

    public FavoriteData() {
    }

    public FavoriteData(UUID article, UUID user, LocalDateTime createdAt) {
        this.article = article;
        this.user = user;
        this.createdAt = createdAt;
    }

    public UUID getArticle() {
        return article;
    }

    public FavoriteData setArticle(UUID article) {
        this.article = article;
        return this;
    }

    public UUID getUser() {
        return user;
    }

    public FavoriteData setUser(UUID user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public FavoriteData setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
