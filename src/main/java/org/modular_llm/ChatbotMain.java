package org.modular_llm;

public class ChatbotMain {
    public static void main(String[] args) {
        PluginManager pm = new PluginManager();
        pm.loadPlugin("WeatherPlugin");
        System.out.println(pm.executePlugin("WeatherPlugin", "Bengaluru"));
        pm.unloadPlugin("WeatherPlugin");
        System.out.println(pm.executePlugin("WeatherPlugin", "Bengaluru"));
    }
}
