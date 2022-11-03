package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rogerfilipes.infrastructure.rest.validator.CheckAtLeastOneNotNull;


@CheckAtLeastOneNotNull(fieldNames = {"email", "password", "username", "bio", "image"})
public class UpdateUser {
    private String email = null;
    private String password = null;
    private String username = null;
    private String bio = null;
    private String image = null;

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("bio")
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
