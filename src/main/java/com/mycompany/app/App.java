package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:/tmp/chrome-win64/chromedriver-win64/chromedriver-win64/chromedriver.exe"
        );

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/tmp/chrome-win64/chrome-win64/chrome.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
            WebElement passwordElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#resultid div.verybigtext b")));
            System.out.println("Сгенерированный пароль: " + passwordElement.getText());

            Task2.getMyIP(webDriver);
            Task3.getWeatherForecast(webDriver);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}
