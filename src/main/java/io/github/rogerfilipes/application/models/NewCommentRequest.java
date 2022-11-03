package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;


public class NewCommentRequest {
    private NewComment comment = null;


    @Schema(required = true)
    @JsonProperty("comment")
    @NotNull
    public NewComment getComment() {
        return comment;
    }

    public void setComment(NewComment comment) {
        this.comment = comment;
    }

}
