package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferenceManageNotifications extends Activity implements SeekBar.OnSeekBarChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preference_manage_notifications);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText("Manage Notifications");

        // Get the values for the seek bar controls
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarProximity);
        seekBar.setMax(150);
        seekBar.setOnSeekBarChangeListener(this);

        // ToDo: Set progress according to shared preferences
        seekBar.setProgress(50);

        // After activity is created
        ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // Set text view value and position
                TextView currentValue = (TextView) findViewById(R.id.currentValue);
                setSeekBarTextPosition(seekBar, seekBar.getProgress(), currentValue);

                // Remove listener from the root view
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView currentValue = (TextView) this.findViewById(R.id.currentValue);
        setSeekBarTextPosition(seekBar, progress, currentValue);
    }

    public void setSeekBarTextPosition(SeekBar seekBar, int progress, TextView currentValue) {
        // Set position for current value
        int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        currentValue.setText("" + progress + " km");
        currentValue.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
