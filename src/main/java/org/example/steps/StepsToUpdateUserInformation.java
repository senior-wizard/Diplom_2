package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.bodies.BodyOfUpdateUserInformation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepsToUpdateUserInformation {

    static String updateUserInformationURL = "/api/auth/user";

    @Step("Изменение данных пользователя")
    public static Response updateUserInformation(String email, String name, String bearerToken) {
        BodyOfUpdateUserInformation bodyOfUpdateUserInformation = new BodyOfUpdateUserInformation(email, name);
        return given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(bearerToken.substring(7))
                .body(bodyOfUpdateUserInformation)
                .when()
                .patch(updateUserInformationURL);

    }

    @Step("Проверка, что email изменился на заданное значение в ответе при изменении данных пользователя")
    public static void checkResponseEmailWhenUpdateUserInformation(Response response, String email) {
        response.then().assertThat().body("user.email", equalTo(email));
    }

    @Step("Проверка, что name изменился на заданное значение в ответе при изменении данных пользователя")
    public static void checkResponseNameWhenUpdateUserInformation(Response response, String name) {
        response.then().assertThat().body("user.name", equalTo(name));
    }

    @Step("Проверка, что код ответа равен 200 при изменении данных пользователя")
    public static void checkStatusCodeWhenUpdateUserInformation(Response response) {
        response.then().statusCode(200);
    }

    @Step("Данные не изменяются без авторизации")
    public static Response cantUpdateUserInformationWithoutAuthorization(String email, String name, String password) {
        BodyOfUpdateUserInformation bodyOfUpdateUserInformation = new BodyOfUpdateUserInformation(email, name);
        return given()
                .contentType(ContentType.JSON)
                .body(bodyOfUpdateUserInformation)
                .when()
                .patch(updateUserInformationURL);
    }

    @Step("Проверка, что 'success' равен false в ответе при попытке изменить данные пользователя, будучи неавторизованным")
    public static void checkResponseWhenUpdateUserInformationWithoutAuthorization(Response response) {
        response.then().assertThat().body("success", equalTo(false));
    }

    @Step("Проверка, что код ответа равен 401 при попытке изменить данные пользователя, будучи неавторизованным")
    public static void checkStatusCodeWhenUpdateUserInformationWithoutAuthorization(Response response) {
        response.then().statusCode(401);
    }
}
