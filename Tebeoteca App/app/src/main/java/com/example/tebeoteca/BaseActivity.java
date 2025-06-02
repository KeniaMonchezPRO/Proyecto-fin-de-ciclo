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

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tebeoteca.cliente.BuscarActivity;
import com.example.tebeoteca.cliente.DialogAnadirSeccionActivity;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.cliente.comic.ComicsActivity;
import com.example.tebeoteca.login.LoginActivity;
import com.example.tebeoteca.registro.RegistroLectorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    protected FrameLayout contentContainer;

    ImageButton btnConfig, btnNotificaciones, btnAnadir, btnRutas, btnWiki, btnEventos, btnAtras;;
    LinearLayout floatingSubmenu;
    BottomNavigationView bottomMenu;
    private Boolean isSubMenuVisible = false;
    View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        contentContainer = findViewById(R.id.content_container);
        //setupMenus();

        btnAtras = findViewById(R.id.btn_atras);
        btnAtras.setOnClickListener(new View.OnClickListener() { // Establece un OnClickListener
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void volverAtras(View view) {
        finish(); // ¡Esto cierra la Activity actual y regresa a la anterior!
    }

    protected void setupMenus(@IdRes int activeMenuItemId) {
        overlay = findViewById(R.id.overlay);

        //Top menu:
        btnConfig = findViewById(R.id.btn_configuracion);
        btnNotificaciones = findViewById(R.id.btn_notificaciones);

        btnAnadir = findViewById(R.id.btn_anadir);

        //Bottom menu:
        btnRutas = findViewById(R.id.btn_ruta);
        btnWiki = findViewById(R.id.btn_wiki);
        btnEventos = findViewById(R.id.btn_eventos);

        floatingSubmenu = findViewById(R.id.submenu_layout);
        floatingSubmenu.setVisibility(View.GONE);

        overlay.setOnClickListener(v -> {
            overlay.setVisibility(View.GONE);
            hideSubMenu(floatingSubmenu);
        });

        bottomMenu = findViewById(R.id.bottom_nav);
        bottomMenu.setSelectedItemId(activeMenuItemId);
        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio && !(this instanceof PerfilClienteActivity)) {
                startActivity(new Intent(this, PerfilClienteActivity.class));
            } else if (id == R.id.nav_buscar && !(this instanceof BuscarActivity)) {
                startActivity(new Intent(this, BuscarActivity.class));
            } else if (id == R.id.nav_comics && !(this instanceof ComicsActivity)) {
                startActivity(new Intent(this, ComicsActivity.class));
            }
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
                            .alpha(0.5f)
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
            if (!(this instanceof ConfiguracionActivity)) {
                startConfiguracionActivity();
            }
        });

        btnNotificaciones.setOnClickListener(v -> {
            if (!(this instanceof NotificacionesActivity)) {
                startNotificacionesActivity();
            }
        });

        btnAnadir.setOnClickListener(v -> startDialogAnadirSeccionActivity());

        btnRutas.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Rutas", Toast.LENGTH_SHORT).show();
        });

        btnWiki.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Wiki", Toast.LENGTH_SHORT).show();
        });

        btnEventos.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Eventos", Toast.LENGTH_SHORT).show();
        });
    }

    protected void setCustomContent(int layoutResId) {
        LayoutInflater.from(this).inflate(layoutResId, contentContainer, true);
    }

    public void startEspecialesActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void startConfiguracionActivity() {
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void startDialogAnadirSeccionActivity() {
        Intent intent = new Intent(this, DialogAnadirSeccionActivity.class);
        startActivity(intent);
    }

    public void startNotificacionesActivity() {
        Intent intent = new Intent(this, NotificacionesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
