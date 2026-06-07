package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {

    public static String getMyIP(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String json_str = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);
            return obj.get("ip").toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
