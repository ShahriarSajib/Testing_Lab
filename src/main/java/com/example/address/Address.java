package com.example.address;

public class Address {
    public String name;
    public String country;
    public String division;

    public Address(String name, String division) {
        this.name = name;
        this.country = "Bangladesh";
        this.division = division;
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getDivision() {
        return this.division;
    }
}