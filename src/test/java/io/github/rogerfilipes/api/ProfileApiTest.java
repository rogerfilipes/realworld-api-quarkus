package io.github.rogerfilipes.api;

import io.github.rogerfilipes.Constants;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class ProfileApiTest {


    @Test
    void getProfile(){
        JSONObject createBody = new JSONObject();
        createBody.put("username", "userprofile");
        createBody.put("password", "password");
        createBody.put("email", "userprofile@example.com");

        JSONObject createUserRequest = new JSONObject();
        createUserRequest.put("user", createBody);

        String token = given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        JSONObject updateBody = new JSONObject();
        updateBody.put("bio", "bio");
        updateBody.put("image", "imageurl");

        JSONObject updateRequest = new JSONObject();
        updateRequest.put("user", updateBody);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization","Token "+token)
                .body(updateRequest.toString())
                .put(Constants.Path.USER)
                .then()
                .statusCode(HttpStatus.SC_OK);

        given()
                .log().all()
                .pathParam("username", "userprofile")
                .get(Constants.Path.PROFILES+"/{username}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("profile.username",is("userprofile"))
                .body("profile.bio", is("bio"))
                .body("profile.image", is("imageurl"))
                .body("profile.following",is(false));
    }

    @Test
    void getProfileNotFound(){
        given()
                .log().all()
                .pathParam("username", "userdoesntexists")
                .get(Constants.Path.PROFILES+"/{username}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void followUser(){
        //User to be followed
        JSONObject createBody = new JSONObject();
        createBody.put("username", "userprofiletobefollowed");
        createBody.put("password", "password");
        createBody.put("email", "userprofiletobefollowed@example.com");

        JSONObject createUserRequest = new JSONObject();
        createUserRequest.put("user", createBody);

        String token = given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        JSONObject updateBody = new JSONObject();
        updateBody.put("bio", "bio");
        updateBody.put("image", "imageurl");

        JSONObject updateRequest = new JSONObject();
        updateRequest.put("user", updateBody);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization","Token "+token)
                .body(updateRequest.toString())
                .put(Constants.Path.USER)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // User to follow
        createBody = new JSONObject();
        createBody.put("username", "userprofilefollowing");
        createBody.put("password", "password");
        createBody.put("email", "userprofilefollowing@example.com");

        createUserRequest = new JSONObject();
        createUserRequest.put("user", createBody);

        token = given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        given().log().all()
                .header("Authorization", "Token "+token)
                .contentType(ContentType.ANY)
                .pathParam("username", "userprofiletobefollowed")
                .post(Constants.Path.PROFILES+"/{username}/follow")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("profile.username", is("userprofiletobefollowed"));

    }

    @Test
    void unFollowUser(){
        //User to be followed
        JSONObject createBody = new JSONObject();
        createBody.put("username", "userprofiletobeunfollowed");
        createBody.put("password", "password");
        createBody.put("email", "userprofiletobeunfollowed@example.com");

        JSONObject createUserRequest = new JSONObject();
        createUserRequest.put("user", createBody);

        String token = given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        JSONObject updateBody = new JSONObject();
        updateBody.put("bio", "bio");
        updateBody.put("image", "imageurl");

        JSONObject updateRequest = new JSONObject();
        updateRequest.put("user", updateBody);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization","Token "+token)
                .body(updateRequest.toString())
                .put(Constants.Path.USER)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // User to follow
        createBody = new JSONObject();
        createBody.put("username", "userprofilefollowingandunfollow");
        createBody.put("password", "password");
        createBody.put("email", "userprofilefollowingandunfollow@example.com");

        createUserRequest = new JSONObject();
        createUserRequest.put("user", createBody);

        token = given()
                .contentType(ContentType.JSON)
                .body(createUserRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        given()
                .header("Authorization", "Token "+token)
                .pathParam("username", "userprofiletobeunfollowed")
                .post(Constants.Path.PROFILES+"/{username}/follow")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("profile.username", is("userprofiletobeunfollowed"));

        given()
                .header("Authorization", "Token "+token)
                .pathParam("username", "userprofiletobeunfollowed")
                .delete(Constants.Path.PROFILES+"/{username}/follow")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("profile.username", is("userprofiletobeunfollowed"));

    }
}
