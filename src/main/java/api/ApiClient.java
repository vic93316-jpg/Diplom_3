package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru/";

    @Step("Создание пользователя через API: {user.email}")
    public static Response createUser(User user) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user) // Автоматическая сериализация объекта в JSON
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