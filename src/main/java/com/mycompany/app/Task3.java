package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;

public class Task3 {

    public static String getWeatherForecast(WebDriver webDriver) throws Exception {
        webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                + "&hourly=temperature_2m,rain&current=cloud_cover"
                + "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");
        WebElement elem = webDriver.findElement(By.tagName("pre"));
        String json_str = elem.getText();
        JSONParser jsonParser = new JSONParser();
        JSONObject forecast = (JSONObject) jsonParser.parse(json_str);

        JSONObject hourly = (JSONObject) forecast.get("hourly");
        JSONArray  times = (JSONArray) hourly.get("time");
        JSONArray  temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray  rains = (JSONArray) hourly.get("rain");

        StringBuilder sb = new StringBuilder();
        sb.append("№   Date               t       rain\n-----------------------------------\n");
        for (int i = 0; i < times.size(); i++) {
            sb.append(i + 1).append("   ")
                    .append(times.get(i)).append("   ")
                    .append(String.format("%.1f°C", ((Number) temperatures.get(i)).doubleValue())).append("   ")
                    .append(String.format("%.2f", ((Number) rains.get(i)).doubleValue()))
                    .append("\n");
        }
        String result = sb.toString();
        new java.io.File("result");
        FileWriter fw = new FileWriter("result/forecast.txt");
        fw.write(result);
        fw.close();
        return result;
    }
}
