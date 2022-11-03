package io.github.rogerfilipes.domain.tag.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class TagsData {
    private UUID id;
    private String tag;

    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public TagsData setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public TagsData setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TagsData setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
