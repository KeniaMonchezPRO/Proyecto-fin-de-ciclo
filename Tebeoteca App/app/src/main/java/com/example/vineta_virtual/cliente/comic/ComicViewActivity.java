package com.example.vineta_virtual.cliente.comic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vineta_virtual.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ComicViewActivity extends AppCompatActivity implements OnSwipeTouchListener.OnSwipeListener {

    private PdfRenderer renderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor fileDescriptor;
    private ZoomableImageView imageView;
    private int pageIndex = 0;
    private int pageCount = 0;
    OnSwipeTouchListener swipeTouchListener;
    boolean esPrevisualizacion;
    int maxPaginasPermitidas = Integer.MAX_VALUE;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_view);

        imageView = findViewById(R.id.pdfImageView);
        ImageButton btnNext = findViewById(R.id.btn_next);
        ImageButton btnPrev = findViewById(R.id.btn_prev);
        ImageButton btnCerrar = findViewById(R.id.btn_cerrar);

        esPrevisualizacion = getIntent().getBooleanExtra("preview", false);
        if (esPrevisualizacion) {
            maxPaginasPermitidas = 5;
        }

        swipeTouchListener = new OnSwipeTouchListener((Context) this, (OnSwipeTouchListener.OnSwipeListener) this);

        File pdfFile = copyPdfFromAssets();
        if (pdfFile == null) {
            Toast.makeText(this, "No se pudo cargar el PDF", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            renderer = new PdfRenderer(fileDescriptor);
            //pageCount = renderer.getPageCount();
            pageCount = Math.min(renderer.getPageCount(), maxPaginasPermitidas);
            showPage(pageIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnNext.setOnClickListener(v -> {
            if (pageIndex < pageCount - 1) {
                pageIndex++;
                showPage(pageIndex);
                Toast.makeText(this, "Página: " + (pageIndex + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (pageIndex > 0) {
                pageIndex--;
                showPage(pageIndex);
                Toast.makeText(this, "Página: " + (pageIndex + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fin del cómic", Toast.LENGTH_SHORT).show();
            }
        });

        btnCerrar.setOnClickListener(v -> finish());
    }
    @Override
    public void onSwipeLeft() {
        if (pageIndex < pageCount - 1) {
            pageIndex++;
            showPage(pageIndex);
            Toast.makeText(this, "Página: " + (pageIndex + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fin del cómic", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSwipeRight() {
        if (pageIndex > 0) {
            pageIndex--;
            showPage(pageIndex);
            Toast.makeText(this, "Página: " + (pageIndex + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
        }
    }

    private void showPage(int index) {
        if (renderer == null || renderer.getPageCount() <= index) return;

        if (currentPage != null) currentPage.close();

        currentPage = renderer.openPage(index);

        Bitmap bitmap = Bitmap.createBitmap(
                currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        imageView.setImageBitmap(bitmap);
        imageView.post(() -> imageView.resetZoom());
        imageView.setOnTouchListener(swipeTouchListener);
    }

    @Override
    protected void onDestroy() {
        try {
            if (currentPage != null) currentPage.close();
            if (renderer != null) renderer.close();
            if (fileDescriptor != null) fileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private File copyPdfFromAssets() {
        File file = new File(getCacheDir(), "comic.pdf");

        try (InputStream in = getAssets().open("comic.pdf");
             OutputStream out = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

