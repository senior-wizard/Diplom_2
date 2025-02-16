import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateOrder;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CreateOrderTest {

    private final String email = BaseClass.email();
    private final String password = BaseClass.password();
    private final String name = BaseClass.name();
    private final List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
    private final List<String> badHashIngredients = List.of("61c0c5a71d1f82001bdaaa6d1", "61c0c5a71d1f82001bdaaa6f1");


    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка, что при создании заказа без авторизации код ответа равен 200 и значение 'order' в ответе не равно 'null'")
    public void createOrderWithoutAuthorizationTest() {
        Response response = StepsToCreateOrder.createOrder(ingredients);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithIngredients(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа без авторизации и без ингредиентов")
    @Description("Проверка, что при создании заказа без авторизации и без ингредиентов код ответа равен 400 и значение 'message' в ответе равно 'Ingredient ids must be provided'")
    public void cantCreateOrderWithoutAuthorizationAndWithoutIngredientsTest() {
        Response response = StepsToCreateOrder.createOrder(List.of());
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithoutIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа без авторизации и неверным хешем ингредиентов")
    @Description("Проверка, что при создании заказа без авторизации и с неверным хэшем ингредиентов код ответа равен 500")
    public void cantCreateOrderWithoutAuthorizationAndWithBadHashOfIngredientsTest() {
        Response response = StepsToCreateOrder.createOrder(badHashIngredients);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithWrongHashOfIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка, что при создании заказа с авторизацией код ответа равен 200 и значение 'order.owner' не равно 'null'")
    public void createOrderWithAuthorizationTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(ingredients, token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithIngredientsAndAuthorization(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа с авторизацией и без ингредиентов")
    @Description("Проверка, что при создании заказа с авторизацией и без ингредиентов код ответа равен 400 и значение 'message' равно 'Ingredient ids must be provided'")
    public void cantCreateOrderWithAuthorizationAndWithoutIngredientsTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(List.of(), token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithoutIngredients(response);
        StepsToCreateOrder.checkResponseWhenCreateOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа с авторизацией и с неверным хешем ингредиентов")
    @Description("Проверка, что при создании заказа с авторизацией и с неверным хешем ингредиентов код ответа равен 500")
    public void cantCreateOrderWithAuthorizationAndWithBadHashOfIngredientsTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToCreateOrder.createOrderWithAuthorization(badHashIngredients, token);
        StepsToCreateOrder.checkStatusCodeWhenCreateOrderWithWrongHashOfIngredients(response);
    }

    @After
    public void tearDown() {
        if (StepsToLoginUser.loginUser(email, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
        }
    }
}

