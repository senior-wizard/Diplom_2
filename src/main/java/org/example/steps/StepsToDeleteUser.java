package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepsToDeleteUser {

    @Step("Удаление пользователя и все нужные проверки в 1 шаг")
    public static void deleteUser(String bearerToken) {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(bearerToken.substring(7))
                .when()
                .delete("/api/auth/user");
        response.then().statusCode(202)
                .and()
                .assertThat().body("message", equalTo("User successfully removed"));

    }
}
