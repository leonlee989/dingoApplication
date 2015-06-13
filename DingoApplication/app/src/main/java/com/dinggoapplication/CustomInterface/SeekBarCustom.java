/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.CustomInterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

        String text = Integer.toString(this.getProgress()) + " KM";

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);
        paint.setStyle(Paint.Style.FILL);

        int thumb_x = (int) (((double)this.getProgress()/this.getMax()) * (double)this.getWidth() - 20.0);
        int middle = (this.getHeight()/2) - 80;

        super.onDraw(canvas);

        canvas.drawText(text, thumb_x, middle, paint);

    }
}
