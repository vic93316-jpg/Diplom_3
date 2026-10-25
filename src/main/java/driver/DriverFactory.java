package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverFactory {

    public static final String BASE_URL = "https://qa-stellarburgers.education-services.ru/";

    private DriverFactory() {
        // Приватный конструктор, так как класс утилитарный
    }

    public static WebDriver createDriver() {
        WebDriver driver;
        if ("yandex".equalsIgnoreCase(System.getProperty("driver"))) {
            driver = startYandex();
        } else {
            driver = startChrome();
        }
        setupDriver(driver);
        driver.get(BASE_URL);
        return driver;
    }

    private static WebDriver startChrome() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static WebDriver startYandex() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\vic93\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        return new ChromeDriver(options);
    }

    private static void setupDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }
}