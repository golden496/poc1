package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    
    // ============ LOCATORS ============
    // Method 1: Using @FindBy (PageFactory)
    @FindBy(id = "user-name")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    // Method 2: Using By locators (for elements not using @FindBy)
    private By errorMessage = By.xpath("//h3[@data-test='error']");
    private By loginForm = By.id("login_button_container");
    
    /**
     * Constructor
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 LoginPage initialized");
    }
    
    /**
     * Enter username
     */
    public void enterUsername(String username) {
        logger.info("📝 Entering username: " + username);
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    /**
     * Enter password
     */
    public void enterPassword(String password) {
        logger.info("📝 Entering password");
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    /**
     * Click login button
     */
    public InventoryPage clickLoginButton() {
        logger.info("🔐 Clicking login button");
        loginButton.click();
        
        // Wait for inventory page to load
        waitHelper.waitForElementToBeVisible(By.id("inventory_container"), 10);
        logger.info("✅ Login successful");
        
        return new InventoryPage(driver);
    }
    
    /**
     * Login with username and password
     */
    public InventoryPage login(String username, String password) {
        logger.info("🔑 Logging in with username: " + username);
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }
    
    /**
     * Get error message
     */
    public String getErrorMessage() {
        try {
            String message = getText(errorMessage);
            logger.warn("⚠️ Error message displayed: " + message);
            return message;
        } catch (Exception e) {
            logger.info("ℹ️ No error message displayed");
            return "";
        }
    }
    
    /**
     * Check if login form is displayed
     */
    public boolean isLoginFormDisplayed() {
        boolean displayed = isElementDisplayed(loginForm);
        logger.info("ℹ️ Login form displayed: " + displayed);
        return displayed;
    }
}