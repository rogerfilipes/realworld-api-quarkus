package io.github.rogerfilipes.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenericErrorModelErrors {
    private List<String> body = new ArrayList<>();

    public GenericErrorModelErrors() {
    }

    public GenericErrorModelErrors(String... messages) {
        this.addMessage(messages);
    }

    public GenericErrorModelErrors addMessage(String... messages) {
        this.body.addAll(Arrays.stream(messages).filter(Objects::nonNull).collect(Collectors.toList()));
        return this;
    }

    @Schema(required = true)
    @JsonProperty("body")
    @NotNull
    public List<String> getBody() {
        return body;
    }

    public GenericErrorModelErrors setBody(List<String> body) {
        this.body = body;
        return this;
    }

}
