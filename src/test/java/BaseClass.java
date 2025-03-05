import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.RestAssured;

import java.util.Locale;

public class BaseClass {

    public static void baseUrl() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    public static String email() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify("?#?#?#?#?#@gmail.com");
    }

    public static String name() {
        Faker faker = new Faker(new Locale("en-US"));
        return faker.name().firstName();
    }

    public static String password() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify("?#?#?#?#?##?#?#?##?");
    }
}
