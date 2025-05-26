package com.example.tebeoteca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tebeoteca.fragments.InicioFragment;
import com.example.tebeoteca.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    ImageButton btnConfig, btnNotificaciones, btnAnadir, btnEspeciales, btnRutas, btnWiki, btnNovedades;
    LinearLayout floatingSubmenu;
    BottomNavigationView bottomMenu;
    private Boolean isSubMenuVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        btnConfig = findViewById(R.id.btn_configuracion);
        btnNotificaciones = findViewById(R.id.btn_notificaciones);
        btnAnadir = findViewById(R.id.btn_anadir);
        //btnEspeciales = findViewById(R.id.btn_especiales);
        btnRutas = findViewById(R.id.btn_ruta);
        btnWiki = findViewById(R.id.btn_wiki);
        btnNovedades = findViewById(R.id.btn_novedades);

        floatingSubmenu = findViewById(R.id.submenu_layout);
        floatingSubmenu.setVisibility(View.GONE);

        bottomMenu = findViewById(R.id.bottom_nav);
        bottomMenu.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_menu) {
                if(isSubMenuVisible) {
                    //floatingSubmenu.setVisibility(View.GONE);
                    hideSubMenu(floatingSubmenu);
                } else {
                    //floatingSubmenu.setVisibility(View.VISIBLE);
                    showSubMenu(floatingSubmenu);
                }
                isSubMenuVisible = !isSubMenuVisible;
                return true;
            } else {
                // Lógica para los demás botones
                hideSubMenu(floatingSubmenu);
                isSubMenuVisible = false;
                return true;
            }

            // Lógica para los demás botones
            //floatingSubmenu.setVisibility(View.GONE);
            //isSubMenuVisible = false;
            //return true;
        });

        //btnConfig.setImageResource(R.drawable.ic_settings);

        /*//Animacion botones:
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.button_fade_in);
        btnConfig.startAnimation(anim);
        btnNotificaciones.startAnimation(anim);
        btnAnadir.startAnimation(anim);*/

        /*renderizado:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect blurEffect = RenderEffect.createBlurEffect(
                    5f, 5f, Shader.TileMode.CLAMP);  // Aqui para ajustar el nivel de desenfoque


            btnConfig.setRenderEffect(blurEffect);
            btnNotificaciones.setRenderEffect(blurEffect);
            btnAnadir.setRenderEffect(blurEffect);
        }*/

        //funcionalidades botones
        btnConfig.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a configuracion", Toast.LENGTH_SHORT).show();
        });

        btnNotificaciones.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a notificaciones", Toast.LENGTH_SHORT).show();
        });

        btnAnadir.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a añadir", Toast.LENGTH_SHORT).show();
        });

        /*btnEspeciales.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Especiales", Toast.LENGTH_SHORT).show();
        });*/

        btnRutas.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Rutas", Toast.LENGTH_SHORT).show();
        });

        btnWiki.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Wiki", Toast.LENGTH_SHORT).show();
        });

        btnNovedades.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Novedades", Toast.LENGTH_SHORT).show();
        });

        // Mostrar fragmento inicial
        cargarFragmento(new InicioFragment());
    }

    public void startEspecialesActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void cargarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showSubMenu(LinearLayout submenu) {
        submenu.setVisibility(View.VISIBLE);

        for (int i = 0; i < submenu.getChildCount(); i++) {
            View fab = submenu.getChildAt(i);
            fab.setAlpha(0f);
            fab.setTranslationY(100f); // empieza más abajo

            fab.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setStartDelay(i * 50) // uno detrás de otro
                    .setDuration(200)
                    .start();
        }
    }

    private void hideSubMenu(LinearLayout submenu) {
        for (int i = 0; i < submenu.getChildCount(); i++) {
            View fab = submenu.getChildAt(i);

            int finalI = i;
            fab.animate()
                    .alpha(0f)
                    .translationY(100f)
                    .setStartDelay(i * 30)
                    .setDuration(150)
                    .withEndAction(() -> {
                        if (finalI == submenu.getChildCount() - 1) {
                            submenu.setVisibility(View.GONE);
                        }
                    })
                    .start();
        }
    }
}
