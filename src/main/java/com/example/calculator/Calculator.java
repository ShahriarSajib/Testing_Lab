package com.example.calculator;

public class Calculator {
    public int multiply(int a, int b) {
        return a * b;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }

    public double div(int a, int b) {
        double ans;
        try {
            ans = a/b;
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("b mustn't be 0");
        }
        return ans;
    }
}
