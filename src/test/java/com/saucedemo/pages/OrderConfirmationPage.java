package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderConfirmationPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(OrderConfirmationPage.class);
    
    // ============ LOCATORS ============
    private By successHeader = By.className("complete-header");
    private By successMessage = By.className("complete-text");
    private By backHomeBtn = By.id("back-to-products");
    
    /**
     * Constructor
     */
    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 OrderConfirmationPage initialized");
    }
    
    /**
     * Get success header text
     */
    public String getSuccessHeader() {
        String header = getText(successHeader);
        logger.info("✅ Success header: " + header);
        return header;
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        String message = getText(successMessage);
        logger.info("✅ Success message: " + message);
        return message;
    }
    
    /**
     * Check if order is confirmed
     */
    public boolean isOrderConfirmed() {
        boolean confirmed = isElementDisplayed(successHeader);
        logger.info("ℹ️ Order confirmed: " + confirmed);
        return confirmed;
    }
    
    /**
     * Go back to home
     */
    public InventoryPage goBackToHome() {
        logger.info("🏠 Going back to home");
        click(backHomeBtn);
        waitHelper.waitForElementToBeVisible(By.id("inventory_container"));
        return new InventoryPage(driver);
    }
}