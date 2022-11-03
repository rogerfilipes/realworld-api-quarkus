package io.github.rogerfilipes.application.models;


public class UserRegistered {
    private String email = null;
    private String username = null;
    private String bio = null;
    private String image = null;
    private String token = null;

    public String getEmail() {
        return email;
    }

    public UserRegistered setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistered setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserRegistered setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getImage() {
        return image;
    }

    public UserRegistered setImage(String image) {
        this.image = image;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserRegistered setToken(String token) {
        this.token = token;
        return this;
    }
}
