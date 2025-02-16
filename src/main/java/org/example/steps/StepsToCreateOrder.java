package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.bodies.BodyOfCreateOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepsToCreateOrder {

    static String createOrderURL = "/api/orders";

    @Step("Создание заказа с авторизацией")
    public static Response createOrderWithAuthorization(List<String> ingredients, String bearerToken) {
        BodyOfCreateOrder bodyOfCreateOrder = new BodyOfCreateOrder(ingredients);
        return given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(bearerToken.substring(7))
                .body(bodyOfCreateOrder)
                .when()
                .post(createOrderURL);
    }

    @Step("Создание заказа")
    public static Response createOrder(List<String> ingredients) {
        BodyOfCreateOrder bodyOfCreateOrder = new BodyOfCreateOrder(ingredients);
        return given()
                .contentType(ContentType.JSON)
                .body(bodyOfCreateOrder)
                .when()
                .post(createOrderURL);
    }

    @Step("Проверка, что 'order' не равен null в ответе при создании заказа")
    public static void checkResponseWhenCreateOrderWithIngredients(Response response) {
        response.then().assertThat().body("order", notNullValue());
    }

    @Step("Проверка, что 'order' не равен null в ответе при создании заказа с авторизацией")
    public static void checkResponseWhenCreateOrderWithIngredientsAndAuthorization(Response response) {
        response.then().assertThat().body("order.owner", notNullValue());
    }

    @Step("Проверка, что код ответа равен 200 при создании заказа")
    public static void checkStatusCodeWhenCreateOrderWithIngredients(Response response) {
        response.then().statusCode(200);
    }

    @Step("Проверка, что 'message' равен 'Ingredient ids must be provided' в ответе при попытке создать заказ без ингредиентов")
    public static void checkResponseWhenCreateOrderWithoutIngredients(Response response) {
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка, что код ответа равен 400 при попытке создать заказ без ингредиентов")
    public static void checkStatusCodeWhenCreateOrderWithoutIngredients(Response response) {
        response.then().statusCode(400);
    }

    @Step("Проверка, что код ответа равен 500 при попытке создать заказ с неверным хешем ингредиента")
    public static void checkStatusCodeWhenCreateOrderWithWrongHashOfIngredients(Response response) {
        response.then().statusCode(500);
    }
}
