/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Customer activity that deals with the activities executed in the customer landing page
 * Inflated layout that displays a menu of options for customer to navigate
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * @deprecated
 * Created by Leon on 18/2/2015.
 */
public class CustomerActivity extends ListActivity {

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

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);
        }

        // Set image for activity action bar
        //ImageView image = (ImageView) findViewById(R.id.actionbar_icon);
        //image.setImageResource(R.drawable.bell);

        // Set Font for title text
        //TextView title = (TextView) findViewById(R.id.actionbar_title);

        // Set title text for activity action bar
        //title.setText(getResources().getString(R.string.app_name));

        // Menu context to be display on customer landing page
        final int noOfOptions = 6;
        String[] values = new String[noOfOptions];
        for (int i=0; i < noOfOptions; ++i) {
            String resName = "customer_optionText_" + (i+1);
            int resId = getResources().getIdentifier(resName, "string", getPackageName());

            values[i] = getResources().getString(resId);
        }

        final CustomerArrayAdapter adapter = new CustomerArrayAdapter(this, values);
        setListAdapter(adapter);
    }

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l        The ListView where the click happened
     * @param v        The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // Actions for individual actions
        switch (position) {
            case 0:
                Intent intent = new Intent(CustomerActivity.this, EatDrinkActivity.class);
                startActivity(intent);
                break;
            case 5:
                //Intent iPreferences = new Intent(CustomerActivity.this, CustomerPreferences.class);
                //startActivity(iPreferences);
                break;
            default:
                String item = (String) getListAdapter().getItem(position);
                Toast.makeText(CustomerActivity.this, item + " selected", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * ArrayAdapter class to display rows of option for customer landing page
     */
    private class CustomerArrayAdapter extends ArrayAdapter<String> {
        /** Application Context */
        private final Context context;
        /** Values to display as text on each row in the array adaper */
        private final String[] values;

        /**
         * Constructor to initialize Array Adapter as a sub class
         * @param context   Application Context
         * @param values    A list of values to display
         */
        public CustomerArrayAdapter(Context context, String[] values) {
            super(context, R.layout.landing_row, values);
            this.context = context;
            this.values = values;
        }

        /**
         * {@inheritDoc}
         *
         * @param position      Position of the row
         * @param convertView   Layout design for each row
         * @param parent        Parent of the layout
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.landing_row, parent, false);

            // Adjust resolution
            RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.rowId);
            Display display = getWindowManager().getDefaultDisplay();
            layout.getLayoutParams().height = (display.getHeight() - 40*6) /6;

            // Set label for different options
            TextView label = (TextView) rowView.findViewById(R.id.landing_label);

            // Set value for label
            label.setText(values[position]);

            // Changing to the different icons
            ImageView imageIcon = (ImageView) rowView.findViewById(R.id.landing_icon);
            switch (position) {
                case 0:
                    //imageIcon.setImageResource(R.drawable.meal);
                    break;
                case 1:
                    imageIcon.setImageResource(R.drawable.watch);
                    break;
                case 2:
                    imageIcon.setImageResource(R.drawable.play);
                    break;
                case 3:
                    imageIcon.setImageResource(R.drawable.buy);
                    break;
                case 4:
                    //imageIcon.setImageResource(R.drawable.ding);
                    break;
                case 5:
                    //imageIcon.setImageResource(R.drawable.preferences);
                    break;
            }

            return rowView;
        }
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
