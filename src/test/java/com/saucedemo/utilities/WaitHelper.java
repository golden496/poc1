package com.saucedemo.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitHelper {
    
    private static final Logger logger = LogManager.getLogger(WaitHelper.class);
    private WebDriver driver;
    
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Wait for element to be visible
     * @param locator - By locator
     * @param timeoutInSeconds - Max wait time
     */
    public WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        logger.info("⏳ Waiting for element: " + locator + " to be visible...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("❌ Element not visible: " + locator);
            throw e;
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator, int timeoutInSeconds) {
        logger.info("⏳ Waiting for element: " + locator + " to be clickable...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("❌ Element not clickable: " + locator);
            throw e;
        }
    }
    
    /**
     * Wait for element to be present in DOM
     */
    public WebElement waitForElementPresence(By locator, int timeoutInSeconds) {
        logger.info("⏳ Waiting for element presence: " + locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("❌ Element not found: " + locator);
            throw e;
        }
    }
    
    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextInElement(By locator, String text, int timeoutInSeconds) {
        logger.info("⏳ Waiting for text '" + text + "' in element: " + locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (Exception e) {
            logger.error("❌ Text not found in element: " + text);
            return false;
        }
    }
    
    /**
     * Wait using default timeout from config
     */
    public WebElement waitForElementToBeVisible(By locator) {
        int defaultWait = ConfigLoader.getInt("explicit.wait");
        return waitForElementToBeVisible(locator, defaultWait);
    }
}