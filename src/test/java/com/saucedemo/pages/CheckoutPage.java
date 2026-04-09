package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);
    
    // ============ LOCATORS ============
    @FindBy(id = "first-name")
    private WebElement firstNameField;
    
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;
    
    private By continueBtn = By.id("continue");
    private By finishBtn = By.id("finish");
    private By cancelBtn = By.id("cancel");
    private By checkoutInfoContainer = By.id("checkout_info_container");
    private By checkoutSummary = By.id("checkout_summary_container");
    
    /**
     * Constructor
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 CheckoutPage initialized");
    }
    
    /**
     * Enter first name
     */
    public void enterFirstName(String firstName) {
        logger.info("📝 Entering first name: " + firstName);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }
    
    /**
     * Enter last name
     */
    public void enterLastName(String lastName) {
        logger.info("📝 Entering last name: " + lastName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }
    
    /**
     * Enter postal code
     */
    public void enterPostalCode(String postalCode) {
        logger.info("📝 Entering postal code: " + postalCode);
        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
    }
    
    /**
     * Fill checkout info
     */
    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        logger.info("📋 Filling checkout information");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }
    
    /**
     * Click continue button
     */
    public void clickContinue() {
        logger.info("▶️ Clicking continue...");
        click(continueBtn);
        waitHelper.waitForElementToBeVisible(checkoutSummary);
        logger.info("✅ Summary page loaded");
    }
    
    /**
     * Click finish button
     */
    public void clickFinish() {
        logger.info("✅ Clicking finish...");
        click(finishBtn);
        waitHelper.waitForElementToBeVisible(By.className("complete-header"));
        logger.info("✅ Order completed");
    }
    
    /**
     * Complete checkout (fill info + click continue + click finish)
     */
    public OrderConfirmationPage completeCheckout(String firstName, String lastName, String postalCode) {
        logger.info("🛒 Completing checkout...");
        fillCheckoutInfo(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
        return new OrderConfirmationPage(driver);
    }
    
    /**
     * Cancel checkout
     */
    public void cancelCheckout() {
        logger.info("❌ Cancelling checkout...");
        click(cancelBtn);
    }
}