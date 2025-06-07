package com.example.tebeoteca.cliente.wiki;

import android.os.Bundle;

import com.example.tebeoteca.BaseActivity;
import com.example.tebeoteca.R;

public class NuevaEntradaWikiActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContent(R.layout.activity_anadir_entrada_wiki);
        setupMenus(R.id.nav_menu, "cliente");
    }
}
