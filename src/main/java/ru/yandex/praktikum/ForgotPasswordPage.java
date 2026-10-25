package ru.yandex.praktikum;

import driver.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebActions actions;
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new WebActions(driver);
    }

    @Step("Переход на форму входа со страницы восстановления пароля")
    public LoginPage clickLoginLink() {
        actions.click(loginLink);
        return new LoginPage(driver);
    }
}