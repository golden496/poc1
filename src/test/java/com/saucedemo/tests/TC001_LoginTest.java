package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utilities.DataReaderJSON;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TC001_LoginTest extends BaseTest {
    
    private static final Logger logger = LogManager.getLogger(TC001_LoginTest.class);
    
    /**
     * Test 1: Valid login
     */
    @Test(description = "Valid user login")
    public void testValidLogin() {
        logger.info("===== TEST: Valid Login =====");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        // Verify: Check if page title contains "Products"
        Assert.assertTrue(driver.getTitle().contains("Swag Labs"), 
                "Login failed - title doesn't contain expected text");
        logger.info("✅ Test passed: Valid login successful");
    }
    
    /**
     * Test 2: Invalid credentials
     */
    @Test(description = "Invalid login attempt")
    public void testInvalidLogin() {
        logger.info("===== TEST: Invalid Login =====");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_password");
        
        // Verify: Error message should be displayed
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface"), 
                "Error message not displayed for invalid credentials");
        logger.info("✅ Test passed: Error message displayed correctly");
    }
    
    /**
     * Test 3: Locked out user
     */
    @Test(description = "Locked out user login")
    public void testLockedOutUser() {
        logger.info("===== TEST: Locked Out User =====");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("locked"), 
                "Locked out error message not displayed");
        logger.info("✅ Test passed: Locked out user error displayed");
    }
    
    /**
     * Test 4: Data-driven login test using JSON
     */
    @Test(dataProvider = "loginDataProvider", description = "Data-driven login test")
    public void testLoginWithDataProvider(HashMap<String, String> testData) {
        logger.info("===== TEST: Data-Driven Login =====");
        logger.info("Test Type: " + testData.get("testType"));
        
        String username = testData.get("username");
        String password = testData.get("password");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        
        // Verify based on test type
        String testType = testData.get("testType");
        
        if ("validLogin".equals(testType)) {
            Assert.assertTrue(driver.getTitle().contains("Swag Labs"), 
                    "Valid login failed");
            logger.info("✅ Test passed: Valid login");
        } else {
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertFalse(errorMessage.isEmpty(), 
                    "Error message should be displayed for invalid login");
            logger.info("✅ Test passed: Error displayed for " + testType);
        }
    }
    
    /**
     * DataProvider: Read login data from JSON file
     */
    @DataProvider(name = "loginDataProvider")
    public Object[][] getLoginData() {
        DataReaderJSON reader = new DataReaderJSON();
        List<HashMap<String, String>> data = reader.getJsonData(
                "src/test/resources/testdata/LoginData.json"
        );
        
        Object[][] testData = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }
        return testData;
    }
}