package com.example.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void groupedAssertions() {
        Address address = new Address("Rahim","Sylhet");
        assertAll("address name",
                () -> assertEquals("Rahim", address.getName()),
                () -> assertEquals("Sylhet", address.getDivision()),
                () -> assertEquals("Bangladesh", address.getCountry())
        );
    }
}