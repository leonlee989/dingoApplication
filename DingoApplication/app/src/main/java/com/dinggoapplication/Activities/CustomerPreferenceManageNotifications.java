/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within manege notification page
 * <p>
 * Inflated layout that display a list of settings for users to customized their notification configuration
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 25/2/2015.
 */
public class CustomerPreferenceManageNotifications extends Activity implements SeekBar.OnSeekBarChangeListener{

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preference_manage_notifications);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);
        }

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText("Manage Notifications");

        // Get the values for the seek bar controls
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarProximity);
        seekBar.setMax(150);
        seekBar.setOnSeekBarChangeListener(this);

        // ToDo: Set progress according to shared settings
        seekBar.setProgress(50);

        // After activity is created
        ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * Callback method to be invoked when the global layout state or the visibility of views
             * within the view tree changes
             */
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

    /**
     * Notification that the progress level has changed. Clients can use the fromUser parameter
     * to distinguish user-initiated changes from those that occurred programmatically.
     *
     * @param seekBar  The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the range 0..max where max
     *                 was set by {@link ProgressBar#setMax(int)}. (The default value for max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView currentValue = (TextView) this.findViewById(R.id.currentValue);
        setSeekBarTextPosition(seekBar, progress, currentValue);
    }

    /**
     * Set the text position for the Seek Bar
     * @param seekBar           Seek bar element in the layout
     * @param progress          Integer value that contains the value of the seek bar
     * @param currentValue      TextView element in the layout to display value for the seek bar
     */
    public void setSeekBarTextPosition(SeekBar seekBar, int progress, TextView currentValue) {
        // Set position for current value
        int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        currentValue.setText("" + progress + " km");
        currentValue.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }

    /**
     * Notification that the user has started a touch gesture. Clients may want to use this
     * to disable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Notification that the user has finished a touch gesture. Clients may want to use this
     * to re-enable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
