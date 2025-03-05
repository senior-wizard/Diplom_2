import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToGetOrders;
import org.example.steps.StepsToLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersTest {

    private final String email = BaseClass.email();
    private final String password = BaseClass.password();
    private final String name = BaseClass.name();

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Получение списка заказов конкретного пользователя с авторизацией")
    @Description("Проверка, что при получении списка заказов конкретного пользователя с авторизацией код ответа равен 200 и значение 'success' равно 'true'")
    public void getOrderListTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToGetOrders.getOrderListWithAuthorization(StepsToLoginUser.loginUserInOneStep(email, password));
        StepsToGetOrders.checkStatusCodeWhenGetOrderListWithAuthorization(response);
        StepsToGetOrders.checkResponseWhenGetOrderListWithAuthorization(response);
    }

    @Test
    @DisplayName("Невозможно получить список заказов конкретного пользователя без авторизации")
    @Description("Проверка, что при попытке получения списка заказов конкретного пользователя без авторизации код ответа равен 401 и значение 'message' равно 'You should be authorised'")
    public void cantGetOrderListWithoutAuthorizationTest() {
        Response response = StepsToGetOrders.cantGetOrderListWithoutAuthorization();
        StepsToGetOrders.checkStatusCodeWhenGetOrderListWithoutAuthorization(response);
        StepsToGetOrders.checkResponseWhenGetOrderListWithoutAuthorization(response);
    }

    @After
    public void tearDown() {
        if (StepsToLoginUser.loginUser(email, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
        }
    }
}
