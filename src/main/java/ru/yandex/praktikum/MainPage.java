package ru.yandex.praktikum;

import driver.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver driver;
    private final WebActions actions;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//a[@href='/account']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new WebActions(driver); // Используем вложенный класс
    }

    @Step("Клик по кнопке «Войти в аккаунт» на главной")
    public LoginPage clickLoginButton() {
        actions.click(loginButton);
        return new LoginPage(driver);
    }

    @Step("Клик по кнопке «Личный кабинет»")
    public LoginPage clickPersonalAccountButton() {
        actions.click(personalAccountButton);
        return new LoginPage(driver);
    }

    @Step("Открытие личного кабинета авторизованным пользователем")
    public ProfilePage openProfile() {
        actions.click(personalAccountButton);
        return new ProfilePage(driver);
    }
}