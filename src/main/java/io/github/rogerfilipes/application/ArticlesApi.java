package io.github.rogerfilipes.application;


import io.github.rogerfilipes.application.models.*;
import io.github.rogerfilipes.domain.article.model.ArticleCriteriaFilter;
import io.github.rogerfilipes.domain.article.ArticlesService;
import io.github.rogerfilipes.domain.comment.CommentsService;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;
import java.util.UUID;

@Path("/articles")
@Tag(name = "Articles")
public class ArticlesApi {

    private final JsonWebToken jsonWebToken;

    private final ArticlesService articlesService;

    private final CommentsService commentsService;


    @Inject
    public ArticlesApi(JsonWebToken jsonWebToken, ArticlesService articlesService, CommentsService commentsService) {
        this.jsonWebToken = jsonWebToken;
        this.articlesService = articlesService;
        this.commentsService = commentsService;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create an article", description = "Create an article. Auth is required")
    @RequestBody(required = true, description = "Article to create", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewArticleRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleArticleResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public SingleArticleResponse createArticle(@Valid NewArticleRequest request, @Context SecurityContext securityContext) {
        return new SingleArticleResponse().setArticle(articlesService.createArticle(getLoggedUserId(), request));
    }

    @GET
    @Path("/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get an article", description = "Get an article. Auth not required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleArticleResponse.class))),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public SingleArticleResponse getArticle(@PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new SingleArticleResponse().setArticle(articlesService.getArticle(getLoggedUserId(), slug));
    }

    @DELETE
    @Path("/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete an article", description = "Delete an article. Auth is required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public Response deleteArticle(@PathParam("slug") String slug, @Context SecurityContext securityContext) {
        articlesService.deleteArticle(getLoggedUserId(), slug);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get recent articles globally", description = "Get most recent articles globally. Use query parameters to filter results. Auth is optional")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipleArticlesResponse.class))),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public MultipleArticlesResponse getArticles(@QueryParam("tag") String tag, @QueryParam("author") String author, @QueryParam("favorited") String favorited, @DefaultValue("20") @QueryParam("limit") Integer limit, @DefaultValue("0") @QueryParam("offset") Integer offset, @Context SecurityContext securityContext) {

        ArticleCriteriaFilter criteriaFilter = new ArticleCriteriaFilter(tag, author, favorited, limit, offset);
        PageResult<Article> articles = articlesService.getArticles(getLoggedUserId(), criteriaFilter);
        return new MultipleArticlesResponse().setArticles(articles.getResult()).setArticlesCount(articles.getTotal());

    }

    private UUID getLoggedUserId() {
        return Optional.ofNullable(jsonWebToken.getSubject()).map(UUID::fromString).orElse(null);
    }

    @GET
    @Path("/feed")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get recent articles from users you follow", description = "Get most recent articles from users you follow. Use query parameters to limit. Auth is required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipleArticlesResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public MultipleArticlesResponse getArticlesFeed(@DefaultValue("20") @QueryParam("limit") Integer limit, @DefaultValue("0") @QueryParam("offset") Integer offset, @Context SecurityContext securityContext) {
        PageResult<Article> pageResult = articlesService.getArticlesFeed(getLoggedUserId(), limit, offset);
        return new MultipleArticlesResponse().setArticles(pageResult.getResult()).setArticlesCount(pageResult.getTotal());
    }

    @PUT
    @Path("/{slug}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update an article", description = "Update an article. Auth is required")
    @RequestBody(required = true, description = "Article to update", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UpdateArticleRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleArticleResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public SingleArticleResponse updateArticle(@NotNull @Valid UpdateArticleRequest body, @PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new SingleArticleResponse().setArticle(articlesService.updateArticle(getLoggedUserId(), body, slug));
    }


    @POST
    @Path("/{slug}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a comment for an article", description = "Create a comment for an article. Auth is required")
    @RequestBody(required = true, description = "Comment you want to create", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewCommentRequest.class)))
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleCommentResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public SingleCommentResponse createComment(@NotNull @Valid NewCommentRequest body, @PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new SingleCommentResponse().setComment(commentsService.createComment(getLoggedUserId(), body, slug));
    }

    @POST
    @Path("/{slug}/favorite")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Favorite an article", description = "Favorite an article. Auth is required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleArticleResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public SingleArticleResponse createFavorite(@PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new SingleArticleResponse().setArticle(articlesService.favorite(getLoggedUserId(), slug));
    }

    @DELETE
    @Path("/{slug}/comments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a comment for an article", description = "Delete a comment for an article. Auth is required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public Response deleteComment(@PathParam("slug") String slug, @PathParam("id") Integer id, @Context SecurityContext securityContext) {
        commentsService.delete(getLoggedUserId(), slug, id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{slug}/favorite")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Unfavorite an article", description = "Unfavorite an article. Auth is required")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleArticleResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    @RolesAllowed("user")
    public SingleArticleResponse deleteFavorite(@PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new SingleArticleResponse().setArticle(articlesService.deleteFavorite(getLoggedUserId(), slug));
    }


    @GET
    @Path("/{slug}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get comments for an article", description = "Get the comments for an article. Auth is optional")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipleCommentsResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "422", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class)))})
    public MultipleCommentsResponse getComments(@PathParam("slug") String slug, @Context SecurityContext securityContext) {
        return new MultipleCommentsResponse().setComments(commentsService.getComments(getLoggedUserId(), slug));
    }


}