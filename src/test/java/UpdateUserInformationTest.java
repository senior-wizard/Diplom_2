import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.example.steps.StepsToUpdateUserInformation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UpdateUserInformationTest {

    private final String email = BaseClass.email();
    private final String password = BaseClass.password();
    private final String name = BaseClass.name();

    private final String changedEmail = BaseClass.email();
    private final String changedName = BaseClass.name();
    private final String changedPassword = BaseClass.password();

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Обновление информации пользователя с авторизацией")
    @Description("Проверка, что при обновлении email и логина пользователя код ответа равен 200 и что email и логин изменили значения в ответе")
    public void updateUserInformationTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToUpdateUserInformation.updateUserInformation(changedEmail, changedName, password, StepsToLoginUser.loginUserInOneStep(email, password));
        StepsToUpdateUserInformation.checkStatusCodeWhenUpdateUserInformation(response);
        StepsToUpdateUserInformation.checkResponseEmailWhenUpdateUserInformation(response, changedEmail);
        StepsToUpdateUserInformation.checkResponseNameWhenUpdateUserInformation(response,changedName);
    }

    @Test
    @DisplayName("Обновление пароля пользователя с авторизацией")
    @Description("Проверка, что при обновлении пароля пользователя код ответа равен 200 и что пользователя можно удалить с новым паролем")
    public void updateUserPasswordTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToUpdateUserInformation.updateUserInformation(email, name, changedPassword, StepsToLoginUser.loginUserInOneStep(email, password));
        StepsToUpdateUserInformation.checkStatusCodeWhenUpdateUserInformation(response);
    }

    @Test
    @DisplayName("Невозможно обновить информацию о пользователе без авторизации")
    @Description("Проверка, что при попытке обновить информацию о пользователе без авторизации код ответа равен 401 и и значение 'success' равно 'false'")
    public void cantUpdateUserInformationWithoutAuthorizationTest() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToUpdateUserInformation.cantUpdateUserInformationWithoutAuthorization(changedEmail, changedName, changedPassword);
        StepsToUpdateUserInformation.checkStatusCodeWhenUpdateUserInformationWithoutAuthorization(response);
        StepsToUpdateUserInformation.checkResponseWhenUpdateUserInformationWithoutAuthorization(response);
    }

    @After
    public void tearDown() {
        if (StepsToLoginUser.loginUser(email, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
        }
        if (StepsToLoginUser.loginUser(changedEmail, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(changedEmail, password));
        }
        if (StepsToLoginUser.loginUser(email, changedPassword).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, changedPassword));
        }
        if (StepsToLoginUser.loginUser(changedEmail, changedPassword).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(changedEmail, changedPassword));
        }
    }
}

