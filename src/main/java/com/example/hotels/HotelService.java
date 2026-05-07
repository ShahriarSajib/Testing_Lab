package com.example.hotels;

import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private List<Reservation> reservations =
            new ArrayList<>();

    public void makeReservation(
            Reservation reservation
    ) {

        Room room = reservation.getRoom();

        room.bookRoom();

        reservations.add(reservation);
    }

    public void cancelReservation(
            String reservationId
    ) {

        Reservation reservation =
                findReservation(reservationId);

        if(reservation == null){
            throw new IllegalStateException(
                    "Reservation not found"
            );
        }

        reservation.updateStatus("CANCELLED");

        reservation.getRoom().releaseRoom();
    }

    public Reservation findReservation(
            String reservationId
    ) {

        return reservations.stream()
                .filter(
                        r ->
                                r.getReservationId()
                                        .equals(reservationId)
                )
                .findFirst()
                .orElse(null);
    }

    public int totalReservations(){

        return reservations.size();
    }

    public long occupiedRooms(){

        return reservations.stream()
                .filter(
                        r ->
                                r.getRoom()
                                        .isAvailable() == false
                        &&
                                !r.getStatus()
                                .equals("CANCELLED")
                )
                .count();
    }

    public double totalRevenue(){

        return reservations.stream()
                .filter(
                        r ->
                                !r.getStatus()
                                .equals("CANCELLED")
                )
                .mapToDouble(
                        Reservation::calculateBill
                )
                .sum();
    }
}