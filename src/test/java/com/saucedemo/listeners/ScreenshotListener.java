package com.saucedemo.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import com.saucedemo.utilities.ScreenshotUtility;
import com.saucedemo.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotListener implements ITestListener {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotListener.class);
    
    /**
     * Called when test starts
     */
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("➡️ TEST STARTED: " + result.getMethod().getMethodName());
    }
    
    /**
     * Called when test passes
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ TEST PASSED: " + result.getMethod().getMethodName());
    }
    
    /**
     * Called when test fails
     * Capture screenshot automatically
     */
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ TEST FAILED: " + result.getMethod().getMethodName());
        logger.error("Failure reason: " + result.getThrowable().getMessage());
        
        // Get WebDriver from test instance
        Object testInstance = result.getInstance();
        WebDriver driver = ((BaseTest) testInstance).driver;
        
        if (driver != null) {
            String testName = result.getMethod().getMethodName();
            ScreenshotUtility.captureFailureScreenshot(driver, testName);
        }
    }
    
    /**
     * Called when test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⏭️ TEST SKIPPED: " + result.getMethod().getMethodName());
    }
}