package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru/";

    @Step("Создание пользователя через API: {email}")
    public static Response createUser(String email, String password, String name) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"name\":\"" + name + "\"}")
                .when()
                .post("/api/auth/register");
    }

    @Step("Удаление пользователя через API")
    public static void deleteUser(String accessToken) {
        given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }
}
