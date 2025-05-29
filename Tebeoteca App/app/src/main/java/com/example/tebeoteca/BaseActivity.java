package com.example.tebeoteca;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tebeoteca.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    protected FrameLayout contentContainer;

    ImageButton btnConfig, btnNotificaciones, btnAnadir, btnEspeciales, btnRutas, btnWiki, btnNovedades;
    LinearLayout floatingSubmenu;
    BottomNavigationView bottomMenu;
    private Boolean isSubMenuVisible = false;
    View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2); // Layout con top y bottom menu

        contentContainer = findViewById(R.id.content_container);
        setupMenus();
    }

    private void setupMenus() {
        overlay = findViewById(R.id.overlay);

        //Top menu:
        btnConfig = findViewById(R.id.btn_configuracion);
        btnNotificaciones = findViewById(R.id.btn_notificaciones);

        btnAnadir = findViewById(R.id.btn_anadir);

        //Bottom menu:
        btnRutas = findViewById(R.id.btn_ruta);
        btnWiki = findViewById(R.id.btn_wiki);
        btnNovedades = findViewById(R.id.btn_novedades);

        floatingSubmenu = findViewById(R.id.submenu_layout);
        floatingSubmenu.setVisibility(View.GONE);

        overlay.setOnClickListener(v -> {
            overlay.setVisibility(View.GONE);
            hideSubMenu(floatingSubmenu);
        });

        bottomMenu = findViewById(R.id.bottom_nav);
        bottomMenu.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_menu) {
                if(isSubMenuVisible && !(overlay.getVisibility() == View.GONE)) {
                    overlay.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .withEndAction(() -> overlay.setVisibility(View.GONE))
                            .start();
                    hideSubMenu(floatingSubmenu);
                } else {
                    overlay.setAlpha(0f);
                    overlay.setVisibility(View.VISIBLE);
                    overlay.animate()
                            .alpha(0.5f) // o 0.6f según qué tan oscuro lo quieres
                            .setDuration(300)
                            .start();
                    showSubMenu(floatingSubmenu);
                }
                isSubMenuVisible = !isSubMenuVisible;
                return true;
            } else {
                hideSubMenu(floatingSubmenu);
                isSubMenuVisible = false;
                return true;
            }
        });

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

        btnRutas.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Rutas", Toast.LENGTH_SHORT).show();
        });

        btnWiki.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Wiki", Toast.LENGTH_SHORT).show();
        });

        btnNovedades.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Novedades", Toast.LENGTH_SHORT).show();
        });
    }

    protected void setCustomContent(int layoutResId) {
        LayoutInflater.from(this).inflate(layoutResId, contentContainer, true);
    }

    public void startEspecialesActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
