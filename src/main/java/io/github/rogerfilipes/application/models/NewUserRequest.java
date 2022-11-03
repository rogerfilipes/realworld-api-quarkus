package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class NewUserRequest {
    private NewUser user = null;


    @Schema(required = true)
    @JsonProperty("user")
    @NotNull
    @Valid
    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }

}
