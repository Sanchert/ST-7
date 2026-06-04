package com.mycompany.app;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

/**
 * Задание №3: почасовой прогноз для Нижнего Новгорода (56°N, 44°E).
 */
public final class Task3 {

    private static final String FORECAST_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                    + "&hourly=temperature_2m,rain&current=cloud_cover"
                    + "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    private static final int MAX_LOAD_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 2_000L;

    private Task3() {
    }

    public static void getWeatherForecast(WebDriver webDriver) {
        try {
            JSONObject forecast = loadForecastWithRetry(webDriver);
            ForecastReportWriter.printAndSave(forecast);
            System.out.println("Таблица сохранена: " + DriverSettings.FORECAST_OUTPUT);
        } catch (Exception e) {
            System.out.println("[ERR] T3: " + e);
        }
    }

    private static JSONObject loadForecastWithRetry(WebDriver webDriver) throws Exception {
        Exception lastFailure = null;

        for (int attempt = 1; attempt <= MAX_LOAD_ATTEMPTS; attempt++) {
            try {
                return JsonPageReader.readObject(webDriver, FORECAST_URL);
            } catch (Exception e) {
                lastFailure = e;
                if (attempt < MAX_LOAD_ATTEMPTS) {
                    System.out.println("Попытка " + attempt + " не удалась, повтор...");
                    Thread.sleep(RETRY_DELAY_MS);
                }
            }
        }

        throw lastFailure;
    }
}
