package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Importe aqui seus fragments:
import com.example.loginapp.fragments.HomeFragment;
import com.example.loginapp.fragments.PrimeirosSocorrosFragment;
import com.example.loginapp.fragments.PerfilFragment;

public class Activity_Navegacao extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // apontar para o arquivo de layout, e não para o menu!
        setContentView(R.layout.activity_navegacao);

        bottomNav = findViewById(R.id.bottom_nav);

        // Ao abrir vai me carregar o fragment “PrimeirosSocorros”
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PrimeirosSocorrosFragment())
                    .commit();
            // Marca como selecionado o item "Primeiros Socorros"
            bottomNav.setSelectedItemId(R.id.nav_primeiros_socorros);
        }

        // Listener de clique na BottomNav
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            }
             else if (itemId == R.id.nav_primeiros_socorros) {
                selectedFragment = new PrimeirosSocorrosFragment();
            }
             else if (itemId == R.id.nav_perfil) {
                selectedFragment = new PerfilFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }
}
