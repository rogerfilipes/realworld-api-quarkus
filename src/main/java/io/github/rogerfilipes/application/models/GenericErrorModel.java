package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class GenericErrorModel {
    private GenericErrorModelErrors errors = null;

    public GenericErrorModel() {
    }

    public GenericErrorModel(String message) {
        this.errors = new GenericErrorModelErrors().addMessage(message);
    }

    public GenericErrorModel(String... messages) {
        this.errors = new GenericErrorModelErrors().addMessage(messages);
    }

    @Schema(required = true)
    @JsonProperty("errors")
    @NotNull
    public GenericErrorModelErrors getErrors() {
        return errors;
    }

    public GenericErrorModel setErrors(GenericErrorModelErrors errors) {
        this.errors = errors;
        return this;
    }

}
