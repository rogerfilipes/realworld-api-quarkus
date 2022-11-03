package io.github.rogerfilipes.api;

import io.github.rogerfilipes.Constants;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class UserApiTest {


    @Test
    void getCurrentUser() {
        JSONObject createBody = new JSONObject();
        createBody.put("username", "currentuser");
        createBody.put("password", "password");
        createBody.put("email", "currentuser@example.com");

        JSONObject request = new JSONObject();
        request.put("user", createBody);

        String token = given()
                .contentType(ContentType.JSON)
                .body(request.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        given()
                .header(HttpHeaders.AUTHORIZATION,"Token "+token)
                .log().all()
                .get(Constants.Path.USER)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("user.email", is("currentuser@example.com"))
                .body("user.username", is("currentuser"))
                .body("user.bio", nullValue())
                .body("user.image", nullValue())
                .body("user.token", notNullValue());
    }

    @Test
    void getCurrentUserWithoutAuthorization() {
        given()
                .log().all()
                .get(Constants.Path.USER)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void updateUser() {
        JSONObject createBody = new JSONObject();
        createBody.put("username", "updateuser");
        createBody.put("password", "password");
        createBody.put("email", "updateuser@example.com");

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
        updateBody.put("email", "newuseremail@example.com");
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
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("user.email",is("newuseremail@example.com"))
                .body("user.bio", is("bio"))
                .body("user.image", is("imageurl"));
    }

    @Test
    void updateUserExistingEmail() {
        // Create user with specific email
        JSONObject createBodyExisting = new JSONObject();
        createBodyExisting.put("username", "existingusermail");
        createBodyExisting.put("password", "password");
        createBodyExisting.put("email", "existingusermail@example.com");

        JSONObject createUserExistingRequest = new JSONObject();
        createUserExistingRequest.put("user", createBodyExisting);

        given()
                .contentType(ContentType.JSON)
                .body(createUserExistingRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // Create second user to update
        JSONObject createBody = new JSONObject();
        createBody.put("username", "updateUserExistingEmail");
        createBody.put("password", "password");
        createBody.put("email", "updateUserExistingEmail@example.com");

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
        updateBody.put("email", "existingusermail@example.com");
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
                .log().all()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    void updateUserExistingUsername() {
        // Create user with specific email
        JSONObject createBodyExisting = new JSONObject();
        createBodyExisting.put("username", "existingusername");
        createBodyExisting.put("password", "password");
        createBodyExisting.put("email", "existingusername@example.com");

        JSONObject createUserExistingRequest = new JSONObject();
        createUserExistingRequest.put("user", createBodyExisting);

        given()
                .contentType(ContentType.JSON)
                .body(createUserExistingRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // Create second user to update
        JSONObject createBody = new JSONObject();
        createBody.put("username", "updateUserExistingUsername");
        createBody.put("password", "password");
        createBody.put("email", "updateUserExistingUsername@example.com");

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
        updateBody.put("username", "existingusername");
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
                .log().all()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    void updateUserAtLeastOneField() {
        JSONObject body = new JSONObject();
        body.put("username", "atleastonefield");
        body.put("password", "password");
        body.put("email", "atleastonefield@example.com");

        JSONObject request = new JSONObject();
        request.put("user", body);

        String token = given()
                .contentType(ContentType.JSON)
                .body(request.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("user.token");

        //Empty body
        JSONObject updateBody = new JSONObject();

        JSONObject updateRequest = new JSONObject();
        updateRequest.put("user", updateBody);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization","Token "+token)
                .body(updateRequest.toString())
                .put(Constants.Path.USER)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


}