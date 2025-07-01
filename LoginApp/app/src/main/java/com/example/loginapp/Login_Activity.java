package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.network.ApiClient;
import com.example.loginapp.network.ApiService;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button btn_login, btn_register;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //  Instancia o ApiService via Retrofit
        api = ApiClient.getRetrofit().create(ApiService.class);

        //  Liga os views
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        btn_login     = findViewById(R.id.login_btn);
        btn_register  = findViewById(R.id.register_btn);

        //  Listener de LOGIN
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        //  Listener de REGISTO
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Register_Activity.class));
            }
        });
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validações
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preenche todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // monta JSON com credenciais
        JsonObject body = new JsonObject();
        body.addProperty("username", username);
        body.addProperty("password", password);

        // Chama o endpoint login.php (em vez de getUsers)
        api.loginUser(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> resp) {
                if (resp.isSuccessful() && resp.body() != null && resp.body().get("success").getAsBoolean()) {

                    int userId = resp.body().get("user_id").getAsInt();
                    getSharedPreferences("my_app_preferences",MODE_PRIVATE)
                            .edit()
                                    .putInt("user_id",userId)
                                            .apply();
                    startActivity(new Intent(Login_Activity.this, Activity_Navegacao.class));
                } else {
                    Toast.makeText(Login_Activity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Login_Activity.this, "Falha de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

