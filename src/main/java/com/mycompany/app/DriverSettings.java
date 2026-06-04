package com.mycompany.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

final class DriverSettings {

    static final String CHROME_DRIVER_PATH =
            "C:/tmp/chrome-win64/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    static final String CHROME_BINARY_PATH = "C:/tmp/chrome-win64/chrome-win64/chrome.exe";

    static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(90);
    static final Duration IMPLICIT_WAIT = Duration.ofSeconds(10);
    static final Duration EXPLICIT_WAIT = Duration.ofSeconds(15);

    static final Path FORECAST_OUTPUT = Paths.get("result", "forecast.txt");

    private DriverSettings() {
    }
}
