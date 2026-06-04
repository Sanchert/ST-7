package com.mycompany.app;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

/**
 * Задание №2: публичный IPv4 через ipify.
 */
public final class Task2 {

    private static final String IPIFY_URL = "https://api.ipify.org/?format=json";

    private Task2() {
    }

    public static void getMyIP(WebDriver webDriver) {
        try {
            JSONObject response = JsonPageReader.readObject(webDriver, IPIFY_URL);
            String ip = (String) response.get("ip");
            System.out.println(ip);
        } catch (Exception e) {
            System.out.println("[ERR] T2: " + e);
        }
    }
}
