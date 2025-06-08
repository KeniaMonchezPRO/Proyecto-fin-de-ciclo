package com.example.tebeoteca.cliente.comic;

import static android.content.Intent.getIntent;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tebeoteca.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ComicViewActivity2 extends AppCompatActivity {

    private static final String TAG = "PdfViewActivity"; // Para logs
    private ViewPager2 pdfViewPager;
    private PaginaAdapter pdfPageAdapter;
    private List<Bitmap> pdfPages;

    private PdfRenderer pdfRenderer;
    private ParcelFileDescriptor parcelFileDescriptor;
    private File comicPdfFile; // El archivo PDF real (copiado de assets o descargado)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_view);
/*
        pdfViewPager = findViewById(R.id.pdfViewPager);
        pdfPages = new ArrayList<>();

        // Paso A: Obtener la ruta del PDF.
        // Aquí debes obtener el nombre o la URI del PDF que quieres abrir.
        // Ejemplo: Si pasaste el nombre del PDF desde otra Activity via Intent
        String pdfFileName = getIntent().getStringExtra("PDF_FILE_NAME"); // Ejemplo: "mi_comic.pdf"
        if (pdfFileName == null) {
            Toast.makeText(this, "No se especificó ningún PDF.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Paso B: Copiar el PDF desde assets al almacenamiento interno si es necesario,
        // o usar directamente el archivo si ya está en una ruta accesible.
        // Asumo que el PDF viene de assets, como discutimos.
        comicPdfFile = new File(getFilesDir(), "comic.pdf");
        if (!comicPdfFile.exists()) {
            try {
                // Copiar el archivo de assets a la carpeta files/ de la app
                InputStream inputStream = getAssets().open(pdfFileName);
                FileOutputStream outputStream = new FileOutputStream(comicPdfFile);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                inputStream.close();
                outputStream.close();
                Log.d(TAG, "PDF copiado de assets a: " + comicPdfFile.getAbsolutePath());
            } catch (IOException e) {
                Log.e(TAG, "Error al copiar PDF de assets: " + e.getMessage());
                Toast.makeText(this, "Error al preparar el PDF.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }


        // Paso C: Cargar el PDF con PdfRenderer y generar los Bitmaps
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(comicPdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);

            int pageCount = pdfRenderer.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                PdfRenderer.Page page = pdfRenderer.openPage(i);
                // Crea un Bitmap con el tamaño original de la página del PDF
                Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                // Renderiza la página en el Bitmap
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                pdfPages.add(bitmap);
                page.close(); // ¡Importante cerrar la página!
            }

            pdfPageAdapter = new PaginaAdapter(pdfPages);
            pdfViewPager.setAdapter(pdfPageAdapter);

        } catch (IOException e) {
            Log.e(TAG, "Error al abrir o renderizar PDF: " + e.getMessage());
            Toast.makeText(this, "No se pudo cargar el cómic.", Toast.LENGTH_SHORT).show();
            finish();
        }*/
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra PdfRenderer y ParcelFileDescriptor cuando la Activity se destruye
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Libera los Bitmaps de las páginas para evitar fugas de memoria
        if (pdfPages != null) {
            for (Bitmap bitmap : pdfPages) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            pdfPages.clear();
        }
    }*/
}
