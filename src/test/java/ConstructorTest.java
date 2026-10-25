import driver.DriverFactory;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.ConstructorPage;

import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Конструктор")
public class ConstructorTest {

    public WebDriver driver;
    private ConstructorPage constructorPage;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        constructorPage = new ConstructorPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Переход к разделу «Булки»")
    @DisplayName("Переключение на вкладку «Булки»")
    @Description("Проверяет, что при клике на вкладку «Булки» она становится активной")
    public void bunsTabTest() {
        assertTrue("Вкладка «Булки» должна быть активна",
                constructorPage.isBunsTabActive());
    }

    @Test
    @Story("Переход к разделу «Соусы»")
    @DisplayName("Переключение на вкладку «Соусы»")
    @Description("Проверяет, что при клике на вкладку «Соусы» она становится активной")
    public void saucesTabTest() {
        assertTrue("Вкладка «Соусы» должна быть активна",
                constructorPage.isSaucesTabActive());
    }

    @Test
    @Story("Переход к разделу «Начинки»")
    @DisplayName("Переключение на вкладку «Начинки»")
    @Description("Проверяет, что при клике на вкладку «Начинки» она становится активной")
    public void fillingsTabTest() {
        assertTrue("Вкладка «Начинки» должна быть активна",
                constructorPage.isFillingsTabActive());
    }
}