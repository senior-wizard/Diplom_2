import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.steps.StepsToCreateUser;
import org.example.steps.StepsToDeleteUser;
import org.example.steps.StepsToLoginUser;
import org.example.steps.StepsToUpdateUserInformation;
import org.junit.Before;
import org.junit.Test;

public class UpdateUserInformationTest {

    private final String email = "5sxs1df8@3g1.ru";
    private final String password = "1234";
    private final String name = "aboba";

    private final String changedEmail = "aboba3574@31.ru";
    private final String changedName = "jmyyh";

    @Before
    public void setUp() {
        BaseClass.baseUrl();
    }

    @Test
    @DisplayName("Обновление информации пользователя с авторизацией")
    public void updateUserInformation() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToUpdateUserInformation.updateUserInformation(changedEmail, changedName, StepsToLoginUser.loginUserInOneStep(email, password));
        StepsToUpdateUserInformation.checkStatusCodeWhenUpdateUserInformation(response);
        StepsToUpdateUserInformation.checkResponseEmailWhenUpdateUserInformation(response, changedEmail);
        StepsToUpdateUserInformation.checkResponseNameWhenUpdateUserInformation(response,changedName);
        StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(changedEmail, password));
    }

    @Test
    @DisplayName("Невозможно обновить информацию о пользователе без авторизации")
    public void cantUpdateUserInformationWithoutAuthorization() {
        StepsToCreateUser.createUserInOneStep(email, password, name);
        Response response = StepsToUpdateUserInformation.cantUpdateUserInformationWithoutAuthorization(changedEmail, changedName);
        StepsToUpdateUserInformation.checkStatusCodeWhenUpdateUserInformationWithoutAuthorization(response);
        StepsToUpdateUserInformation.checkResponseWhenUpdateUserInformationWithoutAuthorization(response);
        StepsToDeleteUser.deleteUser(StepsToLoginUser.loginUserInOneStep(email, password));
    }
}

