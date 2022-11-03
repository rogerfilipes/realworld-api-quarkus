package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;


public class NewComment {
    private String body = null;


    @Schema(required = true)
    @JsonProperty("body")
    @NotNull
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
