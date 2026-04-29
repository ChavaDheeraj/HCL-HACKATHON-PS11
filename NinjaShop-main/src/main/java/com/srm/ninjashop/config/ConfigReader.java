package com.srm.ninjashop.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to locate " + CONFIG_FILE + " in classpath.");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load configuration file.", exception);
        }
    }

    private ConfigReader() {
    }

    public static String getBrowser() {
        return getRequiredProperty("browser");
    }

    public static String getBaseUrl() {
        return getRequiredProperty("baseUrl");
    }

    public static long getTimeout() {
        return Long.parseLong(getRequiredProperty("timeout"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(PROPERTIES.getProperty("headless", "false"));
    }

    public static String getTestDataPath() {
        return getRequiredProperty("testDataPath");
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required configuration for key: " + key);
        }
        return value.trim();
    }
}
