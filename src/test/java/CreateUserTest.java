import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {

    private final String email = "5sshаdf8@31.ru";
    private final String password = "1234";
    private final String name = "aboba";

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createNewUser() {
        Response response = StepsToCreateUser.createNewUser(email, password, name);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUser(response);
        StepsToCreateUser.checkResponseWhenCreateNewUser(response);
        StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
    }

    @Test
    @DisplayName("Невозможно создать повторно такого же пользователя")
    public void cantCreateDuplicateUser() {
        StepsToCreateUser.createNewUser(email, password, name);
        StepsToCreateUser.checkResponseWhenCreateExistingUser(StepsToCreateUser.createNewUser(email, password, name));
        StepsToCreateUser.checkStatusCodeWhenCreateExistingUser(StepsToCreateUser.createNewUser(email, password, name));
        StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
    }

    @Test
    @DisplayName("Невозможно создать пользователя без почты")
    public void cantCreateUserWithoutEmail() {
        Response response = StepsToCreateUser.createNewUser("", password, name);
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без пароля")
    public void cantCreateUserWithoutPassword() {
        Response response = StepsToCreateUser.createNewUser(email, "", name);
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
    }

    @Test
    @DisplayName("Невозможно создать пользователя без имени")
    public void cantCreateUserWithoutName() {
        Response response = StepsToCreateUser.createNewUser(email, password, "");
        StepsToCreateUser.checkResponseWhenCreateNewUserWithoutRequiredField(response);
        StepsToCreateUser.checkStatusCodeWhenCreateNewUserWithoutRequiredField(response);
    }
}

