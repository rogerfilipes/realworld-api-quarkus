package io.github.rogerfilipes.application;

import io.github.rogerfilipes.application.models.GenericErrorModel;
import io.github.rogerfilipes.application.models.TagsResponse;
import io.github.rogerfilipes.domain.tag.TagsService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/tags")
@Tag(name = "Tags")
public class TagsApi {

    @Inject
    TagsService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get tags", description = "Get tags. Auth not required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagsResponse.class))),

            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public TagsResponse tagsGet(@Context SecurityContext securityContext) {
        return new TagsResponse().setTags(service.getAllTags());
    }
}
