package io.github.rogerfilipes;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class RequestUtils {


    public static String createUser(Integer port, String user) {
        return createUser(null, port, user);
    }
    public static String createUser(String urlPrefix, Integer port, String user) {
        String apiPrefix = urlPrefix == null ? "":urlPrefix;
        JSONObject user1 = new JSONObject();
        user1.put("username", user);
        user1.put("password", "password");
        user1.put("email", user+"@example.com");

        JSONObject createUserRequest = new JSONObject();
        createUserRequest.put("user", user1);

        return given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .port(port)
                .post(apiPrefix+Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");
    }
    public static String createArticle(String urlPrefix, String title, String tag, String token){

        String apiPrefix = urlPrefix == null ? "":urlPrefix;
        JSONObject createArticleBody = new JSONObject();
        createArticleBody.put("title", title);
        createArticleBody.put("description","description");
        createArticleBody.put("body","body");
        createArticleBody.put("tagList", new JSONArray(Set.of(tag)));

        JSONObject request = new JSONObject();
        request.put("article", createArticleBody);

        return given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION,"Token "+ token)
                .body(request.toString())
                .log().ifValidationFails(LogDetail.ALL)
                .post(apiPrefix+Constants.Path.ARTICLES)
                .then()
                .statusCode(HttpStatus.SC_OK).extract().path("article.slug");
    }

    public static String createArticle(String title, String tag, String token){
        return createArticle(null, title, tag, token);
    }

    public static int createComment(String slug, String comment, String token){
        return createComment(null, slug, comment, token);
    }


    public static int createComment(String urlPrefix, String slug, String comment, String token){

        String apiPrefix = urlPrefix == null ? "":urlPrefix;
        JSONObject commentBody = new JSONObject();
        commentBody.put("body", comment);

        JSONObject request = new JSONObject();
        request.put("comment", commentBody);

        return given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .pathParam("slug", slug)
                .body(request.toString())
                .post(apiPrefix+Constants.Path.ARTICLES + "/{slug}/comments")
                .then().statusCode(HttpStatus.SC_OK)
                .extract().path("comment.id");
    }

}
