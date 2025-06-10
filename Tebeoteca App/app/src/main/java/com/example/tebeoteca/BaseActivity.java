package com.example.tebeoteca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tebeoteca.cliente.DialogNuevaSeccionActivity;
import com.example.tebeoteca.cliente.PerfilClienteActivity;
import com.example.tebeoteca.cliente.comic.ComicsActivity;
import com.example.tebeoteca.cliente.evento.EventosActivity;
import com.example.tebeoteca.cliente.ruta.RutasActivity;
import com.example.tebeoteca.cliente.wiki.EntradasWikiActivity;
import com.example.tebeoteca.general.BusquedaActivity;
import com.example.tebeoteca.lector.ColeccionActivity;
import com.example.tebeoteca.lector.PerfilLectorActivity;
import com.example.tebeoteca.lector.SeccionesLectorActivity;
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
        Log.d("BaseActivity","onCreate()");

        contentContainer = findViewById(R.id.content_container);

        btnAtras = findViewById(R.id.btn_atras);
        /*btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BaseActivity","btnAtras()");
                finish();
            }
        });*/

    }

    public void volverAtras(View view) {
        Log.d("BaseActivity","volverAtras()");
        finish();
    }

    protected void setupMenus(@IdRes int activeMenuItemId, String perfil) {
        Log.d("BaseActivity","setupMenus() " + perfil);

        overlay = findViewById(R.id.overlay);

        //Top menu:
        btnConfig = findViewById(R.id.btn_configuracion);
        btnNotificaciones = findViewById(R.id.btn_notificaciones);

        btnAnadir = findViewById(R.id.btn_anadir);

        //Bottom menu:
        /*btnRutas = findViewById(R.id.btn_ruta);
        btnWiki = findViewById(R.id.btn_wiki);
        btnEventos = findViewById(R.id.btn_eventos);*/

        floatingSubmenu = findViewById(R.id.submenu_layout);
        bottomMenu = findViewById(R.id.bottom_nav);

        if(perfil.equals("cliente")) {
            Log.d("BaseActivity","es clienteeee");
            floatingSubmenu.setVisibility(View.GONE);
            overlay.setOnClickListener(v -> {
                overlay.setVisibility(View.GONE);
                hideSubMenu(floatingSubmenu);
            });

            bottomMenu.setSelectedItemId(activeMenuItemId);
            bottomMenu.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_inicio && !(this instanceof PerfilClienteActivity)) {
                    Log.d("BaseActivity","1");
                    startActivity(new Intent(this, PerfilClienteActivity.class));
                } else if (id == R.id.nav_buscar && !(this instanceof BusquedaActivity)) {
                    Log.d("BaseActivity","2");
                    startActivity(new Intent(this, BusquedaActivity.class));
                } else if (id == R.id.nav_comics && !(this instanceof ComicsActivity)) {
                    Log.d("BaseActivity","3");
                    startActivity(new Intent(this, ComicsActivity.class));
                }
                if(item.getItemId() == R.id.nav_menu) {
                    mostrarMenuFlotante(item);
                    return false;
                    /*Log.d("BaseActivity","4");
                    if(isSubMenuVisible && !(overlay.getVisibility() == View.GONE)) {
                        Log.d("BaseActivity","5");
                        overlay.animate()
                                .alpha(0f)
                                .setDuration(300)
                                .withEndAction(() -> overlay.setVisibility(View.GONE))
                                .start();
                        hideSubMenu(floatingSubmenu);
                    } else {
                        Log.d("BaseActivity","6");
                        overlay.setAlpha(0f);
                        overlay.setVisibility(View.VISIBLE);
                        overlay.animate()
                                .alpha(0.5f)
                                .setDuration(300)
                                .start();
                        showSubMenu(floatingSubmenu);
                    }
                    isSubMenuVisible = !isSubMenuVisible;
                    return true;*/
                } else {
                    Log.d("BaseActivity","7");
                    hideSubMenu(floatingSubmenu);
                    isSubMenuVisible = false;
                    return true;
                }
            });

            btnAnadir.setOnClickListener(v -> startDialogAnadirSeccionActivity());

            /*btnRutas.setOnClickListener(v -> startRutasActivity());

            btnWiki.setOnClickListener(v -> startEntradasWikiActivity());

            btnEventos.setOnClickListener(v -> startEventosActivity());*/

        } else if(perfil.equals("lector")) {
            floatingSubmenu.setVisibility(View.GONE);
            overlay.setVisibility(View.GONE);
            btnAnadir.setVisibility(View.GONE);
            bottomMenu.setSelectedItemId(activeMenuItemId);
            bottomMenu.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_inicio && !(this instanceof PerfilLectorActivity)) {
                    startActivity(new Intent(this, PerfilLectorActivity.class));
                } else if (id == R.id.nav_buscar && !(this instanceof BusquedaActivity)) {
                    startActivity(new Intent(this, BusquedaActivity.class));
                } else if (id == R.id.nav_comics) {
                    startActivity(new Intent(this, ColeccionActivity.class));
                } else if(id == R.id.nav_menu) {
                    startActivity(new Intent(this, SeccionesLectorActivity.class));
                }
                return true;
            });
        }

        //funcionalidades botones
        btnConfig.setOnClickListener(v -> {
            if (!(this instanceof ConfiguracionActivity)) {
                startConfiguracionActivity();
            }
        });

        btnNotificaciones.setOnClickListener(v -> {
            /*if (!(this instanceof NotificacionesActivity)) {
                startNotificacionesActivity();
            }*/
            Intent intent = new Intent(this, NotificacionesActivity.class);
            startActivity(intent);
        });

    }

    public void mostrarMenuFlotante(MenuItem item) {
            Log.d("BaseActivity","es menú flotante");
            if(isSubMenuVisible && !(overlay.getVisibility() == View.GONE)) {
                Log.d("BaseActivity","5");
                overlay.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction(() -> overlay.setVisibility(View.GONE))
                        .start();
                hideSubMenu(floatingSubmenu);
            } else {
                Log.d("BaseActivity","6");
                overlay.setAlpha(0f);
                overlay.setVisibility(View.VISIBLE);
                overlay.animate()
                        .alpha(0.5f)
                        .setDuration(300)
                        .start();
                showSubMenu(floatingSubmenu);
            }
            isSubMenuVisible = !isSubMenuVisible;
    }

    protected void setCustomContent(int layoutResId) {
        LayoutInflater.from(this).inflate(layoutResId, contentContainer, true);
    }

    /*public void startEspecialesActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }*/

    public void startConfiguracionActivity() {
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void startDialogAnadirSeccionActivity() {
        Intent intent = new Intent(this, DialogNuevaSeccionActivity.class);
        startActivity(intent);
    }

    public void startNotificacionesActivity() {
        Intent intent = new Intent(this, NotificacionesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void startRutasActivity(View view) {
        Log.d("BaseActivity","startRutasAct()");
        Intent intent = new Intent(this, RutasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public void startEventosActivity(View view) {
        Log.d("BaseActivity","startEventosAct()");
        Intent intent = new Intent(this, EventosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void startEntradasWikiActivity(View view) {
        Log.d("BaseActivity","startEntradasWikiAct()");
        Intent intent = new Intent(this, EntradasWikiActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void showSubMenu(LinearLayout submenu) {
        Log.d("BaseActivity","showSubMenu()");
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
        Log.d("BaseActivity","hideSubMenu()");
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
