package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

final class JsonPageReader {

    private static final JSONParser PARSER = new JSONParser();

    private JsonPageReader() {
    }

    static JSONObject readObject(WebDriver driver, String url) throws ParseException {
        driver.get(url);
        WebElement body = driver.findElement(By.tagName("pre"));
        Object parsed = PARSER.parse(body.getText());
        if (!(parsed instanceof JSONObject)) {
            throw new IllegalStateException("Expected JSON object in <pre> body");
        }
        return (JSONObject) parsed;
    }
}
