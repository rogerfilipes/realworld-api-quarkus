package io.github.rogerfilipes.api;

import io.github.rogerfilipes.Constants;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.rogerfilipes.RequestUtils.createArticle;
import static io.github.rogerfilipes.RequestUtils.createUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class TagsApiTest {

    private static String TOKEN_ARTICLE_CREATOR = null;


    @BeforeAll
    public static void setup() {
        Integer port = ConfigProvider.getConfig().getValue("quarkus.http.test-port", Integer.class);

        String user = "user_article_tags";

        TOKEN_ARTICLE_CREATOR = createUser("/api", port, user);
    }

    @Test
    void getTags(){
        createArticle("title get tags", "gettags", TOKEN_ARTICLE_CREATOR);

        given()
                .log().all()
                .get(Constants.Path.TAGS)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("tags", hasSize(greaterThanOrEqualTo(1)));
    }

}
