package com.example.loginapp.model;

import com.google.gson.annotations.SerializedName;

public class Emergency {
    @SerializedName("emergency_id")
    private int emergencyId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("description")
    private String description;

    @SerializedName("status")
    private String status;

    @SerializedName("date")
    private String date;

    public Emergency() {}

    public int getEmergencyId() { return emergencyId; }
    public void setEmergencyId(int emergencyId) { this.emergencyId = emergencyId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
