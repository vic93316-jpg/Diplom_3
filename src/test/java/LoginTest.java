import api.ApiClient;
import driver.DriverFactory;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@Epic("Stellar Burgers")
@Feature("Вход в аккаунт")
@RunWith(Parameterized.class)
public class LoginTest  {
    public WebDriver driver;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{{"chrome"}, {"yandex"}});
    }

    private final String browser;
    private MainPage mainPage;
    private String email;
    private String password;
    private String accessToken;

    public LoginTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        System.setProperty("driver", browser);
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);

        email = "login_" + System.currentTimeMillis() + "@yandex.ru";
        password = "password123";

        Response response = ApiClient.createUser(email, password, "LoginUser");
        assertEquals(200, response.statusCode());

        accessToken = response.jsonPath().getString("accessToken");
        assertNotNull("accessToken не получен при создании пользователя", accessToken);
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
    public void loginViaMainPageButtonTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        MainPage afterLogin = loginPage.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку «Личный кабинет»")
    public void loginViaPersonalAccountButtonTest() {
        LoginPage loginPage = mainPage.clickPersonalAccountButton();
        MainPage afterLogin = loginPage.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку в форме регистрации")
    public void loginViaRegisterFormTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        LoginPage backToLogin = registerPage.clickLoginLink();
        MainPage afterLogin = backToLogin.login(email, password);

        assertLoggedIn(afterLogin);
    }

    @Test
    @Story("Вход через кнопку в форме восстановления пароля")
    public void loginViaForgotPasswordFormTest() {
        LoginPage loginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordLink();

        LoginPage backToLogin = forgotPasswordPage.clickLoginLink();
        MainPage afterLogin = backToLogin.login(email, password);

        assertLoggedIn(afterLogin);
    }
}