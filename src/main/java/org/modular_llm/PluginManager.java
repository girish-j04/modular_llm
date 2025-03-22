package org.modular_llm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private Map<String, Plugin> plugins = new HashMap<>();

    public void loadPlugin(String pluginName) {
        try {
            File pluginDir = new File("src/plugins");
            URL[] urls = { pluginDir.toURI().toURL() };
            URLClassLoader loader = new URLClassLoader(urls);

            Class<?> clazz = loader.loadClass("plugins." + pluginName);

            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
            plugins.put(pluginName, plugin);
            System.out.println("Loaded plugin: " + pluginName);
        } catch (Exception e) {
            System.err.println("Error loading plugin: " + e.getMessage());
        }
    }

    public void unloadPlugin(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            plugins.remove(pluginName);
            System.out.println("Unloaded plugin: " + pluginName);
        }
    }

    public String executePlugin(String pluginName, String input) {
        Plugin plugin = plugins.get(pluginName);
        if (plugin != null) {
            return plugin.execute(input);
        } else {
            return "Plugin not loaded.";
        }
    }
}
