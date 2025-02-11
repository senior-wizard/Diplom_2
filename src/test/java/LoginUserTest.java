import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

    private final String email = "5ssdf8@31.ru";
    private final String password = "1234";
    private final String name = "aboba";

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void loginUser() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser(email, password);
        StepsToLoginUser.checkStatusCodeWhenLoginUser(response);
        StepsToLoginUser.checkResponseWhenLoginUser(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться без логина")
    public void cantLoginUserWithoutEmail() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser("", password);
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться без пароля")
    public void cantLoginUserWithoutPassword() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser(email, "");
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @After
    public void tearDown() {
        StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
    }
}

