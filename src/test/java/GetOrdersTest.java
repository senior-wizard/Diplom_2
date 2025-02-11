import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToGetOrders;
import org.example.steps.StepsToLoginUser;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersTest {

    private final String email = "5ssdf87689@31.ru";
    private final String password = "1234";
    private final String name = "aboba";

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Получение списка заказов конкретного пользователя с авторизацией")
    public void getOrderList() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        String token = StepsToLoginUser.loginUserInOneStep(email, password);
        Response response = StepsToGetOrders.getOrderListWithAuthorization(token);
        StepsToGetOrders.checkStatusCodeWhenGetOrderListWithAuthorization(response);
        StepsToGetOrders.checkResponseWhenGetOrderListWithAuthorization(response);
        StepsToDeleteUser.deleteUser(token);
    }

    @Test
    @DisplayName("Невозможно получить список заказов конкретного пользователя без авторизации")
    public void cantGetOrderListWithoutAuthorization() {
        Response response = StepsToGetOrders.cantGetOrderListWithoutAuthorization();
        StepsToGetOrders.checkStatusCodeWhenGetOrderListWithoutAuthorization(response);
        StepsToGetOrders.checkResponseWhenGetOrderListWithoutAuthorization(response);
    }
}
