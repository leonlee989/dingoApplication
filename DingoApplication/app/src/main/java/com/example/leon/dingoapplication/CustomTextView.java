package com.example.leon.dingoapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Leon on 15/2/2015.
 */
public class CustomTextView extends TextView {
    Typeface normalFont = Typeface.createFromAsset(getContext().getAssets(), getContext().getResources().getString(R.string.font_normal));

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        super.setTypeface(normalFont, 1);
    }
}
