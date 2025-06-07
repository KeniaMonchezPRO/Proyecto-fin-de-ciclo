package com.example.tebeoteca.cliente.ruta;

import android.os.Bundle;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class NuevaRutaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_ruta);
        setupMenus(R.id.nav_menu, "cliente");
    }
}
