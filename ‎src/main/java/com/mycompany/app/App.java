package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/tmp/chrome-win64/chromedriver-win64/chromedriver-win64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/tmp/chrome-win64/chrome-win64/chrome.exe");

        WebDriver webDriver = new ChromeDriver(options);
        try {
            System.out.println("=== Задание №1 ===");
            webDriver.get("https://www.calculator.net/password-generator.html");

            // Даем странице время загрузиться
            Thread.sleep(2000);

            // Находим поле с паролем (возможно, другой селектор)
            WebElement passwordElement = webDriver.findElement(By.cssSelector("#resultid div.verybigtext b"));
            System.out.println("Сгенерированный пароль: " + passwordElement.getText());

            // Задание №2
            Task2.getMyIP(webDriver);

            // Задание №3
            Task3.getWeatherForecast(webDriver);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            // Закрываем браузер
            webDriver.quit();
        }
    }
}