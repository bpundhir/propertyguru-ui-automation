package com.cars24.automation.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {
    private static final ThreadLocal<ExtentTest> TESTS = new ThreadLocal<>();
    private static ExtentReports reports;

    private ExtentReportManager() {
    }

    public static synchronized void initialize(Path reportsDirectory) {
        if (reports != null) {
            return;
        }

        try {
            Files.createDirectories(reportsDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create report directory: " + reportsDirectory, exception);
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
                reportsDirectory.resolve("extent-report.html").toString());
        sparkReporter.config().setDocumentTitle("PropertyGuru Automation Report");
        sparkReporter.config().setReportName("UI Test Execution");

        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
    }

    public static synchronized void startTest(String testName) {
        ensureInitialized();
        TESTS.set(reports.createTest(testName));
    }

    public static void info(String message) {
        currentTest().info(message);
    }

    public static void assertionFailure(AssertionError error) {
        currentTest().fail("Assertion failed: " + error.getMessage());
    }

    public static void pass(String message) {
        currentTest().pass(message);
    }

    public static void fail(Throwable throwable, Path screenshotPath) {
        Throwable failure = throwable == null
                ? new AssertionError("Test failed without a captured exception")
                : throwable;
        if (screenshotPath == null) {
            currentTest().fail(failure);
            return;
        }

        currentTest().fail(failure,
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.toString()).build());
    }

    public static synchronized void flush() {
        if (reports != null) {
            reports.flush();
            reports = null;
        }
        TESTS.remove();
    }

    public static void clearTest() {
        TESTS.remove();
    }

    private static ExtentTest currentTest() {
        ExtentTest test = TESTS.get();
        if (test == null) {
            throw new IllegalStateException("Extent test has not been started");
        }
        return test;
    }

    private static void ensureInitialized() {
        if (reports == null) {
            throw new IllegalStateException("Extent Reports has not been initialized");
        }
    }
}
