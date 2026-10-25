import driver.DriverFactory;
import io.qameta.allure.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.ConstructorPage;
import java.util.Arrays;
import java.util.Collection;


import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Конструктор")
@RunWith(Parameterized.class)
public class ConstructorTest  {

    public WebDriver driver;
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{{"chrome"}, {"yandex"}});
    }

    private final String browser;
    private ConstructorPage constructorPage;

    public ConstructorTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        System.setProperty("driver", browser);
        driver = DriverFactory.createDriver();
        constructorPage = new ConstructorPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
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