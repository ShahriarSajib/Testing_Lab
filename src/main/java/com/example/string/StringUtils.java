package com.example.string;

public class StringUtils {
    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }

    public static boolean containsSubstring(String mainString, String subString) {
        if (mainString == null || subString == null) return false;
        return mainString.contains(subString);
    }
}