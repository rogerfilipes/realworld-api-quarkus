package io.github.rogerfilipes.domain.user.model;

import java.util.UUID;

public class FollowData {
    private UUID user;
    private UUID follows;

    public UUID getUser() {
        return user;
    }

    public FollowData setUser(UUID user) {
        this.user = user;
        return this;
    }

    public UUID getFollows() {
        return follows;
    }

    public FollowData setFollows(UUID follows) {
        this.follows = follows;
        return this;
    }
}
