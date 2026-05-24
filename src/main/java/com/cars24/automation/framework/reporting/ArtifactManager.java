package com.cars24.automation.framework.reporting;

import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ArtifactManager {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private final Path outputDirectory;

    public ArtifactManager(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public Path captureScreenshot(Page page, String testName) {
        Path screenshot = artifactPath(testName, "png");
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshot).setFullPage(true));
            return screenshot.toAbsolutePath();
        } catch (RuntimeException exception) {
            System.err.println("Could not capture failure screenshot: " + exception.getMessage());
            return null;
        }
    }

    public Path tracePath(String testName) {
        return artifactPath(testName, "zip");
    }

    private Path artifactPath(String testName, String extension) {
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create artifact directory: " + outputDirectory, exception);
        }

        String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        return outputDirectory.resolve("%s-%s.%s".formatted(safeName, timestamp, extension));
    }
}
