package com.example.loginapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.network.ApiClient;
import com.example.loginapp.network.ApiService;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    // campos do layout
    private EditText first_name_input,
            last_name_input,
            email_input,
            username_input,
            password_input,
            confirmar_pwd_input,
            etDob;
    private TextView camp_errors;
    private Button confirmation_btn;

    // Retrofit API
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        // 1) Instancia o ApiService
        api = ApiClient.getRetrofit().create(ApiService.class);

        // 2) Liga os views
        first_name_input    = findViewById(R.id.first_name_input);
        last_name_input     = findViewById(R.id.last_name_input);
        email_input         = findViewById(R.id.email_input);
        username_input      = findViewById(R.id.username_input);
        password_input      = findViewById(R.id.password_input);
        confirmar_pwd_input = findViewById(R.id.confirmar_pwd_input);
        etDob               = findViewById(R.id.dob_input);
        camp_errors         = findViewById(R.id.password_error);
        confirmation_btn    = findViewById(R.id.login_btn);

        // 3) DatePicker
        etDob.setFocusable(false);
        etDob.setOnClickListener(v -> showDatePicker());

        // 4) Quando carregar em “Confirmar!”
        confirmation_btn.setOnClickListener(v -> doRegister());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR),
                m = c.get(Calendar.MONTH),
                d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    etDob.setText(date);
                }, y, m, d);

        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    private void doRegister() {
        String first = first_name_input.getText().toString().trim();
        String last  = last_name_input.getText().toString().trim();
        String email = email_input.getText().toString().trim();
        String dob   = etDob.getText().toString().trim();
        String user  = username_input.getText().toString().trim();
        String pwd   = password_input.getText().toString();
        String conf  = confirmar_pwd_input.getText().toString();

        // validações básicas
        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || dob.isEmpty()
                || user.isEmpty() || pwd.isEmpty() || conf.isEmpty()) {
            Toast.makeText(this,
                    "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(conf)) {
            camp_errors.setText("Passwords não coincidem");
            return;
        }

        // validação de password forte
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!pwd.matches(regex)) {
            Toast.makeText(this,
                    "Password fraca: precisa de ≥8 carateres, 1 maiúscula, 1 número e 1 símbolo (!@$%*?&)",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // montar JSON
        JsonObject body = new JsonObject();
        body.addProperty("first_name",    first);
        body.addProperty("last_name",     last);
        body.addProperty("email",         email);
        body.addProperty("date_of_birth", dob);
        body.addProperty("username",      user);
        body.addProperty("password",      pwd);

        // chama endpoint create_user.php
        api.createUser(body).enqueue(new Callback<JsonObject>() {
            @Override public void onResponse(Call<JsonObject> call, Response<JsonObject> resp) {
                if (resp.isSuccessful() && resp.body() != null && resp.body().has("success")) {
                    boolean ok = resp.body().get("success").getAsBoolean();
                    if (ok) {
                        Toast.makeText(Register_Activity.this,
                                "Registo bem-sucedido!", Toast.LENGTH_SHORT).show();
                        // volta ao login
                        startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                        finish();
                        return;
                    }
                }
                Toast.makeText(Register_Activity.this,
                        "Falha no registo", Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Register_Activity.this,
                        "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
