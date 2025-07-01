package com.example.loginapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class gps_teste extends AppCompatActivity {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private TextView tv_lat,tv_lon,tv_altitude,tv_precisao,tv_velocidade,tv_localizacao, tv_sensor,tv_updates;
    private Switch sw_locationsupdates,sw_gps;


    //variavel para saber se estamos realmente a obter a localizaçao ou nao
    boolean updateOn = false;

    //
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    // Google API para a localização de serviços / telemovéis,Uso essencial para o funcionamento correto disto tudo
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_teste);

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_precisao = findViewById(R.id.tv_precisao);
        tv_velocidade = findViewById(R.id.tv_velocidade);
        tv_localizacao = findViewById(R.id.tv_localizacao);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);


        //definimos o intervalo de tempo e o request para obter a localizaçao do utilizador
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY,30000)
                .setMinUpdateIntervalMillis(5000)
                .build();

        //
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //guardamos a localização
                updateUIValues(locationResult.getLastLocation());
            }
        };
        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()){
                    //com maior precisão-usa o gps
                    locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,30000)
                            .setMinUpdateIntervalMillis(5000)
                            .build();
                    tv_sensor.setText("A usar o GPS");
                }else {
                    //com menor precisao usa as torres e o wifi
                    locationRequest = new LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY,30000)
                            .setMinUpdateIntervalMillis(5000)
                            .build();
                    tv_sensor.setText("A usar torres + WIFI");
                }
            }
        });

        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
            @Override
            public void onClick(View v) {
                if (sw_locationsupdates.isChecked()){
                    //localização ligada
                    startLocationUpdates();
                }else{
                    //localização desligada
                    stopLocationUpdates();
                }
            }
        });

        updateGPS();
    }


    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void startLocationUpdates() {
        tv_updates.setText("Localização sincronizada");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();

    }
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void stopLocationUpdates() {

        tv_updates.setText("Localização NÃO sincronizada");
        tv_lat.setText("Not Tracking");
        tv_lon.setText("Not Tracking");
        tv_velocidade.setText("Not Tracking");
        tv_altitude.setText("Not Tracking");
        tv_precisao.setText("Not Tracking");
        tv_localizacao.setText("Not Tracking");
        tv_sensor.setText("Not Tracking");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app requires permission to be granted in otder to work properly ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void updateGPS(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(gps_teste.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,cancellationTokenSource.getToken())
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //ficamos com permisssao para obter a localizaçao do utilizador
                    updateUIValues(location);
                }
            });
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }

        };
    }

    // vamos dar valores aos parametros de localizaçao do utilizador
    private void updateUIValues(Location location) {
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_precisao.setText(String.valueOf(location.getAccuracy()));

        if (location.hasAltitude()){
            tv_altitude.setText(String.valueOf(location.getAltitude()));
        }else{
            tv_altitude.setText("Not Avaiable");
        }

        if (location.hasSpeed()){
            tv_velocidade.setText(String.valueOf(location.getSpeed()));
        }else{
            tv_velocidade.setText("Not Avaiable");
        }

        Geocoder geocoder = new Geocoder(gps_teste.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            tv_localizacao.setText(addresses.get(0).getAddressLine(0));
        }catch (Exception e){
            tv_localizacao.setText("Unable to get street address");

        }
    }

}
