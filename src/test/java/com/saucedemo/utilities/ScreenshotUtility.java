package com.saucedemo.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotUtility {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtility.class);
    private static final String SCREENSHOT_FOLDER = "reports/screenshots/";
    
    /**
     * Take screenshot of current screen
     * @param testName - Name of test (used for filename)
     * @return - Path to saved screenshot
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Create folder if doesn't exist
            if (!Files.exists(Paths.get(SCREENSHOT_FOLDER))) {
                Files.createDirectories(Paths.get(SCREENSHOT_FOLDER));
            }
            
            // Generate timestamp for unique filename
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_FOLDER + fileName;
            
            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            
            logger.info("✅ Screenshot saved: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("❌ Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Take screenshot on failure
     */
    public static void captureFailureScreenshot(WebDriver driver, String testName) {
        String screenshotPath = takeScreenshot(driver, testName + "_FAILURE");
        logger.warn("⚠️ Test failed. Screenshot: " + screenshotPath);
    }
}