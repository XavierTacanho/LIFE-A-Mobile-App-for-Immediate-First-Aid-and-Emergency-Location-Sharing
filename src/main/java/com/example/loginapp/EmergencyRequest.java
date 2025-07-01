package com.example.loginapp;

public class EmergencyRequest {
    private int user_id;
    private String description;
    private String status;
    private double latitude;
    private double longitude;
    private String street_address;

    public EmergencyRequest(int user_id, String description, String status, double latitude, double longitude, String street_address) {
        this.user_id = user_id;
        this.description = description;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street_address = street_address;
    }


}
