package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {

    private static final String FORECAST_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                    + "&hourly=temperature_2m,rain&current=cloud_cover"
                    + "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    private static final int MAX_ATTEMPTS = 3;

    public static void getWeatherForecast(WebDriver webDriver) {
        try {
            JSONObject forecast = loadForecastJson(webDriver);

            JSONObject hourly = (JSONObject) forecast.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            String header = String.format("| %-3s | %-20s | %-11s | %-12s |",
                    "№", "Дата/время", "Температура", "Осадки (мм)");
            String separator = "------------------------------------------------";

            System.out.println(header);
            System.out.println(separator);

            new File("result").mkdirs();
            FileWriter fileWriter = new FileWriter("result/forecast.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(header);
            printWriter.println(separator);

            for (int i = 0; i < times.size(); ++i) {
                String time = (String) times.get(i);
                double temp = ((Number) temperatures.get(i)).doubleValue();
                double rain = ((Number) rains.get(i)).doubleValue();

                String tempStr = String.format("%.1f°C", temp);
                String rainStr = String.format("%.2f", rain);
                String row = String.format("| %-3d | %-20s | %-11s | %-12s |",
                        (i + 1), time, tempStr, rainStr);

                System.out.println(row);
                printWriter.println(row);
            }

            System.out.println(separator);
            printWriter.println(separator);
            printWriter.close();

            System.out.println("Таблица сохранена: result/forecast.txt");

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }

    private static JSONObject loadForecastJson(WebDriver webDriver) throws Exception {
        Exception lastError = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                webDriver.get(FORECAST_URL);
                WebElement elem = webDriver.findElement(By.tagName("pre"));
                String jsonStr = elem.getText();

                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse(jsonStr);
            } catch (Exception e) {
                lastError = e;
                System.out.println("Попытка " + attempt + " не удалась, повтор...");
                Thread.sleep(2000L);
            }
        }

        throw lastError;
    }
}
