package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.saucedemo.utilities.WaitHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {
    
    protected WebDriver driver;
    protected WaitHelper waitHelper;
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    
    /**
     * Constructor - Initialize WebDriver and PageFactory
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on element
     */
    public void click(By locator) {
        try {
            WebElement element = waitHelper.waitForElementToBeClickable(locator);
            element.click();
            logger.info("✅ Clicked on element: " + locator);
        } catch (Exception e) {
            logger.error("❌ Failed to click on: " + locator);
            throw e;
        }
    }
    
    /**
     * Type text in element
     */
    public void type(By locator, String text) {
        try {
            WebElement element = waitHelper.waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("✅ Typed '" + text + "' in element: " + locator);
        } catch (Exception e) {
            logger.error("❌ Failed to type in: " + locator);
            throw e;
        }
    }
    
    /**
     * Get text from element
     */
    public String getText(By locator) {
        try {
            WebElement element = waitHelper.waitForElementToBeVisible(locator);
            String text = element.getText();
            logger.info("✅ Got text: '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            logger.error("❌ Failed to get text from: " + locator);
            throw e;
        }
    }
    
    /**
     * Check if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = waitHelper.waitForElementToBeVisible(locator);
            boolean isDisplayed = element.isDisplayed();
            logger.info("✅ Element visibility: " + isDisplayed + " for: " + locator);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("❌ Element not displayed or not found: " + locator);
            return false;
        }
    }
    
    /**
     * Get page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("📄 Page title: " + title);
        return title;
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("🔗 Current URL: " + url);
        return url;
    }
}