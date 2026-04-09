package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TC003_AddToCartTest extends BaseTest {
    
    private static final Logger logger = LogManager.getLogger(TC003_AddToCartTest.class);
    private InventoryPage inventoryPage;
    
    /**
     * BeforeMethod: Login before each test
     */
    @BeforeMethod
    public void loginBeforeTest() {
        logger.info("🔑 Logging in before test...");
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
    }
    
    /**
     * Test 1: Add single product to cart
     */
    @Test(description = "Add single product to cart")
    public void testAddSingleProductToCart() {
        logger.info("===== TEST: Add Single Product =====");
        
        // Add product at index 0
        inventoryPage.addProductToCartByIndex(0);
        
        // Verify: Cart badge shows 1
        int cartCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(cartCount, 1, 
                "Cart should have 1 item");
        
        logger.info("✅ Test passed: Product added to cart");
    }
    
    /**
     * Test 2: Add multiple products to cart
     */
    @Test(description = "Add multiple products to cart")
    public void testAddMultipleProducts() {
        logger.info("===== TEST: Add Multiple Products =====");
        
        // Add first product
        inventoryPage.addProductToCartByIndex(0);
        logger.info("✓ Added product 1");
        
        // Add second product
        inventoryPage.addProductToCartByIndex(1);
        logger.info("✓ Added product 2");
        
        // Add third product
        inventoryPage.addProductToCartByIndex(2);
        logger.info("✓ Added product 3");
        
        // Verify: Cart badge shows 3
        int cartCount = inventoryPage.getCartItemCount();
        Assert.assertEquals(cartCount, 3, 
                "Cart should have 3 items");
        
        logger.info("✅ Test passed: 3 products added to cart");
    }
    
    /**
     * Test 3: View cart items
     */
    @Test(description = "View and verify cart items")
    public void testViewCartItems() {
        logger.info("===== TEST: View Cart Items =====");
        
        // Add products
        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        
        // Go to cart
        CartPage cartPage = inventoryPage.goToCart();
        
        // Verify: Cart has 2 items
        int cartCount = cartPage.getCartItemCount();
        Assert.assertEquals(cartCount, 2, 
                "Cart should have 2 items");
        
        // Print cart items
        cartPage.printCartItems();
        
        logger.info("✅ Test passed: Cart items verified");
    }
    
    /**
     * Test 4: Remove product from cart
     */
    @Test(description = "Remove product from cart")
    public void testRemoveProductFromCart() {
        logger.info("===== TEST: Remove Product from Cart =====");
        
        // Add 2 products
        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        
        // Go to cart
        CartPage cartPage = inventoryPage.goToCart();
        
        // Verify: 2 items
        Assert.assertEquals(cartPage.getCartItemCount(), 2, 
                "Should have 2 items initially");
        
        // Remove first item
        cartPage.removeItemByIndex(0);
        
        // Verify: 1 item left
        Assert.assertEquals(cartPage.getCartItemCount(), 1, 
                "Should have 1 item after removal");
        
        logger.info("✅ Test passed: Product removed successfully");
    }
}