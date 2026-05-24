package com.cars24.automation.framework.driver;

import com.cars24.automation.framework.config.FrameworkConfig;
import com.cars24.automation.framework.reporting.ArtifactManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Path;

public final class DriverManager implements AutoCloseable {
    private final FrameworkConfig config;
    private final ArtifactManager artifactManager;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public DriverManager(FrameworkConfig config) {
        this.config = config;
        this.artifactManager = new ArtifactManager(config.artifactsDirectory());
    }

    public void initialize() {

        if (page != null) {
            throw new IllegalStateException(
                    "Browser already initialized");
        }

        try {

            playwright = Playwright.create();

            BrowserType.LaunchOptions launchOptions =
                    new BrowserType.LaunchOptions()
                            .setHeadless(config.headless())
                            .setSlowMo(config.slowMotionMilliseconds());

            // Use real Chrome
            if ("chromium".equals(config.browser())) {
                launchOptions.setChannel("chrome");
            }

            browser = browserType().launch(launchOptions);

            context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(
                                    config.viewportWidth(),
                                    config.viewportHeight()
                            )
                            .setLocale("en-US")
                            .setUserAgent(
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                            "Chrome/124.0.0.0 Safari/537.36"
                            )
            );

            // Anti bot workaround
            context.addInitScript(
                    "Object.defineProperty(navigator, 'webdriver', {" +
                            "get: () => undefined" +
                            "});"
            );

            context.setDefaultTimeout(
                    config.actionTimeoutMilliseconds());

            context.setDefaultNavigationTimeout(
                    config.navigationTimeoutMilliseconds());

            if (config.tracingEnabled()) {
                context.tracing().start(
                        new Tracing.StartOptions()
                                .setScreenshots(true)
                                .setSnapshots(true)
                                .setSources(true)
                );
            }

            page = context.newPage();

        } catch (RuntimeException e) {
            close();
            throw e;
        }
    }

    public Page page() {
        if (page == null) {
            throw new IllegalStateException("Browser has not been initialized");
        }
        return page;
    }

    public Path captureFailureScreenshot(String testName) {
        return page == null ? null : artifactManager.captureScreenshot(page, testName);
    }

    public void finish(String testName, boolean failed) {
        try {
            if (context != null && config.tracingEnabled()) {
                if (failed) {
                    context.tracing().stop(new Tracing.StopOptions().setPath(artifactManager.tracePath(testName)));
                } else {
                    context.tracing().stop();
                }
            }
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        page = null;
    }

    private BrowserType browserType() {
        return switch (config.browser()) {
            case "chromium" -> playwright.chromium();
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> throw new IllegalStateException("Unsupported browser: " + config.browser());
        };
    }
}
