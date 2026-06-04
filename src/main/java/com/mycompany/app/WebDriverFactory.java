package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

final class WebDriverFactory {

    private WebDriverFactory() {
    }

    static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", DriverSettings.CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(DriverSettings.CHROME_BINARY_PATH);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(DriverSettings.PAGE_LOAD_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(DriverSettings.IMPLICIT_WAIT);
        return driver;
    }
}
