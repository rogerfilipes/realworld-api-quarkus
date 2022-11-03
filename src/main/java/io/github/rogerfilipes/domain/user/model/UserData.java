package io.github.rogerfilipes.domain.user.model;

import java.util.UUID;

public class UserData {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;

    private String token;

    public UUID getId() {
        return id;
    }

    public UserData setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserData setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserData setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserData setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getImage() {
        return image;
    }

    public UserData setImage(String image) {
        this.image = image;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserData setToken(String token) {
        this.token = token;
        return this;
    }
}
