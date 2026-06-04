package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

final class ForecastReportWriter {

    private enum Alignment {
        LEFT,
        CENTER
    }

    private ForecastReportWriter() {
    }

    static void printAndSave(JSONObject forecast) throws IOException {
        String content = buildReport(forecast);
        System.out.println(content);

        Path parent = DriverSettings.FORECAST_OUTPUT.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(DriverSettings.FORECAST_OUTPUT, content.getBytes(StandardCharsets.UTF_8));
    }

    static String buildReport(JSONObject forecast) {
        JSONObject hourly = (JSONObject) forecast.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        int rowCount = times.size();

        String[] indexValues = new String[rowCount];
        String[] timeValues = new String[rowCount];
        String[] temperatureValues = new String[rowCount];
        String[] rainValues = new String[rowCount];

        for (int i = 0; i < rowCount; i++) {
            indexValues[i] = String.valueOf(i + 1);
            timeValues[i] = (String) times.get(i);
            temperatureValues[i] = String.format(Locale.ROOT, "%.1f°C",
                    ((Number) temperatures.get(i)).doubleValue());
            rainValues[i] = String.format(Locale.ROOT, "%.2f",
                    ((Number) rains.get(i)).doubleValue());
        }

        int indexWidth = columnWidth("№", indexValues, 0);
        int indexSepWidth = indexWidth + 2;

        int dateTimeWidth = columnWidth("Дата/время", timeValues, 0);
        int dateTimeSepWidth = dateTimeWidth + 2;

        int temperatureWidth = columnWidth("Температура", temperatureValues, 0);
        int temperatureSepWidth = temperatureWidth + 2;

        int rainWidth = columnWidth("Осадки (мм)", rainValues, 0);
        int rainSepWidth = rainWidth + 2;

        String header = formatDataRow(
                padLeft("№", indexWidth),
                padLeft("Дата/время", dateTimeWidth),
                padLeft("Температура", temperatureWidth),
                padLeft("Осадки (мм)", rainWidth)
        );

        String separator = formatSeparatorRow(
                formatSeparator(indexSepWidth, Alignment.CENTER),
                formatSeparator(dateTimeSepWidth, Alignment.LEFT),
                formatSeparator(temperatureSepWidth, Alignment.LEFT),
                formatSeparator(rainSepWidth, Alignment.CENTER)
        );

        StringBuilder report = new StringBuilder();
        report.append(header).append(System.lineSeparator());
        report.append(separator).append(System.lineSeparator());

        for (int i = 0; i < rowCount; i++) {
            report.append(formatDataRow(
                    padLeft(indexValues[i], indexWidth),
                    padLeft(timeValues[i], dateTimeWidth),
                    padLeft(temperatureValues[i], temperatureWidth),
                    padLeft(rainValues[i], rainWidth)
            )).append(System.lineSeparator());
        }

        return report.toString();
    }

    private static int columnWidth(String header, String[] values, int extra) {
        int max = header.length();
        for (String value : values) {
            max = Math.max(max, value.length());
        }
        return max + extra;
    }

    private static String formatDataRow(String col1, String col2, String col3, String col4) {
        return String.format(Locale.ROOT, "| %s | %s | %s | %s |", col1, col2, col3, col4);
    }

    private static String formatSeparatorRow(String col1, String col2, String col3, String col4) {
        return String.format(Locale.ROOT, "|%s|%s|%s|%s|", col1, col2, col3, col4);
    }

    private static String padLeft(String value, int width) {
        if (value.length() > width) {
            return value.substring(0, width);
        }
        return String.format(Locale.ROOT, "%-" + width + "s", value);
    }

    private static String formatSeparator(int width, Alignment alignment) {
        char[] line = new char[width];
        Arrays.fill(line, '-');
        switch (alignment) {
            case LEFT:
                line[0] = ':';
                break;
            case CENTER:
                line[0] = ':';
                line[width - 1] = ':';
                break;
        }
        return new String(line);
    }
}
