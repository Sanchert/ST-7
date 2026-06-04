package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Точка входа: задание №1 и запуск {@link Task2}, {@link Task3}.
 */
public class App {

    private static final String PASSWORD_GENERATOR_URL =
            "https://www.calculator.net/password-generator.html";
    private static final String PASSWORD_SELECTOR = "#resultid div.verybigtext b";

    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.createChromeDriver();
        try {
            printGeneratedPassword(webDriver);
            Task2.getMyIP(webDriver);
            Task3.getWeatherForecast(webDriver);
        } catch (Exception e) {
            System.out.println("[ERR] T1: " + e);
        } finally {
            webDriver.quit();
        }
    }

    private static void printGeneratedPassword(WebDriver webDriver) {
        webDriver.get(PASSWORD_GENERATOR_URL);

        WebDriverWait wait = new WebDriverWait(webDriver, DriverSettings.EXPLICIT_WAIT);
        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PASSWORD_SELECTOR)));

        System.out.println("Сгенерированный пароль: " + password.getText());
    }
}
