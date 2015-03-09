package com.dinggoapplication.CustomInterface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by Leon on 9/3/2015.
 */
public class SeekBarCustom extends SeekBar {
    public SeekBarCustom(Context context) {
        super(context);
    }

    public SeekBarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int thumb_x = (this.getProgress()/this.getMax()) * this.getWidth();
        int middle = this.getHeight()/2;
        canvas.drawText("Test", thumb_x,middle, null);
        super.onDraw(canvas);
    }
}
