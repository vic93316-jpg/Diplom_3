package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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


    public static class WebActions {
        private final WebDriver driver;
        private final WebDriverWait wait;

        public WebActions(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        @Step("Клик по элементу: {locator}")
        public void click(By locator) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        }

        @Step("Ввод текста '{text}' в поле: {locator}")
        public void type(By locator, String text) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
        }

        @Step("Проверка видимости элемента: {locator}")
        public boolean isVisible(By locator) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        }

        @Step("Проверка видимости элемента с таймаутом: {locator}")
        public boolean isVisibleWithTimeout(By locator, Duration timeout) {
            try {
                new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
                return true;
            } catch (TimeoutException e) {
                return false;
            }
        }

        @Step("Получение текста элемента: {locator}")
        public String getText(By locator) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        }

        @Step("Принудительный клик через JavaScript: {locator}")
        public void jsClick(By locator) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}