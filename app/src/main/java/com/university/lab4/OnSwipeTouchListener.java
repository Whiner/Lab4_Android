package com.university.lab4;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public abstract class OnSwipeTouchListener implements View.OnTouchListener {
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    OnSwipeTouchListener(Context context) {
        final OnSwipeTouchListener swipe = this;
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener() {
            @Override
            public void onZoomScale(float scaleFactor) {
                swipe.onZoomScale(scaleFactor);
            }
        });
        gestureDetector = new GestureDetector(context, new GestureListener() {
            @Override
            protected void onSwipeLeft() {
                swipe.onSwipeLeft();
            }

            @Override
            protected void onSwipeRight() {
                swipe.onSwipeRight();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        return gestureDetector.onTouchEvent(motionEvent) && scaleGestureDetector.onTouchEvent(motionEvent);
    }

    public abstract void onSwipeRight();

    public abstract void onSwipeLeft();

    public abstract void onZoomScale(float scaleFactor);
}