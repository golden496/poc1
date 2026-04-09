package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TC002_InventoryTest extends BaseTest {
    
    private static final Logger logger = LogManager.getLogger(TC002_InventoryTest.class);
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
     * Test 1: Verify products are displayed
     */
    @Test(description = "Verify products displayed")
    public void testProductsDisplayed() {
        logger.info("===== TEST: Products Displayed =====");
        
        Assert.assertTrue(inventoryPage.isInventoryPageLoaded(), 
                "Inventory page not loaded");
        
        int productCount = inventoryPage.getTotalProducts();
        Assert.assertTrue(productCount > 0, 
                "No products found on inventory page");
        
        logger.info("✅ Test passed: " + productCount + " products displayed");
    }
    
    /**
     * Test 2: Print all products
     */
    @Test(description = "Print all products")
    public void testPrintAllProducts() {
        logger.info("===== TEST: Print All Products =====");
        
        inventoryPage.printAllProducts();
        
        int productCount = inventoryPage.getTotalProducts();
        Assert.assertTrue(productCount > 0, "No products found");
        
        logger.info("✅ Test passed: All products printed");
    }
    
    /**
     * Test 3: Click on first product
     */
    @Test(description = "Click on first product")
    public void testClickFirstProduct() {
        logger.info("===== TEST: Click First Product =====");
        
        inventoryPage.clickProductByIndex(0);
        
        // Verify: Product page should load
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Swag Labs"), 
                "Product page not loaded");
        
        logger.info("✅ Test passed: First product clicked successfully");
    }
}