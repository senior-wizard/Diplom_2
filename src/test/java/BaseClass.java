import io.restassured.RestAssured;

public class BaseClass {

    public static void baseUrl() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}
