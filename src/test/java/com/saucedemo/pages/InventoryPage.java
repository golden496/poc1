package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(InventoryPage.class);
    
    // ============ LOCATORS ============
    @FindBy(className = "inventory_item")
    private List<WebElement> productList;
    
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.id("shopping_cart_container");
    private By sortDropdown = By.className("product_sort_container");
    private By productName = By.className("inventory_item_name");
    private By addToCartBtn = By.xpath("//button[contains(text(), 'Add to cart')]");
    private By inventoryContainer = By.id("inventory_container");
    
    /**
     * Constructor
     */
    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 InventoryPage initialized");
    }
    
    /**
     * Get all products
     */
    public int getTotalProducts() {
        int total = productList.size();
        logger.info("📦 Total products: " + total);
        return total;
    }
    
    /**
     * Get product names
     */
    public void printAllProducts() {
        logger.info("📋 Available products:");
        for (int i = 0; i < productList.size(); i++) {
            String productName = productList.get(i)
                    .findElement(By.className("inventory_item_name"))
                    .getText();
            logger.info((i + 1) + ". " + productName);
        }
    }
    
    /**
     * Click on product by index
     */
    public ProductPage clickProductByIndex(int index) {
        logger.info("🖱️ Clicking on product at index: " + index);
        try {
            WebElement product = productList.get(index);
            product.findElement(By.className("inventory_item_name")).click();
            logger.info("✅ Product clicked");
            return new ProductPage(driver);
        } catch (IndexOutOfBoundsException e) {
            logger.error("❌ Product index out of bounds: " + index);
            throw e;
        }
    }
    
    /**
     * Add product to cart by index
     */
    public void addProductToCartByIndex(int index) {
        logger.info("🛒 Adding product at index " + index + " to cart");
        try {
            WebElement product = productList.get(index);
            WebElement addBtn = product.findElement(addToCartBtn);
            addBtn.click();
            logger.info("✅ Product added to cart");
        } catch (Exception e) {
            logger.error("❌ Failed to add product: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Get cart item count
     */
    public int getCartItemCount() {
        try {
            String count = waitHelper.waitForElementToBeVisible(cartBadge)
                    .getText();
            logger.info("🛒 Cart items: " + count);
            return Integer.parseInt(count);
        } catch (Exception e) {
            logger.info("ℹ️ Cart is empty");
            return 0;
        }
    }
    
    /**
     * Go to cart
     */
    public CartPage goToCart() {
        logger.info("🛒 Going to cart...");
        click(cartLink);
        waitHelper.waitForElementToBeVisible(By.id("cart_contents_container"));
        logger.info("✅ Navigated to cart");
        return new CartPage(driver);
    }
    
    /**
     * Check if inventory page is loaded
     */
    public boolean isInventoryPageLoaded() {
        boolean loaded = isElementDisplayed(inventoryContainer);
        logger.info("ℹ️ Inventory page loaded: " + loaded);
        return loaded;
    }
}