import driver.DriverFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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
        // DriverFactory сама определяет, какой браузер запускать
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
    public void bunsTabTest() {
        assertTrue("Вкладка «Булки» должна быть активна",
                constructorPage.isBunsTabActive());
    }

    @Test
    @Story("Переход к разделу «Соусы»")
    public void saucesTabTest() {
        assertTrue("Вкладка «Соусы» должна быть активна",
                constructorPage.isSaucesTabActive());
    }

    @Test
    @Story("Переход к разделу «Начинки»")
    public void fillingsTabTest() {
        assertTrue("Вкладка «Начинки» должна быть активна",
                constructorPage.isFillingsTabActive());
    }
}