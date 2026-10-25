package ru.yandex.praktikum;

import driver.DriverFactory.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebActions actions;

    private final By nameInput = By.name("name");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.name("Пароль");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By errorMessage = By.xpath("//p[contains(@class,'input__error')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new WebActions(driver);
    }

    @Step("Регистрация пользователя: {name}, {email}, {password}")
    public void register(String name, String email, String password) {
        actions.type(nameInput, name);
        actions.type(emailInput, email);
        actions.type(passwordInput, password);
        actions.click(registerButton);
    }

    @Step("Переход на форму входа со страницы регистрации")
    public LoginPage clickLoginLink() {
        actions.click(loginLink);
        return new LoginPage(driver);
    }

    @Step("Получение текста ошибки при регистрации")
    public String getErrorMessage() {
        return actions.getText(errorMessage);
    }

    @Step("Проверка, что отображается ошибка")
    public boolean isErrorVisible() {
        return actions.isVisibleWithTimeout(errorMessage, Duration.ofSeconds(3));
    }
}