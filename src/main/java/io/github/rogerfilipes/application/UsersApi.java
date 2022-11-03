package io.github.rogerfilipes.application;


import io.github.rogerfilipes.application.models.*;
import io.github.rogerfilipes.domain.user.UsersService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;


@Path("/users")
@Tag(name = "User and Authentication")
public class UsersApi {

    private final UsersService service;

    @Inject
    public UsersApi(UsersService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Register a new user", description = "Register a new user")
    @RequestBody(required = true, description = "Details of the new user to register", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewUserRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public UserResponse<UserRegistered> createUser(@Valid NewUserRequest request, @Context SecurityContext securityContext) {
        return new UserResponse<>(service.register(request));
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Existing user login", description = "Login for existing user")
    @RequestBody(required = true, description = "Credentials to use", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginUserRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public UserResponse<UserRegistered> login(@Valid LoginUserRequest request, @Context SecurityContext securityContext) {
        return new UserResponse<>(service.login(request));
    }
}
