package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.bodies.BodyOfCreateUser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StepsToCreateUser {

    static String createUserURL = "/api/auth/register";

    @Step("Создание пользователя")
    public static Response createNewUser(String email, String password, String name) {
        BodyOfCreateUser bodyOfCreateUser = new BodyOfCreateUser(email, password, name);
        return given()
                .contentType(ContentType.JSON)
                .body(bodyOfCreateUser)
                .when()
                .post(createUserURL);
    }

    @Step("Проверка, что 'success' равен true в ответе при создании нового пользователя")
    public static void checkResponseWhenCreateNewUser(Response response) {
        response.then().assertThat().body("success", equalTo(true));
    }

    @Step("Проверка, что код ответа равен 200 при создании нового пользователя")
    public static void checkStatusCodeWhenCreateNewUser(Response response) {
        response.then().statusCode(200);
    }

    @Step("Проверка, что 'success' равен false в ответе при попытке создать одного и того же пользователя дважды")
    public static void checkResponseWhenCreateExistingUser(Response response) {
        response.then().assertThat().body("success", equalTo(false));
    }

    @Step("Проверка, что код ответа равен 403 при попытке создать одного и того же пользователя дважды")
    public static void checkStatusCodeWhenCreateExistingUser(Response response) {
        response.then().statusCode(403);
    }

    @Step("Проверка, что 'success' равен false в ответе при попытке создать нового пользователя, не заполняя одно из обязательных полей")
    public static void checkResponseWhenCreateNewUserWithoutRequiredField(Response response) {
        response.then().assertThat().body("success", equalTo(false));
    }

    @Step("Проверка, что код ответа равен 401 при попытке создать нового пользователя, не заполняя одно из обязательных полей")
    public static void checkStatusCodeWhenCreateNewUserWithoutRequiredField(Response response) {
        response.then().statusCode(403);
    }

    @Step("Создание пользователя и все нужные проверки в 1 шаг")
    public static void createUserInOneStep(String email, String password, String name) {
        BodyOfCreateUser bodyOfCreateUser = new BodyOfCreateUser(email, password, name);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(bodyOfCreateUser)
                .when()
                .post(createUserURL);
        response.then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }
}

