package io.github.rogerfilipes.application;

import io.github.rogerfilipes.application.models.GenericErrorModel;
import io.github.rogerfilipes.application.models.Profile;
import io.github.rogerfilipes.application.models.ProfileResponse;
import io.github.rogerfilipes.domain.profile.ProfilesService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Path("/profiles")
@Tag(name = "Profile")
public class ProfilesApi {

    private final ProfilesService service;

    private final JsonWebToken jsonWebToken;

    @Inject
    public ProfilesApi(ProfilesService service, JsonWebToken jsonWebToken) {
        this.service = service;
        this.jsonWebToken = jsonWebToken;
    }

    @POST
    @Path("/{username}/follow")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Follow a user", description = "Follow a user by username")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public ProfileResponse followUserByUsername(@PathParam("username") String username, @Context SecurityContext securityContext) {
        return new ProfileResponse().setProfile(service.followUserByUsername(UUID.fromString(jsonWebToken.getSubject()), username));
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a profile", description = "Get a profile of a user of the system. Auth is optional")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public ProfileResponse getProfileByUsername(@PathParam("username") String username, @Context SecurityContext securityContext) {

        Profile profile;
        if (jsonWebToken.getSubject() != null) {
            profile = service.getProfileByUsername(UUID.fromString(jsonWebToken.getSubject()), username);
        } else {
            profile = service.getProfileByUsername(username);
        }

        return new ProfileResponse().setProfile(profile);
    }

    @DELETE
    @Path("/{username}/follow")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Unfollow a user", description = "Unfollow a user by username")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public ProfileResponse unfollowUserByUsername(@PathParam("username") String username, @Context SecurityContext securityContext) {
        return new ProfileResponse().setProfile(service.unfollowUserByUsername(UUID.fromString(jsonWebToken.getSubject()), username));
    }
}
