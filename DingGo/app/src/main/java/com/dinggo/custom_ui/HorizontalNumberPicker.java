/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggo.custom_ui;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.dinggo.R;

/**
 * Class that customizes Number Picker element to allow it to be displayed horizontally
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 26/02/15.
 */
public class HorizontalNumberPicker extends LinearLayout implements OnClickListener {
    /** Buttons that is associated with the Number Picker */
    Button plus, minus;
    /** Text View that is associated with the Number Picker */
    TextView pickerText;
    /** Start value for the number picker */
    int nStart = 0;
    /** Max value for the number picker */
    int nEnd = 30;

    /**
     * Constructor to initialize HorizontalNumberPicker with the following parameters
     *
     * @param context   Application context that stores all the application resources
     * @param attrs     A set of attributes that is associated with the Horizontal Number Picker
     */
    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.horizontal_number_picker, this);

        plus = (Button) findViewById(R.id.plusButton);
        minus = (Button) findViewById(R.id.minusButton);
        pickerText = (TextView) findViewById(R.id.pickerNumberText);
        pickerText.setText("2");
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String getString = String.valueOf(pickerText.getText());
        int current = Integer.parseInt(getString);
        if (v == plus) {
            if (current < nEnd) {
                current++;
                pickerText.setText(String.valueOf(current));
            }

        }
        if (v == minus) {
            if (current > nStart) {
                current--;
                pickerText.setText(String.valueOf(current));
            }
        }

    }
}