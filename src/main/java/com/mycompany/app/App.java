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
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebElement passwordText = webDriver.findElement(By.cssSelector("div.verybigtext b"));
            System.out.println("Task 1: " + passwordText.getText());
            System.out.println("Task 2: " + Task2.getMyIP(webDriver));
            System.out.println("Task 3: \n" + Task3.getWeatherForecast(webDriver));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            webDriver.quit();
        }
    }
}
