package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

final class ForecastReportWriter {

    private static final String HEADER = String.format(
            Locale.ROOT, "| %-3s | %-20s | %-11s | %-12s |", "№", "Дата/время", "Температура", "Осадки (мм)");
    private static final String SEPARATOR = "------------------------------------------------";

    private ForecastReportWriter() {
    }

    static void printAndSave(JSONObject forecast) throws IOException {
        JSONObject hourly = (JSONObject) forecast.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        StringBuilder report = new StringBuilder();
        report.append(HEADER).append(System.lineSeparator());
        report.append(SEPARATOR).append(System.lineSeparator());

        for (int index = 0; index < times.size(); index++) {
            report.append(formatRow(
                    index + 1,
                    (String) times.get(index),
                    ((Number) temperatures.get(index)).doubleValue(),
                    ((Number) rains.get(index)).doubleValue()
            )).append(System.lineSeparator());
        }

        report.append(SEPARATOR).append(System.lineSeparator());

        String content = report.toString();
        System.out.println(content);

        Path parent = DriverSettings.FORECAST_OUTPUT.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(DriverSettings.FORECAST_OUTPUT, content.getBytes(StandardCharsets.UTF_8));
    }

    private static String formatRow(int number, String dateTime, double temperatureC, double rainMm) {
        return String.format(
                Locale.ROOT,
                "| %-3d | %-20s | %-11s | %-12s |",
                number,
                dateTime,
                String.format(Locale.ROOT, "%.1f°C", temperatureC),
                String.format(Locale.ROOT, "%.2f", rainMm)
        );
    }
}
