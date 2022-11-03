package io.github.rogerfilipes.api;

import io.github.rogerfilipes.Constants;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.ConfigProvider;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.rogerfilipes.RequestUtils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
public class CommentsApiTest {
    private static String TOKEN_ARTICLE_CREATOR = null;
    private static String TOKEN_COMMENTER_1 = null;
    private static String TOKEN_COMMENTER_2 = null;


    @BeforeAll
    public static void setup() {
        Integer port = ConfigProvider.getConfig().getValue("quarkus.http.test-port", Integer.class);

        String user = "user_article";
        String user2 = "user_comment_1";
        String user3 = "user_comment_2";

        TOKEN_ARTICLE_CREATOR = createUser("/api",port, user);
        TOKEN_COMMENTER_1 = createUser("/api",port, user2);
        TOKEN_COMMENTER_2 = createUser("/api", port, user3);
    }

    @Test
    void createSingleComment() {
        createArticle("article to comment", "tag5", TOKEN_ARTICLE_CREATOR);
        JSONObject commentBody = new JSONObject();
        commentBody.put("body", "this is a comment");

        JSONObject request = new JSONObject();
        request.put("comment", commentBody);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + TOKEN_COMMENTER_1)
                .pathParam("slug", "article-to-comment")
                .body(request.toString())
                .post(Constants.Path.ARTICLES + "/{slug}/comments")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("comment.id", notNullValue())
                .body("comment.createdAt", notNullValue())
                .body("comment.createdAt", matchesPattern(Constants.Pattern.DATE_TIME))
                .body("comment.updatedAt", notNullValue())
                .body("comment.updatedAt", matchesPattern(Constants.Pattern.DATE_TIME))
                .body("comment.body", is("this is a comment"))
                .body("comment.author.username", is("user_article"))
                .body("comment.author.bio", nullValue())
                .body("comment.author.image", nullValue())
                .body("comment.author.following", is(false));
    }

    @Test
    void getArticleComments() {
        createArticle("article get comments", "tag1", TOKEN_ARTICLE_CREATOR);
        createComment("article-get-comments", "this is a comment 1", TOKEN_COMMENTER_1);
        createComment("article-get-comments", "this is a comment 2", TOKEN_COMMENTER_1);
        createComment("article-get-comments", "this is a comment 3", TOKEN_COMMENTER_2);

        given().log().all()
                .pathParam("slug", "article-get-comments")
                .get(Constants.Path.ARTICLES + "/{slug}/comments")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("comments", hasSize(equalTo(3)));
    }

    @Test
    void deleteComment() {
        String slug = createArticle("article delete comment", "tag1", TOKEN_ARTICLE_CREATOR);
        int id = createComment("article-delete-comment", "this is a comment 1", TOKEN_COMMENTER_1);

        given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_COMMENTER_1)
                .pathParam("slug", slug)
                .pathParam("id", id)
                .delete(Constants.Path.ARTICLES + "/{slug}/comments/{id}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }
}
