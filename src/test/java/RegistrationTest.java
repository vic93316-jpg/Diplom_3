import driver.DriverFactory;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.LoginPage;
import ru.yandex.praktikum.MainPage;
import ru.yandex.praktikum.RegisterPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTest {

    public WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Успешная регистрация")
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Проверка успешной регистрации нового пользователя (без вывода сообщений об ошибках)")
    public void successfulRegistrationTest() {
        String email = "test_" + System.currentTimeMillis() + "@yandex.ru";
        String password = "password123";
        String name = "TestUser";

        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.register(name, email, password);

        assertFalse("Ошибка при регистрации не должна отображаться",
                registerPage.isErrorVisible());
    }

    @Test
    @Story("Ошибка при коротком пароле")
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверка появления ошибки, если пароль меньше 6 символов")
    public void shortPasswordErrorTest() {
        String email = "test_" + System.currentTimeMillis() + "@yandex.ru";
        String shortPassword = "12345";
        String name = "TestUser";

        LoginPage loginPage = mainPage.clickLoginButton();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.register(name, email, shortPassword);

        assertTrue("Должна отображаться ошибка о некорректном пароле",
                registerPage.isErrorVisible());
        String error = registerPage.getErrorMessage();
        assertTrue("Текст ошибки должен указывать на проблему с паролем",
                error.contains("6") || error.toLowerCase().contains("пароль"));
    }
}