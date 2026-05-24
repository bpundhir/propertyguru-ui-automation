package com.cars24.automation.framework.base;

import com.cars24.automation.framework.config.FrameworkConfig;
import com.cars24.automation.framework.driver.DriverManager;
import com.cars24.automation.framework.pages.PropertyGuruHomePage;
import com.cars24.automation.framework.reporting.ExtentReportManager;
import com.cars24.automation.framework.validations.PropertyGuruValidation;
import com.microsoft.playwright.Page;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.net.URI;
import java.nio.file.Path;

public abstract class BaseClass {
    private final ThreadLocal<DriverManager> drivers = new ThreadLocal<>();
    private final ThreadLocal<FrameworkConfig> configurations = new ThreadLocal<>();
    protected PropertyGuruHomePage propertyGuruHomePage() {
        return new PropertyGuruHomePage(page());

    }
    protected PropertyGuruValidation propertyGuruValidation() {
        return new PropertyGuruValidation(
                propertyGuruHomePage()
        );
    }

    @BeforeSuite(alwaysRun = true)
    public void initializeReport() {
        ExtentReportManager.initialize(FrameworkConfig.load().reportsDirectory());
    }

    @BeforeMethod(alwaysRun = true)
    public void initializeBrowser(ITestResult result) {
        FrameworkConfig config = FrameworkConfig.load();
        DriverManager driverManager = new DriverManager(config);

        configurations.set(config);
        drivers.set(driverManager);
        ExtentReportManager.startTest(result.getMethod().getMethodName());

        driverManager.initialize();
        URI navigationUrl = config.baseUrl()
                .resolve(config.saleListingsPath());
        driverManager.page()
                .navigate(navigationUrl.toString());
        ExtentReportManager.info(
                "Opened URL: " + navigationUrl
        );
        ExtentReportManager.info(
                "Browser initialized: "
                        + config.browser());
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        DriverManager driverManager = drivers.get();
        boolean failed = !result.isSuccess();
        try {
            if (failed) {
                Path screenshot = driverManager == null ? null
                        : driverManager.captureFailureScreenshot(result.getMethod().getMethodName());
                ExtentReportManager.fail(result.getThrowable(), screenshot);
            } else {
                ExtentReportManager.pass("Test completed successfully");
            }
            if (driverManager != null) {
                driverManager.finish(result.getMethod().getMethodName(), failed);
            }
        } finally {
            drivers.remove();
            configurations.remove();
            ExtentReportManager.clearTest();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        ExtentReportManager.flush();
    }

    protected Page page() {
        DriverManager driverManager = drivers.get();
        if (driverManager == null) {
            throw new IllegalStateException("Driver manager is unavailable before setup");
        }
        return driverManager.page();
    }

    protected FrameworkConfig config() {
        FrameworkConfig config = configurations.get();
        if (config == null) {
            throw new IllegalStateException("Test configuration is unavailable before setup");
        }
        return config;
    }
}
