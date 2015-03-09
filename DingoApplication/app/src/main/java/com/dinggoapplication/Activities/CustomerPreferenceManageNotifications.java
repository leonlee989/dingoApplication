package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferenceManageNotifications extends Activity {

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

        // Get the values for the controls
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarProximity);
        seekBar.setMax(150);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
