import driver.DriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.LoginPage;
import ru.yandex.praktikum.MainPage;
import ru.yandex.praktikum.RegisterPage;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
@RunWith(Parameterized.class)
public class RegistrationTest  {
    public WebDriver driver;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{{"chrome"}, {"yandex"}});
    }

    private final String browser;
    private MainPage mainPage;

    public RegistrationTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        System.setProperty("driver", browser);
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Story("Успешная регистрация")
    @Description("Проверка успешной регистрации нового пользователя")
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
    @Description("Проверка ошибки, если пароль меньше 6 символов")
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