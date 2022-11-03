package io.github.rogerfilipes.application;


import io.github.rogerfilipes.application.models.GenericErrorModel;
import io.github.rogerfilipes.application.models.UpdateUserRequest;
import io.github.rogerfilipes.application.models.UserRegistered;
import io.github.rogerfilipes.application.models.UserResponse;
import io.github.rogerfilipes.domain.user.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;


@Path("/user")
@Tag(name = "User and Authentication")
public class UserApi {

    private final JsonWebToken jsonWebToken;

    private final UserService userService;


    @Inject
    public UserApi(JsonWebToken jsonWebToken, UserService userService) {
        this.jsonWebToken = jsonWebToken;
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "GetCurrentUser", summary = "Gets the currently user", description = "Gets the currently logged-in user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public UserResponse<UserRegistered> getCurrentUser(@Context SecurityContext securityContext) {
        return new UserResponse<>(userService.getCurrentUser(UUID.fromString(jsonWebToken.getSubject())));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "UpdateCurrentUser", summary = "update current user", description = "Updated user information for current user")
    @RequestBody(required = true, description = "User details to update. At least **one** field is required.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UpdateUserRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public UserResponse<UserRegistered> updateCurrentUser(@NotNull @Valid UpdateUserRequest request, @Context SecurityContext securityContext) {
        return new UserResponse<>(userService.updateCurrentUser(UUID.fromString(jsonWebToken.getSubject()), request.getUser()));
    }
}
