package ru.yandex.praktikum;

import driver.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;
    private final WebActions actions;

    private final By bunsTab = By.xpath("//span[text()='Булки']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']");
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new WebActions(driver);
    }

    @Step("Переход к вкладке «Булки»")
    public void clickBunsTab() {
        actions.click(bunsTab);
    }

    @Step("Переход к вкладке «Соусы»")
    public void clickSaucesTab() {
        actions.click(saucesTab);
    }

    @Step("Переход к вкладке «Начинки»")
    public void clickFillingsTab() {
        actions.click(fillingsTab);
    }

    @Step("Получение текста активной вкладки")
    public String getActiveTabText() {
        return actions.getText(activeTab).trim();
    }

    @Step("Ожидание активации вкладки: {expectedText}")
    private boolean waitForActiveTab(String expectedText) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> getActiveTabText().equals(expectedText));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Проверка, что вкладка «Булки» активна")
    public boolean isBunsTabActive() {
        clickSaucesTab();
        waitForActiveTab("Соусы");
        clickBunsTab();
        return waitForActiveTab("Булки");
    }

    @Step("Проверка, что вкладка «Соусы» активна")
    public boolean isSaucesTabActive() {
        clickBunsTab();
        waitForActiveTab("Булки");
        clickSaucesTab();
        return waitForActiveTab("Соусы");
    }

    @Step("Проверка, что вкладка «Начинки» активна")
    public boolean isFillingsTabActive() {
        clickBunsTab();
        waitForActiveTab("Булки");
        clickFillingsTab();
        return waitForActiveTab("Начинки");
    }
}