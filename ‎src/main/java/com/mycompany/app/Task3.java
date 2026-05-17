package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void getWeatherForecast(WebDriver webDriver) {
        try {
            System.out.println("\n=== Задание №3 ===");

            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

            webDriver.get(url);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            // Извлекаем данные
            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            // Выводим таблицу в консоль
            System.out.println("\nПрогноз погоды в Нижнем Новгороде на 24 часа");
            System.out.println("------------------------------------------------");
            System.out.printf("| %-3s | %-20s | %-11s | %-10s |\n", "№", "Дата/время", "Температура", "Осадки (мм)");
            System.out.println("------------------------------------------------");

            // Сохраняем в файл
            FileWriter fileWriter = new FileWriter("result/forecast.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("| %-3s | %-20s | %-11s | %-10s |\n", "№", "Дата/время", "Температура", "Осадки (мм)");
            printWriter.println("------------------------------------------------");

            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                double temp = (double) temperatures.get(i);
                double rain = (double) rains.get(i);

                String tempStr = String.format("%.1f°C", temp);
                String rainStr = String.format("%.2f", rain);

                System.out.printf("| %-3d | %-20s | %-11s | %-10s |\n", (i+1), time, tempStr, rainStr);
                printWriter.printf("| %-3d | %-20s | %-11s | %-10s |\n", (i+1), time, tempStr, rainStr);
            }

            System.out.println("------------------------------------------------");
            printWriter.println("------------------------------------------------");
            printWriter.close();

            System.out.println("\nТаблица сохранена в файл: result/forecast.txt");

        } catch (Exception e) {
            System.out.println("Ошибка в Task3: " + e);
        }
    }
}