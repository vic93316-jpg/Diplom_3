import api.ApiClient;
import driver.DriverFactory;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.*;

import static org.junit.Assert.*;

@Epic("Stellar Burgers")
@Feature("Вход в аккаунт")
public class LoginTest {

    public WebDriver driver;
    private MainPage mainPage;
    private String email;
    private String password;
    private String accessToken;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);

        email = "login_" + System.currentTimeMillis() + "@yandex.ru";
        password = "password123";

        User user = User.builder()
                .email(email)
                .password(password)
                .name("LoginUser")
                .build();

        Response response = ApiClient.createUser(user);

        assertEquals(200, response.statusCode());
        accessToken = response.jsonPath().getString("accessToken");
        assertNotNull("accessToken не получен", accessToken);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            ApiClient.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void assertLoggedIn(MainPage afterLogin) {
        ProfilePage profilePage = afterLogin.openProfile();
        assertTrue("После входа должен открыться личный кабинет",
                profilePage.isProfileHeaderVisible());
    }

    @Test
    @Story("Вход по кнопке «Войти в аккаунт» на главной")
    @DisplayName("Вход через кнопку «Войти в аккаунт» на главной странице")
    @Description("Проверяет вход пользователя при нажатии кнопки «Войти в аккаунт» на главной странице")
    public void loginViaMainPageButtonTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        MainPage afterLogin = loginPage.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку «Личный кабинет»")
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Проверяет вход пользователя при нажатии кнопки «Личный кабинет» в хедере")
    public void loginViaPersonalAccountButtonTest() {
        LoginPage loginPage = mainPage.clickPersonalAccountButton();
        MainPage afterLogin = loginPage.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку в форме регистрации")
    @DisplayName("Вход через кнопку «Войти» на странице регистрации")
    @Description("Проверяет вход пользователя через форму регистрации (кнопка «Войти»)")
    public void loginViaRegisterFormTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        LoginPage backToLogin = registerPage.clickLoginLink();
        MainPage afterLogin = backToLogin.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку в форме восстановления пароля")
    @DisplayName("Вход через кнопку «Войти» на странице восстановления пароля")
    @Description("Проверяет вход пользователя через форму восстановления пароля (кнопка «Войти»)")
    public void loginViaForgotPasswordFormTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordLink();

        LoginPage backToLogin = forgotPasswordPage.clickLoginLink();
        MainPage afterLogin = backToLogin.login(email, password);

        assertLoggedIn(afterLogin);
    }
}