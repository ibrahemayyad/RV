package com.asynctask.eutd.rv;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class cMainTextView extends TextView {
    public cMainTextView(Context context) {
        super(context);
        init();
    }

    public cMainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public cMainTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"Ed Wood Movies.ttf");

        setTypeface(typeface);
    }
}
