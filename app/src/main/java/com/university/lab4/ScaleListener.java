package com.university.lab4;

import android.util.Log;
import android.view.ScaleGestureDetector;

public abstract class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        Log.i("swipe", "onScale");
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.i("swipe", "onScaleBegin " + scaleGestureDetector.getScaleFactor());
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.i("swipe", "onScaleEnd");
        //onZoomScale(scaleGestureDetector.getScaleFactor());
    }

    public abstract void onZoomScale(float scaleFactor);
}