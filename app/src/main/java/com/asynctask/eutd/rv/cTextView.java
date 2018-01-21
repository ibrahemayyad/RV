package com.asynctask.eutd.rv;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ibrahem on 1/14/2018.
 */

public class cTextView extends TextView {
    public cTextView(Context context) {
        super(context);
        init();
    }

    public cTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public cTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"GeosansLight.ttf");

        setTypeface(typeface);
    }
}
