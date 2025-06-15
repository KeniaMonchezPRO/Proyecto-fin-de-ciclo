package com.example.vineta_virtual.cliente.ruta;

import android.os.Bundle;

import com.example.vineta_virtual.BaseActivity;
import com.example.vineta_virtual.R;

public class NuevaRutaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_ruta);
        setupMenus(R.id.nav_menu, "cliente");
    }
}
