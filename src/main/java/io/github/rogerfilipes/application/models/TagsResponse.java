package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class TagsResponse {
    private List<String> tags = new ArrayList<>();


    @Schema(required = true)
    @JsonProperty("tags")
    @NotNull
    public List<String> getTags() {
        return tags;
    }

    public TagsResponse setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

}
