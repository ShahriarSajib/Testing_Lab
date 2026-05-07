package com.example.hotels;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Hotel Reservation System Test")
public class HotelServiceTest {

    HotelService service;

    Guest guest;

    Room room;

    Reservation reservation;

    @BeforeEach
    void setup(){

        service = new HotelService();

        guest =
                new Guest(
                        "G101",
                        "Sajib"
                );

        room =
                new Room(
                        101,
                        "Deluxe",
                        5000
                );

        reservation =
                new Reservation(
                        "R101",
                        guest,
                        room,
                        3
                );
    }

    // TEST 1
    @Test
    @Order(1)
    @DisplayName("Make Reservation Successfully")
    void testMakeReservation(){

        service.makeReservation(reservation);

        assertEquals(
                1,
                service.totalReservations()
        );

        assertFalse(room.isAvailable());
    }

    // TEST 2
    @Test
    @DisplayName("Prevent Double Room Booking")
    void testDoubleBooking(){

        service.makeReservation(reservation);

        Guest secondGuest =
                new Guest(
                        "G102",
                        "Nirob"
                );

        Reservation secondReservation =
                new Reservation(
                        "R102",
                        secondGuest,
                        room,
                        2
                );

        Exception exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> {

                            service.makeReservation(
                                    secondReservation
                            );

                        }
                );

        assertEquals(
                "Room already booked",
                exception.getMessage()
        );
    }

    // TEST 3
    @Test
    @DisplayName("Find Existing Reservation")
    void testFindReservation(){

        service.makeReservation(reservation);

        Reservation found =
                service.findReservation("R101");

        assertNotNull(found);

        assertEquals(
                "Sajib",
                found.getGuest().getName()
        );
    }

    // TEST 4
    @Test
    @DisplayName("Find Non Existing Reservation")
    void testFindNonExistingReservation(){

        Reservation found =
                service.findReservation("R999");

        assertNull(found);
    }

    // TEST 5
    @Test
    @DisplayName("Cancel Reservation Successfully")
    void testCancelReservation(){

        service.makeReservation(reservation);

        service.cancelReservation("R101");

        assertEquals(
                "CANCELLED",
                reservation.getStatus()
        );

        assertTrue(room.isAvailable());
    }

    // TEST 6
    @Test
    @DisplayName("Cancel Non Existing Reservation")
    void testCancelNonExistingReservation(){

        Exception exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> {

                            service.cancelReservation(
                                    "R500"
                            );

                        }
                );

        assertEquals(
                "Reservation not found",
                exception.getMessage()
        );
    }

    // TEST 7
    @Test
    @DisplayName("Calculate Reservation Bill")
    void testReservationBill(){

        assertEquals(
                15000,
                reservation.calculateBill()
        );
    }

    // TEST 8
    @Test
    @DisplayName("Hotel Revenue Calculation")
    void testHotelRevenue(){

        service.makeReservation(reservation);

        Guest secondGuest =
                new Guest(
                        "G200",
                        "Munni"
                );

        Room secondRoom =
                new Room(
                        102,
                        "Suite",
                        7000
                );

        Reservation secondReservation =
                new Reservation(
                        "R200",
                        secondGuest,
                        secondRoom,
                        2
                );

        service.makeReservation(
                secondReservation
        );

        assertEquals(
                29000,
                service.totalRevenue()
        );
    }

    // TEST 9
    @Test
    @DisplayName("Occupied Room Count")
    void testOccupiedRooms(){

        service.makeReservation(reservation);

        assertEquals(
                1,
                service.occupiedRooms()
        );
    }

    // TEST 10
    @Test
    @DisplayName("Cancelled Reservation Not Counted")
    void testCancelledReservation(){

        service.makeReservation(reservation);

        service.cancelReservation("R101");

        assertEquals(
                0,
                service.totalRevenue()
        );

        assertEquals(
                0,
                service.occupiedRooms()
        );
    }

    // TEST 11
    @Test
    @DisplayName("Invalid Room Price")
    void testInvalidRoomPrice(){

        Exception exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {

                            new Room(
                                    500,
                                    "VIP",
                                    -1000
                            );

                        }
                );

        assertEquals(
                "Invalid room price",
                exception.getMessage()
        );
    }

    // TEST 12
    @Test
    @DisplayName("Invalid Night Count")
    void testInvalidNightCount(){

        Exception exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {

                            new Reservation(
                                    "R999",
                                    guest,
                                    room,
                                    0
                            );

                        }
                );

        assertEquals(
                "Invalid number of nights",
                exception.getMessage()
        );
    }

    // TEST 13
    @Test
    @DisplayName("Invalid Guest Name")
    void testInvalidGuestName(){

        Exception exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {

                            new Guest(
                                    "G999",
                                    ""
                            );

                        }
                );

        assertEquals(
                "Guest name cannot be empty",
                exception.getMessage()
        );
    }

    // TEST 14
    @Test
    @DisplayName("Multiple Reservations Different Rooms")
    void testMultipleReservations(){

        Room secondRoom =
                new Room(
                        102,
                        "Suite",
                        8000
                );

        Guest secondGuest =
                new Guest(
                        "G300",
                        "Nafiz"
                );

        Reservation secondReservation =
                new Reservation(
                        "R300",
                        secondGuest,
                        secondRoom,
                        2
                );

        service.makeReservation(reservation);

        service.makeReservation(secondReservation);

        assertEquals(
                2,
                service.totalReservations()
        );

        assertEquals(
                2,
                service.occupiedRooms()
        );
    }

    // TEST 15
    @Test
    @DisplayName("Room Becomes Available After Cancellation")
    void testRoomRelease(){

        service.makeReservation(reservation);

        assertFalse(room.isAvailable());

        service.cancelReservation("R101");

        assertTrue(room.isAvailable());
    }
}