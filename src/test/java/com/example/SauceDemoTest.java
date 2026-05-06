package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25)); // increased wait
    }

    @Test
    void testSauceDemoFlow() {

        // 1. Navigate
        driver.get("https://www.saucedemo.com/");
        assertTrue(driver.getTitle().contains("Swag Labs"));

        // 2. Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify login success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        assertTrue(driver.getCurrentUrl().contains("inventory"));

        // 3. Open product
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='Sauce Labs Backpack']")));
        product.click();

        // Verify product page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_details_name")));
        assertTrue(driver.getPageSource().contains("Sauce Labs Backpack"));

        // 🔥 3.5 Add to cart (CRITICAL FIX)
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart")));
        addToCartBtn.click();

        // Verify item added (cart badge = 1)
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        assertEquals("1", cartBadge.getText());

        // 4. Back to products
        wait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-products"))).click();

        // 5. Go to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();

        // Verify cart page
        wait.until(ExpectedConditions.urlContains("cart"));

        WebElement cartTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("title"))
        );

        assertEquals("Your Cart", cartTitle.getText());

        // 6. Checkout
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutBtn.click();

        // 7. Fill form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Sajib");
        driver.findElement(By.id("last-name")).sendKeys("Test");
        driver.findElement(By.id("postal-code")).sendKeys("1230");

        // 8. Continue
        driver.findElement(By.id("continue")).click();

        // Final verification (overview page)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        assertTrue(driver.getPageSource().contains("Checkout: Overview"));
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}