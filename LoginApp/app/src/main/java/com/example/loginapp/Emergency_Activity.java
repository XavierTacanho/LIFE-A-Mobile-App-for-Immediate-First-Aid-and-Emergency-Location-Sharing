package com.example.loginapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.loginapp.network.ApiClient;
import com.example.loginapp.network.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Emergency_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private TextView tvStreet,tvStreetName;
    private MapView mapView;
    private Button btnSos;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocation;

    private ActivityResultLauncher<String[]> locationPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> {
                        Boolean fine = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                        if (Boolean.TRUE.equals(fine)) {
                            mapView.getMapAsync(this);
                        } else {
                            Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);

        tvStreet   = findViewById(R.id.tv_street);
        tvStreetName = findViewById(R.id.tv_street_name);
        mapView    = findViewById(R.id.map_view);
        btnSos     = findViewById(R.id.btn_sos);
        fusedLocation = LocationServices.getFusedLocationProviderClient(this);

        // Inicializa o MapView
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);

        btnSos.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("my_app_preferences",MODE_PRIVATE);
            int userId = prefs.getInt("user_id",-1);
            if(userId == -1){
                Toast.makeText(this, "Utilizador não Identificado!",Toast.LENGTH_SHORT).show();
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissões de localização em falta", Toast.LENGTH_SHORT).show();
                return;
            }

            fusedLocation.getLastLocation().addOnSuccessListener(loc -> {
                if (loc != null) {
                    Geocoder geocoder = new Geocoder(Emergency_Activity.this, Locale.getDefault());
                    String street = "Indefinido";
                    try {
                        List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                        if (!addresses.isEmpty()) {
                            street = addresses.get(0).getAddressLine(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    EmergencyRequest request = new EmergencyRequest(
                            userId,
                            "Emergência reportada via botão SOS",
                            "Pendente",
                            loc.getLatitude(),
                            loc.getLongitude(),
                            street
                    );

                    ApiService api = ApiClient.getRetrofit().create(ApiService.class);
                    api.sendEmergency(request).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(Emergency_Activity.this, "SOS enviado com sucesso!", Toast.LENGTH_SHORT).show();
                                System.out.println("Resposta PHP: " + response.body().toString());

                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:112"));
                                startActivity(callIntent);

                            } else {
                                Toast.makeText(Emergency_Activity.this, "Erro no envio do SOS (resposta mal formatada)", Toast.LENGTH_SHORT).show();
                                try {
                                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                                    System.err.println("Erro do servidor: " + errorBody);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(Emergency_Activity.this, "Erro ao enviar SOS", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                } else {
                    Toast.makeText(Emergency_Activity.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
                }
            });
        });


        // Pede permissão e só depois carrega o mapa
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapView.getMapAsync(this);
        } else {
            locationPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.googleMap = map;
        map.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            fusedLocation.getLastLocation()
                    .addOnSuccessListener(loc -> {
                        if (loc != null) {
                            LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
                            map.addMarker(new MarkerOptions().position(pos).title("Você está aqui"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f));
                            tvStreet.setText(String.format(
                                    Locale.getDefault(),
                                    "Lat: %.5f    Lon: %.5f",
                                    loc.getLatitude(), loc.getLongitude()
                            ));
                            Geocoder geocoder = new Geocoder(Emergency_Activity.this);
                            try {
                                List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                                tvStreetName.setText(addresses.get(0).getAddressLine(0));
                            }catch (Exception e){
                                tvStreetName.setText("Unable to get street address");

                            }

                        } else {
                            tvStreet.setText("Localização indisponível");
                        }
                    })
                    .addOnFailureListener(e -> tvStreet.setText("Erro ao obter localização"));
        } else {
            // caso inesperado: perdemos permissão no runtime
            Toast.makeText(this, "Permissão de localização foi revogada", Toast.LENGTH_SHORT).show();
        }
    }


    // --- Lifecycle do MapView ---
    @Override protected void onResume()        { super.onResume(); mapView.onResume(); }
    @Override protected void onStart()         { super.onStart();  mapView.onStart();  }
    @Override protected void onStop()          { mapView.onStop(); super.onStop();   }
    @Override protected void onPause()         { mapView.onPause(); super.onPause(); }
    @Override protected void onDestroy()       { mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory()        { super.onLowMemory(); mapView.onLowMemory(); }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
}
