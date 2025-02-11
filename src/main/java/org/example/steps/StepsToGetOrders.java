package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepsToGetOrders {

    @Step("Получение списка заказов конкретного пользователя")
    public static Response getOrderListWithAuthorization(String bearerToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(bearerToken.substring(7))
                .when()
                .get("/api/orders");
    }

    @Step("Проверка, что 'success' равен true в ответе при получении списка заказов конкретного пользователя")
    public static void checkResponseWhenGetOrderListWithAuthorization(Response response) {
        response.then().assertThat().body("success", equalTo(true));
    }

    @Step("Проверка, что код ответа равен 200 при получении списка заказов конкретного пользователя")
    public static void checkStatusCodeWhenGetOrderListWithAuthorization(Response response) {
        response.then().statusCode(200);
    }

    @Step("Получение списка заказов конкретного пользователя")
    public static Response cantGetOrderListWithoutAuthorization() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/orders");
    }

    @Step("Проверка, что 'message' равен 'You should be authorised' в коде ответа при попытке получить список заказов конкретного пользователя без авторизации")
    public static void checkResponseWhenGetOrderListWithoutAuthorization(Response response) {
        response.then().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка, что код ответа равен 401 при попытке получить список заказов конкретного пользователя без авторизации")
    public static void checkStatusCodeWhenGetOrderListWithoutAuthorization(Response response) {
        response.then().statusCode(401);
    }
}
