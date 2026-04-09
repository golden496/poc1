package com.saucedemo.pages;

import java.util.List;
import java.util.logging.LogManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(CartPage.class);
    
    // ============ LOCATORS ============
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;
    
    private By cartItemName = By.className("inventory_item_name");
    private By checkoutBtn = By.id("checkout");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By removeBtn = By.xpath("//button[contains(text(), 'Remove')]");
    private By cartContents = By.id("cart_contents_container");
    
    /**
     * Constructor
     */
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("📄 CartPage initialized");
    }
    
    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        int count = cartItems.size();
        logger.info("🛒 Items in cart: " + count);
        return count;
    }
    
    /**
     * Print all items in cart
     */
    public void printCartItems() {
        logger.info("📋 Items in cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            String itemName = cartItems.get(i)
                    .findElement(cartItemName)
                    .getText();
            logger.info((i + 1) + ". " + itemName);
        }
    }
    
    /**
     * Remove item from cart by index
     */
    public void removeItemByIndex(int index) {
        logger.info("🗑️ Removing item at index: " + index);
        try {
            WebElement item = cartItems.get(index);
            item.findElement(removeBtn).click();
            logger.info("✅ Item removed");
        } catch (Exception e) {
            logger.error("❌ Failed to remove item: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Proceed to checkout
     */
    public CheckoutPage proceedToCheckout() {
        logger.info("💳 Proceeding to checkout...");
        click(checkoutBtn);
        waitHelper.waitForElementToBeVisible(By.id("checkout_info_container"));
        logger.info("✅ Navigated to checkout");
        return new CheckoutPage(driver);
    }
    
    /**
     * Continue shopping
     */
    public InventoryPage continueShopping() {
        logger.info("🛍️ Continuing shopping...");
        click(continueShoppingBtn);
        waitHelper.waitForElementToBeVisible(By.id("inventory_container"));
        logger.info("✅ Back to inventory");
        return new InventoryPage(driver);
    }
    
    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        boolean empty = cartItems.size() == 0;
        logger.info("ℹ️ Cart empty: " + empty);
        return empty;
    }
}