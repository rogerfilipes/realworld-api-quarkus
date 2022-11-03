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
class UsersApiTest {


    @Test
    void createUser() {
        JSONObject createBody = new JSONObject();
        createBody.put("username", "usercreate");
        createBody.put("password", "password");
        createBody.put("email", "usercreate@example.com");

        JSONObject request = new JSONObject();
        request.put("user", createBody);

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(request.toString())
                .post(Constants.Path.USERS)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("user.email", is("usercreate@example.com"))
                .body("user.username", is("usercreate"))
                .body("user.bio", nullValue())
                .body("user.image", nullValue())
                .body("user.token", notNullValue());
    }


    @Test
    void createUserInvalidRequest() {
        JSONObject request = new JSONObject();

        JSONObject createBody = new JSONObject();
        createBody.put("username", "usercreate");
        createBody.put("password", JSONObject.NULL);
        createBody.put("email", JSONObject.NULL);

        request.put("user", createBody);

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(request.toString())
                .post(Constants.Path.USERS)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    @Test
    void loginUser() {
        JSONObject createBody = new JSONObject();
        createBody.put("username", "userlogin");
        createBody.put("password", "password");
        createBody.put("email", "userlogin@example.com");

        JSONObject createRequest = new JSONObject();
        createRequest.put("user", createBody);

        given()
                .contentType(ContentType.JSON)
                .body(createRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK);

        JSONObject loginBody = new JSONObject();
        loginBody.put("password", "password");
        loginBody.put("email", "userlogin@example.com");

        JSONObject loginRequest = new JSONObject();
        loginRequest.put("user", loginBody);

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest.toString())
                .log().all()
                .post(Constants.Path.USERS + "/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("user.email", is("userlogin@example.com"))
                .body("user.username", is("userlogin"))
                .body("user.bio", is(nullValue()))
                .body("user.image", is(nullValue()))
                .body("user.token", notNullValue());
    }

    @Test
    void loginUserUnauthorized() {
        JSONObject createBody = new JSONObject();
        createBody.put("username", "userloginUnauthorized");
        createBody.put("password", "password");
        createBody.put("email", "userloginUnauthorized@example.com");

        JSONObject createRequest = new JSONObject();
        createRequest.put("user", createBody);

        given()
                .contentType(ContentType.JSON)
                .body(createRequest.toString())
                .post(Constants.Path.USERS)
                .then()
                .statusCode(HttpStatus.SC_OK);

        JSONObject loginBody = new JSONObject();
        loginBody.put("password", "wrongpassword");
        loginBody.put("email", "userloginUnauthorized@example.com");

        JSONObject loginRequest = new JSONObject();
        loginRequest.put("user", loginBody);

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest.toString())
                .log().all()
                .post(Constants.Path.USERS + "/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}