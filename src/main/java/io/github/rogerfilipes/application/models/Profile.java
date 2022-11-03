package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class Profile {
    private String username = null;
    private String bio = null;
    private String image = null;
    private Boolean following = Boolean.FALSE;


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


    @Schema(required = true)
    @JsonProperty("following")
    @NotNull
    public Boolean isFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

}
