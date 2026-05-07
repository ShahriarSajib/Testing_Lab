package com.example.hotels;

public class Guest {

    private String guestId;
    private String name;

    public Guest(
            String guestId,
            String name
    ) {

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(
                    "Guest name cannot be empty"
            );
        }

        this.guestId = guestId;
        this.name = name;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }
}
