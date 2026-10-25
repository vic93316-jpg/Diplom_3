package ru.yandex.praktikum;

import driver.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {

    private final WebActions actions;
    private final By profileHeader = By.xpath("//h2[text()='Профиль'] | //a[text()='Профиль']");

    public ProfilePage(WebDriver driver) {
        this.actions = new WebActions(driver);
    }

    @Step("Проверка, что открыт личный кабинет")
    public boolean isProfileHeaderVisible() {
        return actions.isVisible(profileHeader);
    }
}