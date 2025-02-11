import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateOrder;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CreateOrderTest {

    private final String email = "5ss7adf8@341.ru";
    private final String password = "1234";
    private final String name = "aboba";
    private final List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
    private final List<String> badHashIngredients = List.of("61c0c5a71d1f82001bdaaa6d1", "61c0c5a71d1f82001bdaaa6f1");


    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithoutAuthorization() {
        Response response = StepsToCreateOrder.createOrder(ingredients);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithIngredients(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа с авторизацией и без ингредиентов")
    public void cantCreateOrderWithoutAuthorizationAndWithoutIngredients() {
        Response response = StepsToCreateOrder.createOrder(List.of());
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithoutIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа с авторизацией и неверным хешем ингредиентов")
    public void cantCreateOrderWithoutAuthorizationAndWithBadHashOfIngredients() {
        Response response = StepsToCreateOrder.createOrder(badHashIngredients);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithWrongHashOfIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithAuthorization() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(ingredients, token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithIngredientsAndAuthorization(response);
        StepsToDeleteUser.deleteUser(token);
    }

    @Test
    @DisplayName("Невозможность создания заказа без авторизации и без ингредиентов")
    public void cantCreateOrderWithAuthorizationAndWithoutIngredients() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(List.of(), token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithoutIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithoutIngredients(response);
        StepsToDeleteUser.deleteUser(token);
    }

    @Test
    @DisplayName("Невозможность создания заказа без авторизации и с неверным хешем ингредиентов")
    public void cantCreateOrderWithAuthorizationAndWithBadHashOfIngredients() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(badHashIngredients, token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithWrongHashOfIngredients(response);
        StepsToDeleteUser.deleteUser(token);
    }
}

