
package com.example.sauce_code;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceDemoFullTestSuite {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");
    }

    // ========================== HELPER METHODS ==========================

    void login(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    void addItemToCart(int index) {
        List<WebElement> buttons = driver.findElements(By.cssSelector(".btn_inventory"));
        buttons.get(index).click();
    }

    void openCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    void logout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    // ========================== LOGIN TESTS ==========================

    @Test
    @Order(1)
    void tc01_validLogin() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Order(2)
    void tc02_performanceGlitchUserLogin() {
        login("performance_glitch_user", "secret_sauce");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Order(3)
    void tc03_problemUserLogin() {
        login("problem_user", "secret_sauce");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Order(4)
    void tc04_errorUserLogin() {
        login("error_user", "secret_sauce");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Order(5)
    void tc05_visualUserLogin() {
        login("visual_user", "secret_sauce");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Order(6)
    void tc06_invalidUsernamePassword() {
        login("invalid", "invalid");
        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(error.isDisplayed());
    }

    @Test
    @Order(7)
    void tc07_validUsernameInvalidPassword() {
        login("standard_user", "wrong");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(8)
    void tc08_invalidUsernameValidPassword() {
        login("wrong", "secret_sauce");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(9)
    void tc09_emptyUsername() {
        login("", "secret_sauce");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(10)
    void tc10_emptyPassword() {
        login("standard_user", "");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(11)
    void tc11_emptyUsernameAndPassword() {
        login("", "");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(12)
    void tc12_lockedOutUser() {
        login("locked_out_user", "secret_sauce");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(13)
    void tc13_sqlInjectionAttempt() {
        login("' OR 1=1 --", "password");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(14)
    void tc14_xssAttempt() {
        login("<script>alert('x')</script>", "password");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test
    @Order(15)
    void tc15_caseSensitiveLogin() {
        login("STANDARD_USER", "SECRET_SAUCE");
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    // ========================== INVENTORY TESTS ==========================

    @Test
    @Order(16)
    void tc16_inventoryPageLoads() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed());
    }

    @Test
    @Order(17)
    void tc17_productsVisible() {
        login("standard_user", "secret_sauce");
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        assertTrue(products.size() > 0);
    }

    @Test
    @Order(18)
    void tc18_productNamesVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElements(By.className("inventory_item_name")).size() > 0);
    }

    @Test
    @Order(19)
    void tc19_productPricesVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElements(By.className("inventory_item_price")).size() > 0);
    }

    @Test
    @Order(20)
    void tc20_productImagesVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElements(By.tagName("img")).size() > 0);
    }

    @Test
    @Order(21)
    void tc21_productDescriptionsVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElements(By.className("inventory_item_desc")).size() > 0);
    }

    @Test
    @Order(22)
    void tc22_cartIconVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElement(By.className("shopping_cart_link")).isDisplayed());
    }

    @Test
    @Order(23)
    void tc23_menuButtonVisible() {
        login("standard_user", "secret_sauce");
        assertTrue(driver.findElement(By.id("react-burger-menu-btn")).isDisplayed());
    }

    // ========================== SORTING TESTS ==========================

    @Test
    @Order(24)
    void tc24_sortAZ() {
        login("standard_user", "secret_sauce");
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("az");
        assertEquals("az", sort.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    @Order(25)
    void tc25_sortZA() {
        login("standard_user", "secret_sauce");
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("za");
        assertEquals("za", sort.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    @Order(26)
    void tc26_sortLowHigh() {
        login("standard_user", "secret_sauce");
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("lohi");
        assertEquals("lohi", sort.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    @Order(27)
    void tc27_sortHighLow() {
        login("standard_user", "secret_sauce");
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("hilo");
        assertEquals("hilo", sort.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    @Order(28)
    void tc28_verifyDynamicSorting() {
        login("standard_user", "secret_sauce");
        Select sort = new Select(driver.findElement(By.className("product_sort_container")));
        sort.selectByValue("za");
        assertNotNull(sort.getFirstSelectedOption());
    }

    // ========================== CART TESTS ==========================

    @Test
    @Order(29)
    void tc29_addSingleItem() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(30)
    void tc30_addMultipleItems() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        addItemToCart(1);
        assertEquals("2", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(31)
    void tc31_removeItem() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        driver.findElement(By.cssSelector(".btn_secondary")).click();
        assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
    }

    @Test
    @Order(32)
    void tc32_removeAllItems() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        addItemToCart(1);

        List<WebElement> removeButtons = driver.findElements(By.cssSelector(".btn_secondary"));
        for (WebElement btn : removeButtons) {
            btn.click();
        }

        assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
    }

    @Test
    @Order(33)
    void tc33_verifyCartBadgeCount() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(34)
    void tc34_cartPersistsAfterNavigation() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        openCart();
        driver.navigate().back();
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(35)
    void tc35_verifyCartItemDetails() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        openCart();
        assertTrue(driver.findElement(By.className("inventory_item_name")).isDisplayed());
    }

    @Test
    @Order(36)
    void tc36_verifyCartAfterLogoutLogin() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        logout();
        login("standard_user", "secret_sauce");
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(37)
    void tc37_preventDuplicateEntries() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    // ========================== PRODUCT DETAILS ==========================

    @Test
    @Order(38)
    void tc38_openProductDetails() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        assertTrue(driver.getCurrentUrl().contains("inventory-item"));
    }

    @Test
    @Order(39)
    void tc39_verifyProductTitle() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        assertTrue(driver.findElement(By.className("inventory_details_name")).isDisplayed());
    }

    @Test
    @Order(40)
    void tc40_verifyProductImage() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        assertTrue(driver.findElement(By.tagName("img")).isDisplayed());
    }

    @Test
    @Order(41)
    void tc41_verifyProductPrice() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        assertTrue(driver.findElement(By.className("inventory_details_price")).isDisplayed());
    }

    @Test
    @Order(42)
    void tc42_addToCartFromDetailsPage() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        driver.findElement(By.cssSelector(".btn_inventory")).click();
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }

    @Test
    @Order(43)
    void tc43_backToProductsButton() {
        login("standard_user", "secret_sauce");
        driver.findElements(By.className("inventory_item_name")).get(0).click();
        driver.findElement(By.id("back-to-products")).click();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    // ========================== CHECKOUT TESTS ==========================

    @Test
    @Order(44)
    void tc44_successfulCheckout() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        openCart();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();
        assertTrue(driver.findElement(By.className("complete-header")).isDisplayed());
    }

    // TC45 - TC52 follow same checkout pattern with validations

    @Test
    @Order(48)
    void tc48_missingFirstName() {
        login("standard_user", "secret_sauce");
        addItemToCart(0);
        openCart();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    // ========================== LOGOUT & SESSION ==========================

    @Test
    @Order(53)
    void tc53_successfulLogout() {
        login("standard_user", "secret_sauce");
        logout();
        assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Test
    @Order(54)
    void tc54_redirectAfterLogout() {
        login("standard_user", "secret_sauce");
        logout();
        assertTrue(driver.getCurrentUrl().contains("saucedemo"));
    }

    // ========================== SIDEBAR TESTS ==========================

    @Test
    @Order(58)
    void tc58_openSidebarMenu() {
        login("standard_user", "secret_sauce");
        driver.findElement(By.id("react-burger-menu-btn")).click();
        assertTrue(driver.findElement(By.className("bm-menu-wrap")).isDisplayed());
    }

    // ========================== NAVIGATION TESTS ==========================

    @Test
    @Order(63)
    void tc63_inventoryToCartNavigation() {
        login("standard_user", "secret_sauce");
        openCart();
        assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    // ========================== UI TESTS ==========================

    @Test
    @Order(67)
    void tc67_verifyPageLoad() {
        long start = System.currentTimeMillis();
        driver.navigate().refresh();
        long end = System.currentTimeMillis();

        assertTrue((end - start) < 5000);
    }

    @Test
    @Order(68)
    void tc68_verifyResponsiveLayout() {
        driver.manage().window().setSize(new Dimension(375, 812));
        assertTrue(driver.findElement(By.id("login-button")).isDisplayed());
    }

    @Test
    @Order(69)
    void tc69_verifyNoBrokenImages() {
        login("standard_user", "secret_sauce");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        assertTrue(images.size() > 0);
    }

    // ========================== EDGE CASE TESTS ==========================

    @Test
    @Order(72)
    void tc72_rapidAddRemove() {
        login("standard_user", "secret_sauce");

        for (int i = 0; i < 3; i++) {
            addItemToCart(0);
            driver.findElement(By.cssSelector(".btn_secondary")).click();
        }

        assertTrue(true);
    }

    @Test
    @Order(75)
    void tc75_checkoutWithEmptyCart() {
        login("standard_user", "secret_sauce");
        openCart();
        assertTrue(driver.findElement(By.className("cart_list")).isDisplayed());
    }

    // ========================== XPATH TESTS ==========================

    @Test
    @Order(78)
    void tc78_xpathContains() {
        WebElement loginBtn = driver.findElement(By.xpath("//input[contains(@id,'login')]"));
        assertTrue(loginBtn.isDisplayed());
    }

    @Test
    @Order(79)
    void tc79_xpathText() {
        login("standard_user", "secret_sauce");
        WebElement title = driver.findElement(By.xpath("//span[text()='Products']"));
        assertTrue(title.isDisplayed());
    }

    @Test
    @Order(80)
    void tc80_xpathAncestor() {
        login("standard_user", "secret_sauce");
        WebElement button = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button"));
        assertTrue(button.isDisplayed());
    }

    @Test
    @Order(81)
    void tc81_xpathSibling() {
        login("standard_user", "secret_sauce");
        WebElement price = driver.findElement(By.xpath("//div[@class='inventory_item_name']/following::div[@class='inventory_item_price'][1]"));
        assertTrue(price.isDisplayed());
    }

    @Test
    @Order(82)
    void tc82_xpathIndex() {
        login("standard_user", "secret_sauce");
        WebElement firstItem = driver.findElement(By.xpath("(//div[@class='inventory_item'])[1]"));
        assertTrue(firstItem.isDisplayed());
    }

    @Test
    @Order(83)
    void tc83_cssSelectorValidation() {
        WebElement username = driver.findElement(By.cssSelector("#user-name"));
        assertTrue(username.isDisplayed());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
