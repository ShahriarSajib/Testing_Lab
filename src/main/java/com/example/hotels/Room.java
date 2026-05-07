package com.example.hotels;

public class Room {

    private int roomNumber;

    private String type;

    private double pricePerNight;

    private boolean available;

    public Room(
            int roomNumber,
            String type,
            double pricePerNight
    ) {

        if(pricePerNight <= 0){
            throw new IllegalArgumentException(
                    "Invalid room price"
            );
        }

        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void bookRoom() {

        if(!available){
            throw new IllegalStateException(
                    "Room already booked"
            );
        }

        available = false;
    }

    public void releaseRoom() {

        available = true;
    }
}
