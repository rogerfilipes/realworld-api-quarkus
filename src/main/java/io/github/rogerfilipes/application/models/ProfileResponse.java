package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class ProfileResponse {
    private Profile profile = null;


    @Schema(required = true)
    @JsonProperty("profile")
    @NotNull
    public Profile getProfile() {
        return profile;
    }

    public ProfileResponse setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

}
