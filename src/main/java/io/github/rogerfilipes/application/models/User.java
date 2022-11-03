package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;


public class User {
    private String email = null;
    private String password = null;
    private String username = null;
    private String bio = null;
    private String image = null;

    private String token = null;


    @Schema(required = true)
    @JsonProperty("email")
    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Schema(required = true)
    @JsonProperty("password")
    @NotNull
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Schema(required = true)
    @JsonProperty("username")
    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Schema(required = true)
    @JsonProperty("bio")
    @NotNull
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    @Schema(required = true)
    @JsonProperty("image")
    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Schema()
    @JsonProperty("token")
    @NotNull
    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

}
