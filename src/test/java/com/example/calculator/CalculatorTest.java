package com.example.calculator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalculatorTest {
    Calculator calculator;

    @BeforeAll
    static void setupBeforeClass() {
        System.out.println("Before the execution of this test suit, will run once");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Creating calculator instance");
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Simple multiplication should work")
    @Order(2)
    void testMultiply() {
        assertEquals(20, calculator.multiply(4, 5),
                "Regular multiplication should work");
    }

    @RepeatedTest(5)
    @DisplayName("Ensure correct handling of zero")
    @Order(1)
    void testMultiplyWithZero() {
        assertEquals(0, calculator.multiply(0, 5), "Multiple with zero should be zero");
        assertEquals(0, calculator.multiply(5, 0), "Multiple with zero should be zero");
    }

    @Test
    @DisplayName("Test addition")
    @Order(4)
    void testAddition() {
        assertEquals(9, calculator.add(4, 5),
                "Regular multiplication should work");
        assertNotEquals(10, calculator.add(4,5), "10 and 4+5 is not same");
        assertTrue(10 > calculator.add(4,5), "10 is greater than 4+5");
        assertFalse(10 > calculator.add(5,6), "10 is less than 5+6 ");
//        assertNull();
//        assertNotNull();
    }

    @Test
    @DisplayName("Test division")
    @Order(3)
    void testDivistion() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> calculator.div(10, 0));
        assertEquals("b mustn't be 0", exception.getMessage());
//        assertThrows(RuntimeException.class, () -> calculator.div(10, 0));
    }

    @AfterEach
    void cleanup() {
        System.out.println("Runs after each test");
    }

    @AfterAll
    static void cleanupAfterClass() {
        System.out.println("Runs once after all tests");
    }
}