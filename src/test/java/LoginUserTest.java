import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

    private final String email = BaseClass.email();
    private final String password = BaseClass.password();
    private final String name = BaseClass.name();

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("")
    public void loginUserTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser(email, password);
        StepsToLoginUser.checkStatusCodeWhenLoginUser(response);
        StepsToLoginUser.checkResponseWhenLoginUser(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться без логина")
    @Description("Проверка, что при попытке авторизоваться без логина код ответа равен 401 и значение 'success' равно 'false'")
    public void cantLoginUserWithoutEmailTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser("", password);
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться с неверным логином")
    @Description("Проверка, что при попытке авторизоваться с неверным логином код ответа равен 401 и значение 'success' равно 'false'")
    public void cantLoginUserWithWrongEmailTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser("1234qwerty@mail.ru", password);
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться без пароля")
    @Description("Проверка, что при попытке авторизоваться без пароля код ответа равен 401 и значение 'success' равно 'false'")
    public void cantLoginUserWithoutPasswordTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser(email, "");
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @Test
    @DisplayName("Невозможно авторизоваться с неверным паролем")
    @Description("Проверка, что при попытке авторизоваться с неверным паролем код ответа равен 401 и значение 'success' равно 'false'")
    public void cantLoginUserWithWrongPasswordTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToLoginUser.loginUser(email, password + "1");
        StepsToLoginUser.checkStatusCodeWhenLoginUserWithoutLoginOrPassword(response);
        StepsToLoginUser.checkResponseWhenLoginUserWithoutLoginOrPassword(response);
    }

    @After
    public void tearDown() {
        if (StepsToLoginUser.loginUser(email, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
        }
    }
}

