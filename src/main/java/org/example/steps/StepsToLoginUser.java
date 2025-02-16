package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.bodies.BodyOfLoginUser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepsToLoginUser {

    static String loginUserURL = "/api/auth/login";

    @Step("Авторизация пользователя")
    public static Response loginUser(String email, String password) {
        BodyOfLoginUser bodyOfLoginUser = new BodyOfLoginUser(email, password);
        return given()
                .contentType(ContentType.JSON)
                .body(bodyOfLoginUser)
                .when()
                .post(loginUserURL);
    }

    @Step("Проверка, что 'success' равен true в ответе при авторизации пользователя")
    public static void checkResponseWhenLoginUser(Response response) {
        response.then().assertThat().body("success", equalTo(true));
    }

    @Step("Проверка, что код ответа равен 200 при авторизации пользователя")
    public static void checkStatusCodeWhenLoginUser(Response response) {
        response.then().statusCode(200);
    }

    @Step("Проверка, что 'success' равен false в ответе при попытке авторизации без логина или пароля")
    public static void checkResponseWhenLoginUserWithoutLoginOrPassword(Response response) {
        response.then().assertThat().body("success", equalTo(false));
    }

    @Step("Проверка, что код ответа равен 401 при попытке авторизации без логина или пароля")
    public static void checkStatusCodeWhenLoginUserWithoutLoginOrPassword(Response response) {
        response.then().statusCode(401);
    }

    @Step("Авторизация пользователя и все нужные проверки в 1 шаг")
    public static String loginUserInOneStep(String email, String password) {
        BodyOfLoginUser bodyOfLoginUser = new BodyOfLoginUser(email, password);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(bodyOfLoginUser)
                .when()
                .post(loginUserURL);
        response.then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        return response.jsonPath().getString("accessToken");
    }

}