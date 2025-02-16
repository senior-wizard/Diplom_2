import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {

    private final String email = BaseClass.email();
    private final String password = BaseClass.password();
    private final String name = BaseClass.name();

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Проверка, что при создании нового пользователя код ответа равен 200 и значение 'success' равно 'true'")
    public void createNewUserTest() {
        Response response = StepsToCreateUser.createNewUser(email, password, name);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUser(response);
        StepsToCreateUser.checkResponseWhenCreateNewUser(response);
    }

    @Test
    @DisplayName("Невозможно создать повторно такого же пользователя")
    @Description("Проверка, что при попытке создать пользователя с существующим в ДБ email-ом код ответа равен 403 и значение 'success' равно 'false'")
    public void cantCreateDuplicateUserTest() {
        StepsToCreateUser.createNewUser(email, password, name);
        StepsToCreateUser.checkStatusCodeWhenCreateExistingUser(StepsToCreateUser.createNewUser(email, password, name));
        StepsToCreateUser.checkResponseWhenCreateExistingUser(StepsToCreateUser.createNewUser(email, password, name));
    }

    @Test
    @DisplayName("Невозможно создать пользователя без email")
    @Description("Проверка, что при попытке создать пользователя без email код ответа равен 403 и значение 'success' равно 'false'")
    public void cantCreateUserWithoutEmailTest() {
        Response response = StepsToCreateUser.createNewUser("", password, name);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без пароля")
    @Description("Проверка, что при попытке создать пользователя без пароля код ответа равен 403 и значение 'success' равно 'false'")
    public void cantCreateUserWithoutPasswordTest() {
        Response response = StepsToCreateUser.createNewUser(email, "", name);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без имени")
    @Description("Проверка, что при попытке создать пользователя без имени код ответа равен 403 и значение 'success' равно 'false'")
    public void cantCreateUserWithoutNameTest() {
        Response response = StepsToCreateUser.createNewUser(email, password, "");
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
    }

    @After
    public void tearDown() {
        if (StepsToLoginUser.loginUser(email, password).statusCode() != 401) {
            StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
        }
    }
}

