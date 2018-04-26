package com.example.an.pnaandroid.library.uicustom;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomButton extends AppCompatButton implements View.OnTouchListener {
    public CustomButton(Context context) {
        super(context);

    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
