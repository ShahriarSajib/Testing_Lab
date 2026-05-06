package com.example.spice_jet;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpiceJetJUnitTest {

        static WebDriver driver;
        static WebDriverWait wait;

        @BeforeAll
        public static void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();

                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }

        @Test
        @Order(1)
        public void bookFlight() {

                // 1. Navigate to website
                driver.get("https://book.spicejet.com/search.aspx");

                // 2. Select Departure City (Chennai)
                WebElement fromCity = wait.until(ExpectedConditions.elementToBeClickable(
                                By.id("ControlGroupSearchView_AvailabilitySearchInputSearchVieworiginStation1_CTXT")));
                fromCity.click();

                WebElement chennai = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//a[contains(text(),'Chennai (MAA)')]")));
                chennai.click();

                // 3. Select Arrival City (Delhi)
                WebElement delhi = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//a[contains(text(),'Delhi (DEL)')]")));
                delhi.click();

                // 4. Select DEPART DATE (20 January)
                WebElement date = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//a[text()='20']")));
                date.click();

                // 5. Select Currency (BDT)
                WebElement currencyDropdown = driver.findElement(
                                By.id("ControlGroupSearchView_AvailabilitySearchInputSearchView_DropDownListCurrency"));

                Select select = new Select(currencyDropdown);
                select.selectByVisibleText("BDT");

                // 6. Click flight search icon
                WebElement searchBtn = driver.findElement(
                                By.id("ControlGroupSearchView_AvailabilitySearchInputSearchView_ButtonSubmit"));

                searchBtn.click();

                // 7. Verification
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'Flight') or contains(text(),'Depart')]")));

                Assertions.assertTrue(driver.getTitle().length() > 0,
                                "Flight search page did not load properly");

                System.out.println("Flight search completed successfully!");
        }

        @AfterAll
        public static void tearDown() {
                if (driver != null) {
                        driver.quit();
                }
        }
}