package com.saucedemo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import com.saucedemo.utilities.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    
    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    /**
     * Run BEFORE each test class
     * Opens browser and navigates to app URL
     */
    @BeforeClass
    public void setup() {
        try {
            logger.info("🔨 Setting up test...");
            
            // 1. Get browser type from config
            String browser = ConfigLoader.get("browser").toLowerCase();
            logger.info("🌐 Browser: " + browser);
            
            // 2. Initialize appropriate driver
            switch (browser) {
                case "chrome":
                    driver = new ChromeDriver();
                    logger.info("✅ Chrome driver initialized");
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    logger.info("✅ Firefox driver initialized");
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    logger.info("✅ Edge driver initialized");
                    break;
                default:
                    logger.error("❌ Browser not supported: " + browser);
                    driver = new ChromeDriver();
            }
            
            // 3. Set timeouts
            int implicitWait = ConfigLoader.getInt("implicit.wait");
            int pageLoadWait = ConfigLoader.getInt("page.load.wait");
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadWait));
            
            logger.info("⏱️ Implicit wait: " + implicitWait + "s");
            logger.info("⏱️ Page load wait: " + pageLoadWait + "s");
            
            // 4. Delete cookies and maximize window
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            logger.info("📐 Window maximized");
            
            // 5. Navigate to application
            String appUrl = ConfigLoader.get("app.url");
            driver.get(appUrl);
            logger.info("🚀 Navigated to: " + appUrl);
            
        } catch (Exception e) {
            logger.error("❌ Setup failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Run AFTER each test class
     * Closes browser
     */
    @AfterClass
    public void tearDown() {
        try {
            if (driver != null) {
                logger.info("🧹 Closing browser...");
                driver.quit();
                logger.info("✅ Browser closed");
            }
        } catch (Exception e) {
            logger.error("❌ Teardown failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}