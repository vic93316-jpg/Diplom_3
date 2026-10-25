package ru.yandex.praktikum;

import driver.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {

    private final WebDriver driver;
    private final WebActions actions;

    private final By emailInput = By.name("name");
    private final By passwordInput = By.name("Пароль");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new WebActions(driver);
    }

    @Step("Вход с email: {email} и паролем: {password}")
    public MainPage login(String email, String password) {
        actions.type(emailInput, email);
        actions.type(passwordInput, password);
        actions.click(loginButton);
        return new MainPage(driver);
    }

    @Step("Переход на форму регистрации")
    public RegisterPage clickRegisterLink() {
        actions.click(registerLink);
        return new RegisterPage(driver);
    }

    @Step("Переход на форму восстановления пароля")
    public ForgotPasswordPage clickForgotPasswordLink() {
        actions.click(forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }
}