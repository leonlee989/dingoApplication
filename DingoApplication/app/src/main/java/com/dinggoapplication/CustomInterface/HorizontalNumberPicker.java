package com.dinggoapplication.CustomInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dinggoapplication.R;
/**
 * Created by siungee on 26/02/15.
 */
public class HorizontalNumberPicker extends LinearLayout implements OnClickListener {

    Button plus, minus;
    TextView pickerText;

    int nStart = 0;
    int nEnd = 30;

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
