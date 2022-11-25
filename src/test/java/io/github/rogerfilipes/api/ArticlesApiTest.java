package io.github.rogerfilipes.api;

import io.github.rogerfilipes.Constants;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.ConfigProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.github.rogerfilipes.RequestUtils.createUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ArticlesApiTest {

    private static String TOKEN_USER_1 = null;
    private static String TOKEN_USER_2 = null;
    private static final String dateTimePattern = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$";

    @BeforeAll
    public static void setup(){
        Integer port = ConfigProvider.getConfig().getValue("quarkus.http.test-port", Integer.class);

        String user = "user_1";
        String user2 = "user_2";

        TOKEN_USER_1 = createUser("/api", port, user);
        TOKEN_USER_2 = createUser("/api", port, user2);


    }



    @Test
    void getArticles(){
        given()
                .log().all()
                .get(Constants.Path.ARTICLES)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body("articles", notNullValue())
                .body("articlesCount", notNullValue());
    }


    @Test
    void createArticle(){

        JSONObject createArticleBody = new JSONObject();
        createArticleBody.put("title", "Article Title");
        createArticleBody.put("description","description");
        createArticleBody.put("body","body");
        createArticleBody.put("tagList", new JSONArray(Set.of("tag1")));

        JSONObject request = new JSONObject();
        request.put("article", createArticleBody);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION,"Token "+ TOKEN_USER_1)
                .body(request.toString())
                .post(Constants.Path.ARTICLES)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("article-title"))
                .body("article.title", is("Article Title"))
                .body("article.description", is("description"))
                .body("article.body", is("body"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.updatedAt", notNullValue())
                .body("article.updatedAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(false))
                .body("article.favoritesCount", is(0))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));


    }

    @Test
    void deleteArticle(){
        JSONObject createArticleBody = new JSONObject();
        createArticleBody.put("title", "title delete");
        createArticleBody.put("description","description");
        createArticleBody.put("body","body");
        createArticleBody.put("tagList", new JSONArray(Set.of("tag1")));

        JSONObject request = new JSONObject();
        request.put("article", createArticleBody);

        String slug = given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION,"Token "+ TOKEN_USER_1)
                .body(request.toString())
                .post(Constants.Path.ARTICLES)
                .then()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(HttpStatus.SC_OK)
                .extract().path("article.slug");

        given()
                .header(HttpHeaders.AUTHORIZATION, "Token "+ TOKEN_USER_1)
                .pathParam("slug", slug)
                .delete(Constants.Path.ARTICLES+"/{slug}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void listArticles(){
       createArticle("title 1", "tagged1", TOKEN_USER_1);
       createArticle("title 2","tagged1", TOKEN_USER_1);
       createArticle("title 3", "tagged2", TOKEN_USER_1);
       createArticle("title 4", "tagged3", TOKEN_USER_1);

       given()
               .log().all()
               .get(Constants.Path.ARTICLES)
               .then()
               .log().all()
               .statusCode(HttpStatus.SC_OK)
               .body("articles", hasSize(greaterThanOrEqualTo(4)));
    }

    @Test
    void listArticlesByTag(){
        createArticle("title 5", "tagged5", TOKEN_USER_1);
        createArticle("title 6","tagged5", TOKEN_USER_1);
        createArticle("title 7", "tagged5", TOKEN_USER_1);
        createArticle("title 8", "tagged6", TOKEN_USER_1);

        given()
                .log().all()
                .queryParam("tag","tagged5")
                .get(Constants.Path.ARTICLES)

                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("articles", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    void listArticlesByAuthor(){
        createArticle("title author 5", "tagged5", TOKEN_USER_1);
        createArticle("title author 6","tagged5", TOKEN_USER_1);
        createArticle("title author 7", "tagged5", TOKEN_USER_2);
        createArticle("title author 8", "tagged6", TOKEN_USER_2);

        given()
                .log().all()
                .queryParam("author","user_2")
                .get(Constants.Path.ARTICLES)

                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("articles", hasSize(greaterThan(2)));
    }

    @Test
    void listArticlesByAuthorAndTag(){
        createArticle("title author la1", "tagged5", TOKEN_USER_1);
        createArticle("title author la2","tagged_autag", TOKEN_USER_1);
        createArticle("title author la3", "tagged5", TOKEN_USER_2);
        createArticle("title author la4", "tagged_autag", TOKEN_USER_2);

        given()
                .log().all()
                .queryParam("author","user_2")
                .queryParam("tag","tagged_autag")
                .get(Constants.Path.ARTICLES)

                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("articles", hasSize(equalTo(1)));
    }

    @Test
    void getArticleBySlug(){
        createArticle("title slug 5", "tagged5", TOKEN_USER_1);
        given()
                .log().all()
                .pathParam("slug", "title-slug-5")
                .get(Constants.Path.ARTICLES+"/{slug}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("title-slug-5"))
                .body("article.title", is("title slug 5"))
                .body("article.description", is("description"))
                .body("article.body", is("body"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(false))
                .body("article.favoritesCount", is(0))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));

    }

    @Test
    void updateArticle(){
        String slug = createArticle("title to change", "tagged5", TOKEN_USER_1);

        JSONObject updateBody = new JSONObject();
        updateBody.put("title", "title updated");
        updateBody.put("body", "body updated");
        updateBody.put("description", "description updated");

        JSONObject updateRequest = new JSONObject();
        updateRequest.put("article", updateBody);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_USER_1)
                .pathParam("slug", slug)
                .body(updateRequest.toString())
                .put(Constants.Path.ARTICLES+"/{slug}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("title-updated"))
                .body("article.title", is("title updated"))
                .body("article.description", is("description updated"))
                .body("article.body", is("body updated"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.updatedAt", notNullValue())
                .body("article.updatedAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(false))
                .body("article.favoritesCount", is(0))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));

        slug = "title-updated";
        given()
                .log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_USER_1)
                .pathParam("slug", slug)
                .get(Constants.Path.ARTICLES+"/{slug}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("title-updated"))
                .body("article.title", is("title updated"))
                .body("article.description", is("description updated"))
                .body("article.body", is("body updated"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.updatedAt", notNullValue())
                .body("article.updatedAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(false))
                .body("article.favoritesCount", is(0))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));

    }

    @Test
    void getFeed(){
        createArticle("title feed 1", "tagged5", TOKEN_USER_1);
        createArticle("title feed 2", "tagged5", TOKEN_USER_1);
        createArticle("title feed 3", "tagged5", TOKEN_USER_1);
        createArticle("title feed 4", "tagged5", TOKEN_USER_1);
        createArticle("title feed 5", "tagged5", TOKEN_USER_1);
        createArticle("title feed 6", "tagged5", TOKEN_USER_1);

        given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_USER_2)
                .queryParam("offset", 0)
                .queryParam("limit", 5)
                .get(Constants.Path.ARTICLES+"/feed")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("articles", hasSize(equalTo(5)));
    }

    @Test
    void favorite(){
        String slug = createArticle("title favorite", "tagged5", TOKEN_USER_1);

        given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_USER_2)
                .pathParam("slug",slug)
                .post(Constants.Path.ARTICLES+"/{slug}/favorite")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("title-favorite"))
                .body("article.title", is("title favorite"))
                .body("article.description", is("description"))
                .body("article.body", is("body"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(true))
                .body("article.favoritesCount", is(1))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));
    }

    @Test
    void deleteFavorite(){
        String  slug = createArticle("title unfavorite", "tagged5", TOKEN_USER_1);

        given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token "+TOKEN_USER_2)
                .pathParam("slug", slug)
                .delete(Constants.Path.ARTICLES+"/{slug}/favorite")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("article.slug", is("title-unfavorite"))
                .body("article.title", is("title unfavorite"))
                .body("article.description", is("description"))
                .body("article.body", is("body"))
                .body("article.tagList", hasSize(greaterThan(0)))
                .body("article.createdAt", notNullValue())
                .body("article.createdAt", matchesPattern(dateTimePattern))
                .body("article.favorited", is(false))
                .body("article.favoritesCount", is(0))
                .body("article.author.username", is("user_1"))
                .body("article.author.bio", nullValue())
                .body("article.author.image", nullValue())
                .body("article.author.following", is(false));
    }

    private String createArticle(String title, String tag, String token){
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
                .post(Constants.Path.ARTICLES)
                .then()
                .statusCode(HttpStatus.SC_OK).extract().path("article.slug");


    }
}
