package com.example.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
class StringUtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {"racecar", "madam", "level"})
    void testIsPalindrome(String word) {
        System.out.println(word);
        assertTrue(StringUtils.isPalindrome(word), word + " not palindrom");
    }

    @ParameterizedTest
    @CsvSource({
            "racecar, true",
            "hello, false",
            "madam, true",
            "world, false"
    })
    void testIsPalindromeWithExpectedResult(String word, boolean expectedResult) {
        assertEquals(expectedResult, StringUtils.isPalindrome(word), "word: " + word + " expectedResult: " + expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "hello world, hello, true",
            "unit testing, test, true",
            "JUnit 5, JUnit, true",
            "parameterized tests, unit, false",
            "abc, xyz, false"
    })
    void testContainsSubstring(String mainString, String subString, boolean expectedResult) {
        assertEquals(expectedResult, StringUtils.containsSubstring(mainString, subString));
    }
}