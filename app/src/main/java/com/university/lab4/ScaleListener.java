package com.university.lab4;

import android.view.ScaleGestureDetector;

public abstract class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        onZoomScale(scaleGestureDetector.getScaleFactor());
    }

    public abstract void onZoomScale(float scaleFactor);
}