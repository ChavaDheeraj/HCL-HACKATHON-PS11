package com.srm.ninjashop.utils;

import com.srm.ninjashop.driver.DriverFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ScreenshotUtils {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(String testName) {
        try {
            Path screenshotDirectory = Paths.get(System.getProperty("user.dir"), "screenshots");
            Files.createDirectories(screenshotDirectory);

            String fileName = testName + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path destination = screenshotDirectory.resolve(fileName);

            Path source = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

            return destination.toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + testName, exception);
        }
    }
}
