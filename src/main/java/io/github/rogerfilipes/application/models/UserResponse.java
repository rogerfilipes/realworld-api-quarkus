package io.github.rogerfilipes.application.models;

public class UserResponse<T> {
    private T user = null;

    public UserResponse() {
    }

    public UserResponse(T register) {
        user = register;
    }

    public T getUser() {
        return user;
    }

    public UserResponse<T> setUser(T user) {
        this.user = user;
        return this;
    }
}
