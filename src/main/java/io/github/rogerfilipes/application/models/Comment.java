package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {
    private Integer id = null;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private Date createdAt = null;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private Date updatedAt = null;
    private String body = null;
    private Profile author = null;


    @Schema(required = true)
    @JsonProperty("id")
    @NotNull
    public Integer getId() {
        return id;
    }

    public Comment setId(Integer id) {
        this.id = id;
        return this;
    }


    @Schema(required = true)
    @JsonProperty("createdAt")
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }


    @Schema(required = true)
    @JsonProperty("updatedAt")
    @NotNull
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Comment setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }


    @Schema(required = true)
    @JsonProperty("body")
    @NotNull
    public String getBody() {
        return body;
    }

    public Comment setBody(String body) {
        this.body = body;
        return this;
    }

    @Schema(required = true)
    @JsonProperty("author")
    @NotNull
    public Profile getAuthor() {
        return author;
    }

    public Comment setAuthor(Profile author) {
        this.author = author;
        return this;
    }

}
