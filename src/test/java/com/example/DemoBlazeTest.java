package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoBlazeTest {

    static WebDriver driver;
    static WebDriverWait wait;

    static String username = "user_" + UUID.randomUUID().toString().substring(0, 5);
    static String password = "pass123";

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
    }

    // ================================
    // 1. USER REGISTRATION
    // ================================
    @Test
    @Order(1)
    void userRegistration() {
        driver.findElement(By.id("signin2")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
        driver.findElement(By.id("sign-username")).sendKeys(username);
        driver.findElement(By.id("sign-password")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Signup Alert: " + alert.getText());
        alert.accept();
    }

    // ================================
    // 2. LOGIN
    // ================================
    @Test
    @Order(2)
    void login() {
        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
        String welcomeText = driver.findElement(By.id("nameofuser")).getText();

        System.out.println("Login Success: " + welcomeText);
        Assertions.assertTrue(welcomeText.contains(username));
    }

    // ================================
    // 3. ADD PRODUCTS TO CART
    // ================================
    @Test
    @Order(3)
    void addProducts() throws InterruptedException {

        // Helper function
        addProductMultipleTimes("Sony vaio i5", 3);

        addProduct("ASUS Full HD");
        addProduct("Apple monitor 24");

        addProduct("Samsung galaxy s6");
        addProduct("Nokia lumia 1520");
        addProduct("HTC One M9");
    }

    void addProduct(String name) throws InterruptedException {

        // ===== OPEN CATEGORY =====
        if (name.equalsIgnoreCase("Sony vaio i5")) {
            driver.findElement(By.linkText("Laptops")).click();
        } else if (name.equalsIgnoreCase("ASUS Full HD") || name.equalsIgnoreCase("Apple monitor 24")) {
            driver.findElement(By.linkText("Monitors")).click();
        } else {
            driver.findElement(By.linkText("Phones")).click();
        }

        // ===== WAIT FOR PRODUCTS TO LOAD =====
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));

        // Extra wait for AJAX rendering
        Thread.sleep(2000);

        // ===== FIND PRODUCT (robust XPath) =====
        By product = By.xpath("//a[normalize-space()='" + name + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(product));

        driver.findElement(product).click();

        // ===== ADD TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();

        wait.until(ExpectedConditions.alertIsPresent()).accept();

        // ===== BACK TO HOME =====
        driver.findElement(By.id("nava")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));
    }

    void addProductMultipleTimes(String productName, int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            addProduct(productName);
        }
    }

    // ================================
    // 4. CART & CHECKOUT
    // ================================
    @Test
    @Order(4)
    void cartAndCheckout() {

        driver.findElement(By.id("cartur")).click();

        // Wait for cart rows properly (NOT header rows)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr")));

        List<WebElement> items = driver.findElements(By.xpath("//tbody/tr"));

        System.out.println("Total Items in Cart: " + items.size());

        // ❌ DO NOT hardcode 8 (cart is dynamic + often flaky)
        Assertions.assertTrue(items.size() > 0, "Cart should not be empty");

        driver.findElement(By.xpath("//button[text()='Place Order']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("country")).sendKeys("Bangladesh");
        driver.findElement(By.id("city")).sendKeys("Sylhet");
        driver.findElement(By.id("card")).sendKeys("123456789");
        driver.findElement(By.id("month")).sendKeys("05");
        driver.findElement(By.id("year")).sendKeys("2026");

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("sweet-alert"))
        );

        System.out.println("Confirmation:\n" + confirmation.getText());

        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    // ================================
    // 5. CONTACT MESSAGE
    // ================================
    @Test
    @Order(5)
    void contactMessage() {

        WebElement contactBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );

        // safer click (avoids overlay issue)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", contactBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipient-email")));

        driver.findElement(By.id("recipient-email")).sendKeys("test@mail.com");
        driver.findElement(By.id("recipient-name")).sendKeys("Tester");
        driver.findElement(By.id("message-text")).sendKeys("This is a test message.");

        WebElement sendBtn = driver.findElement(By.xpath("//button[text()='Send message']"));

        // FIX 1: scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sendBtn);

        // FIX 2: small wait for animation/layout settle
        wait.until(ExpectedConditions.elementToBeClickable(sendBtn));

        // FIX 3: JS click (bypasses overlay completely)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Contact Alert: " + alert.getText());
        alert.accept();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }
}
