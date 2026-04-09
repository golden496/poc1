package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(ProductPage.class);
    
    // ============ LOCATORS ============
    private By productName = By.className("inventory_details_name");
    private By productPrice = By.className("inventory_details_price");
    private By productDescription = By.className("inventory_details_desc");
    private By addToCartBtn = By.id("add-to-cart");
    private By backBtn = By.id("back-to-products");
    
    /**
     * Constructor
     */
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 ProductPage initialized");
    }
    
    /**
     * Get product name
     */
    public String getProductName() {
        String name = getText(productName);
        logger.info("📦 Product name: " + name);
        return name;
    }
    
    /**
     * Get product price
     */
    public String getProductPrice() {
        String price = getText(productPrice);
        logger.info("💰 Product price: " + price);
        return price;
    }
    
    /**
     * Get product description
     */
    public String getProductDescription() {
        String desc = getText(productDescription);
        logger.info("📝 Product description: " + desc);
        return desc;
    }
    
    /**
     * Add product to cart
     */
    public void addToCart() {
        logger.info("🛒 Adding product to cart");
        click(addToCartBtn);
    }
    
    /**
     * Go back to inventory
     */
    public InventoryPage goBackToInventory() {
        logger.info("⬅️ Going back to inventory");
        click(backBtn);
        waitHelper.waitForElementToBeVisible(By.id("inventory_container"));
        logger.info("✅ Back to inventory page");
        return new InventoryPage(driver);
    }
}