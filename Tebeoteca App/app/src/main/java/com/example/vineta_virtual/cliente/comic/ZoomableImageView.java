package com.example.vineta_virtual.cliente.comic;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ZoomableImageView extends androidx.appcompat.widget.AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener {

    private Matrix matrix;
    private float[] matrixValues = new float[9];
    private float minScale = 1f;
    private float maxScale = 5f;

    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    private float lastX, lastY;
    private boolean isDragging = false;

    public ZoomableImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();

        scaleDetector = new ScaleGestureDetector(context, this);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float currentScale = getCurrentScale();
                float targetScale = (currentScale < maxScale) ? currentScale * 2f : minScale;

                matrix.postScale(targetScale / currentScale, targetScale / currentScale, e.getX(), e.getY());
                fixScale();
                fixTranslation();
                setImageMatrix(matrix);
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                isDragging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastX;
                float dy = event.getY() - lastY;

                if (getCurrentScale() > minScale) {
                    matrix.postTranslate(dx, dy);
                    fixTranslation();
                    setImageMatrix(matrix);
                    isDragging = true;
                }

                lastX = event.getX();
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                break;
        }

        return true;
    }

    private float getCurrentScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    private void fixScale() {
        float scale = getCurrentScale();
        if (scale < minScale) {
            float factor = minScale / scale;
            matrix.postScale(factor, factor, getWidth() / 2f, getHeight() / 2f);
        } else if (scale > maxScale) {
            float factor = maxScale / scale;
            matrix.postScale(factor, factor, getWidth() / 2f, getHeight() / 2f);
        }
    }

    private void fixTranslation() {
        matrix.getValues(matrixValues);
        float transX = matrixValues[Matrix.MTRANS_X];
        float transY = matrixValues[Matrix.MTRANS_Y];

        float scale = matrixValues[Matrix.MSCALE_X];
        float imageWidth = getDrawable().getIntrinsicWidth() * scale;
        float imageHeight = getDrawable().getIntrinsicHeight() * scale;

        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float maxTransX = 0;
        float minTransX = viewWidth - imageWidth;
        float maxTransY = 0;
        float minTransY = viewHeight - imageHeight;

        if (minTransX > 0) minTransX = maxTransX = (viewWidth - imageWidth) / 2;
        if (minTransY > 0) minTransY = maxTransY = (viewHeight - imageHeight) / 2;

        float newTransX = Math.min(Math.max(transX, minTransX), maxTransX);
        float newTransY = Math.min(Math.max(transY, minTransY), maxTransY);

        matrix.postTranslate(newTransX - transX, newTransY - transY);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        float currentScale = getCurrentScale();
        float newScale = currentScale * scaleFactor;

        if (newScale >= minScale && newScale <= maxScale) {
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            fixTranslation();
            setImageMatrix(matrix);
        }

        return true;
    }

    @Override public boolean onScaleBegin(ScaleGestureDetector detector) { return true; }
    @Override public void onScaleEnd(ScaleGestureDetector detector) {}

    public void resetZoom() {
        matrix.reset();

        Drawable d = getDrawable();
        if (d == null) return;

        float viewWidth = getWidth();
        float viewHeight = getHeight();
        float imageWidth = d.getIntrinsicWidth();
        float imageHeight = d.getIntrinsicHeight();

        float scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        minScale = scale;

        float dx = (viewWidth - imageWidth * scale) / 2;
        float dy = (viewHeight - imageHeight * scale) / 2;

        matrix.postScale(scale, scale);
        matrix.postTranslate(dx, dy);
        setImageMatrix(matrix);
    }
}


