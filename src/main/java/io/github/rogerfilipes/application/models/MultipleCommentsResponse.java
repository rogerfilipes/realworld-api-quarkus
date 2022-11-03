package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MultipleCommentsResponse {
    private List<Comment> comments = new ArrayList<>();


    @Schema(required = true)
    @JsonProperty("comments")
    @NotNull
    public List<Comment> getComments() {
        return comments;
    }

    public MultipleCommentsResponse setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

}
