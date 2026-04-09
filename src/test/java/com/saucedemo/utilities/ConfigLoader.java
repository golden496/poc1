package com.saucedemo.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    
    private static Properties properties;
    
    // Static block - runs when class is loaded
    static {
        try {
            properties = new Properties();
            String configPath = "src/test/resources/config/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("❌ Error loading config.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get property value by key
     * @param key - Property key from config.properties
     * @return - Property value
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property as integer
     */
    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
    
    /**
     * Get property as boolean
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}