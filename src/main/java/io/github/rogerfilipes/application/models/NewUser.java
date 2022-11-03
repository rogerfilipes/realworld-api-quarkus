package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class NewUser {
    private String username = null;
    private String email = null;
    private String password = null;


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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
