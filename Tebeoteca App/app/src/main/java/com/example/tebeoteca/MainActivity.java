package com.example.tebeoteca;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tebeoteca.fragments.BuscarFragment;
import com.example.tebeoteca.fragments.InicioFragment;
import com.example.tebeoteca.fragments.PerfilFragment;
import com.example.tebeoteca.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        topAppBar = findViewById(R.id.topAppBar);

        //setSupportActionBar(topAppBar);

        // Mostrar fragmento inicial
        cargarFragmento(new InicioFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                cargarFragmento(new InicioFragment());
            } else if (itemId == R.id.nav_buscar) {
                cargarFragmento(new BuscarFragment());
            } else if (itemId == R.id.nav_perfil) {
                cargarFragmento(new PerfilFragment());
            }

            return true;
        });

        // Acciones del menú superior
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_notificaciones) {
                Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.action_configuracion) {
                Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    private void cargarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler().postDelayed(this::startLoginActivity, 1000);
    }*/



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
