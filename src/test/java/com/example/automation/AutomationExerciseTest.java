package com.example.automation;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutomationExerciseTest {

    static WebDriver driver;

    static String email = "user" + UUID.randomUUID().toString().substring(0, 5)
            + "@gmail.com";

    static String password = "123456";

    @BeforeAll
    static void setup() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    void completeWorkflow() throws InterruptedException {

        // =========================
        // OPEN WEBSITE
        // =========================

        driver.get("https://automationexercise.com");

        // =========================
        // SIGNUP
        // =========================

        driver.findElement(By.linkText("Signup / Login"))
                .click();

        driver.findElement(By.name("name"))
                .sendKeys("Sajib");

        driver.findElement(
                By.xpath("//input[@data-qa='signup-email']"))
                .sendKeys(email);

        driver.findElement(
                By.xpath("//button[text()='Signup']"))
                .click();

        // =========================
        // ACCOUNT INFORMATION
        // =========================

        driver.findElement(By.id("id_gender1")).click();

        driver.findElement(By.id("password"))
                .sendKeys(password);

        driver.findElement(By.id("first_name"))
                .sendKeys("Shahriar");

        driver.findElement(By.id("last_name"))
                .sendKeys("Sajib");

        driver.findElement(By.id("address1"))
                .sendKeys("Dhaka");

        driver.findElement(By.id("state"))
                .sendKeys("Dhaka");

        driver.findElement(By.id("city"))
                .sendKeys("Dhaka");

        driver.findElement(By.id("zipcode"))
                .sendKeys("1207");

        driver.findElement(By.id("mobile_number"))
                .sendKeys("01700000000");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement createBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Create Account']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].click();", createBtn);

        // =========================
        // VERIFY ACCOUNT CREATED
        // =========================

        // Wait for the actual heading element
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[@data-qa='account-created']")));

        // Assertion (safe)
        assertTrue(msg.isDisplayed());
        assertEquals("ACCOUNT CREATED!", msg.getText());

        // Click Continue safely
        WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.linkText("Continue")));

        continueBtn.click();

        // =========================
        // LOGOUT
        // =========================

        driver.findElement(By.linkText("Logout"))
                .click();

        // =========================
        // LOGIN AGAIN
        // =========================

        driver.findElement(
                By.xpath("//input[@data-qa='login-email']"))
                .sendKeys(email);

        driver.findElement(
                By.xpath("//input[@data-qa='login-password']"))
                .sendKeys(password);

        driver.findElement(
                By.xpath("//button[text()='Login']"))
                .click();

        // Verify Login
        assertTrue(driver.getPageSource()
                .contains("Logged in as"));

        // =========================
        // PRODUCTS
        // =========================
        // Wait until Products link is clickable
        // Wait until visible
        WebElement products = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Products")));

        // Then clickable
        wait.until(ExpectedConditions.elementToBeClickable(products));

        products.click();

        Thread.sleep(2000);

        // Add First Product
        List<WebElement> addButtons = driver.findElements(
                By.xpath("//a[contains(text(),'Add to cart')]"));

        addButtons.get(0).click();

        Thread.sleep(2000);

        driver.findElement(
                By.xpath("//button[text()='Continue Shopping']"))
                .click();

        // Add Second Product
        addButtons.get(1).click();

        Thread.sleep(2000);

        driver.findElement(
                By.xpath("//button[text()='Continue Shopping']"))
                .click();

        // =========================
        // OPEN CART
        // =========================

        driver.findElement(By.linkText("Cart"))
                .click();

        // Verify products added
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        assertEquals(2, rows.size());

        // =========================
        // CONTACT US
        // =========================

        driver.findElement(By.linkText("Contact us"))
                .click();

        driver.findElement(By.name("name"))
                .sendKeys("Sajib");

        driver.findElement(By.name("email"))
                .sendKeys(email);

        driver.findElement(By.name("subject"))
                .sendKeys("Selenium Test");

        driver.findElement(By.id("message"))
                .sendKeys("Automation testing message");

        driver.findElement(
                By.xpath("//input[@type='submit']"))
                .click();

        // Handle Alert
        Alert alert = driver.switchTo().alert();

        alert.accept();

        // Verify Success
        assertTrue(driver.getPageSource()
                .contains("Success"));

        // =========================
        // FINAL LOGOUT
        // =========================

        driver.findElement(By.linkText("Logout"))
                .click();

        assertTrue(driver.getCurrentUrl()
                .contains("login"));
    }

    @AfterAll
    static void close() {

        driver.quit();
    }
}