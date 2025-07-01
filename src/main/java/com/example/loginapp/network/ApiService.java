package com.example.loginapp.network;

import com.example.loginapp.EmergencyRequest;
import com.example.loginapp.model.Emergency;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("Login.php")
    Call<JsonObject> loginUser(@Body JsonObject credentials);

    @POST("create_user.php")
    Call<JsonObject> createUser(@Body JsonObject userData);

    @POST("create_emergency.php")
    Call<JsonObject> sendEmergency(@Body EmergencyRequest emergencyData);

}
