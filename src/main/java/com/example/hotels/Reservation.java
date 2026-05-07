package com.example.hotels;

public class Reservation {

    private String reservationId;

    private Guest guest;

    private Room room;

    private int nights;

    private String status;

    public Reservation(
            String reservationId,
            Guest guest,
            Room room,
            int nights
    ) {

        if(nights <= 0){
            throw new IllegalArgumentException(
                    "Invalid number of nights"
            );
        }

        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.status = "BOOKED";
    }

    public String getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String status){

        this.status = status;
    }

    public double calculateBill(){

        return room.getPricePerNight() * nights;
    }
}