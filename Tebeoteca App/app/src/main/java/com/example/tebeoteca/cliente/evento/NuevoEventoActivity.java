package com.example.tebeoteca.cliente.evento;

import android.os.Bundle;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class NuevoEventoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_evento);
        setupMenus(R.id.nav_menu, "cliente");
    }
}
