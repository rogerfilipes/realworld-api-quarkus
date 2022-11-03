package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class SingleCommentResponse {
    private Comment comment = null;


    @Schema(required = true)
    @JsonProperty("comment")
    @NotNull
    public Comment getComment() {
        return comment;
    }

    public SingleCommentResponse setComment(Comment comment) {
        this.comment = comment;
        return this;
    }

}
