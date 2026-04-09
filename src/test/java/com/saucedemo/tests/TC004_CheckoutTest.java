package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.OrderConfirmationPage;
import com.saucedemo.utilities.DataReaderJSON;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TC004_CheckoutTest extends BaseTest {
    
    private static final Logger logger = LogManager.getLogger(TC004_CheckoutTest.class);
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    
    /**
     * BeforeMethod: Login and add products before each test
     */
    @BeforeMethod
    public void setupForCheckout() {
        logger.info("🔑 Setting up for checkout test...");
        
        // Login
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
        
        // Add products
        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        
        // Go to cart
        cartPage = inventoryPage.goToCart();
        
        logger.info("✅ Setup complete: 2 products added to cart");
    }
    
    /**
     * Test 1: Complete checkout with valid data
     */
    @Test(description = "Complete checkout successfully")
    public void testCompleteCheckout() {
        logger.info("===== TEST: Complete Checkout =====");
        
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout
        OrderConfirmationPage confirmationPage = checkoutPage.completeCheckout(
                "John", 
                "Doe", 
                "12345"
        );
        
        // Verify: Order confirmed
        Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
                "Order should be confirmed");
        
        String successMessage = confirmationPage.getSuccessMessage();
        logger.info("Success message: " + successMessage);
        
        logger.info("✅ Test passed: Checkout completed successfully");
    }
    
    /**
     * Test 2: Data-driven checkout with multiple users
     */
    @Test(dataProvider = "checkoutDataProvider", description = "Data-driven checkout")
    public void testCheckoutWithDataProvider(HashMap<String, String> testData) {
        logger.info("===== TEST: Data-Driven Checkout =====");
        logger.info("Testing with user: " + testData.get("firstName") + " " + testData.get("lastName"));
        
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout with test data
        OrderConfirmationPage confirmationPage = checkoutPage.completeCheckout(
                testData.get("firstName"),
                testData.get("lastName"),
                testData.get("postalCode")
        );
        
        // Verify
        Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
                "Order confirmation failed");
        
        logger.info("✅ Test passed: Checkout for " + testData.get("firstName"));
    }
    
    /**
     * DataProvider: Read checkout data from JSON
     */
    @DataProvider(name = "checkoutDataProvider")
    public Object[][] getCheckoutData() {
        DataReaderJSON reader = new DataReaderJSON();
        List<HashMap<String, String>> data = reader.getJsonData(
                "src/test/resources/testdata/CheckoutData.json"
        );
        
        Object[][] testData = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }
        return testData;
    }
}