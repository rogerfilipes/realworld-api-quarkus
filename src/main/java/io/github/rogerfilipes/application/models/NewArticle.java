package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class NewArticle {
    private String title = null;
    private String description = null;
    private String body = null;
    private List<String> tagList = new ArrayList<>();


    @Schema(required = true)
    @JsonProperty("title")
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Schema(required = true)
    @JsonProperty("description")
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Schema(required = true)
    @JsonProperty("body")
    @NotNull
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Schema()
    @JsonProperty("tagList")
    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

}
