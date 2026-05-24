package com.cars24.automation.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

public final class FrameworkConfig {
    private static final String DEFAULT_ENVIRONMENT = "qa";
    private static final String ENVIRONMENT_PROPERTY = "environment";

    private final Properties values;
    private final String environment;

    private FrameworkConfig(Properties values, String environment) {
        this.values = values;
        this.environment = environment;
    }

    public static FrameworkConfig load() {
        String environment = System.getProperty(ENVIRONMENT_PROPERTY, DEFAULT_ENVIRONMENT).trim();
        String resourceName = "config/%s.properties".formatted(environment);
        Properties values = new Properties();

        try (InputStream stream = FrameworkConfig.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (stream == null) {
                throw new IllegalStateException("Configuration profile not found: " + resourceName);
            }
            values.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load configuration profile: " + resourceName, exception);
        }

        return new FrameworkConfig(values, environment);
    }

    public String environment() {
        return environment;
    }

    public URI baseUrl() {
        return URI.create(required("base.url"));
    }

    public String saleListingsPath() {
        return required("sale.listings.path");
    }

    public String expectedSaleListingsPath() {
        return required("expected.sale.listings.path");
    }

    public String browser() {
        String browser =
                required("browser")
                        .toLowerCase(Locale.ROOT);
        return switch (browser) {
            case "chromium",
                 "firefox",
                 "webkit" -> browser;
            default ->
                    throw new IllegalStateException(
                            "Unsupported browser: "
                                    + browser
                    );
        };
    }

    public boolean headless() {
        return booleanValue("headless");
    }

    public double slowMotionMilliseconds() {
        return doubleValue("slow.motion.ms");
    }

    public long browserCloseDelayMilliseconds() {
        return longValue("browser.close.delay.ms");
    }

    public int viewportWidth() {
        return integerValue("viewport.width");
    }

    public int viewportHeight() {
        return integerValue("viewport.height");
    }

    public int actionTimeoutMilliseconds() {
        return integerValue("action.timeout.ms");
    }

    public int navigationTimeoutMilliseconds() {
        return integerValue("navigation.timeout.ms");
    }

    public Path artifactsDirectory() {
        return Path.of(required("artifacts.directory"));
    }

    public Path reportsDirectory() {
        return Path.of(required("reports.directory"));
    }

    public boolean tracingEnabled() {
        return booleanValue("trace.enabled");
    }

    private String required(String key) {
        String value = System.getProperty(key, values.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required configuration value is missing: " + key);
        }
        return value.trim();
    }

    private int integerValue(String key) {
        try {
            return Integer.parseInt(required(key));
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Configuration value must be an integer: " + key, exception);
        }
    }

    private double doubleValue(String key) {
        try {
            return Double.parseDouble(required(key));
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Configuration value must be numeric: " + key, exception);
        }
    }

    private long longValue(String key) {
        try {
            return Long.parseLong(required(key));
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Configuration value must be a long integer: " + key, exception);
        }
    }

    private boolean booleanValue(String key) {
        String value = required(key);
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
            throw new IllegalStateException("Configuration value must be true or false: " + key);
        }
        return Boolean.parseBoolean(value);
    }
}
