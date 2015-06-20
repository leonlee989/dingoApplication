/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Activities;

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

import com.merchant_dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within Merchant landing page
 * <p>
 * Inflated layout that provide a list of options for merchant to navigate around the application
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 1.5
 * Created by Leon on 02/28/2015.
 */
public class MerchantActivity extends ListActivity {

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
            // Customized title as TextView in the Action bar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_merchant_landing_custom);
        }

        // Set image for activity action bar
        ImageView image = (ImageView) findViewById(R.id.merchant_actionbar_icon);
        image.setImageResource(R.mipmap.bell);

        // Set title for the action bar title
        TextView title = (TextView) findViewById(R.id.merchant_actionbar_title);
        title.setText(getResources().getString(R.string.company_name));

        // Set sub title for the action bar sub title
        TextView subTitle = (TextView) findViewById(R.id.merchant_actionbar_subtitle);
        subTitle.setText("Merchant");

        // Assign option text for merchant to an array of string
        final int noOfOption = 4;
        String[] values = new String[noOfOption];
        for (int i=0; i < noOfOption; i++) {
            String resName = "merchant_optionText_" + (i+1);
            int resId = getResources().getIdentifier(resName, "string", getPackageName());

            values[i] = getResources().getString(resId);
        }

        final MerchantArrayAdapter adapter = new MerchantArrayAdapter(this, values);
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
        // Action for individual actions
        switch(position) {
            case 0:
                Intent intent1 = new Intent(MerchantActivity.this, DingADeal.class);
                startActivity(intent1);
                break;
            case 1:
                Intent intent2 = new Intent(MerchantActivity.this, OngoingDeal.class);
                startActivity(intent2);
                break;
            default:
                String item = (String) getListAdapter().getItem(position);
                Toast.makeText(MerchantActivity.this, item + " selected", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Array adapter to customize list view that is displayed for merchant selection
     */
    public class MerchantArrayAdapter extends ArrayAdapter<String> {
        /** Application context that contains context resources of the application */
        private final Context context;
        /** A array of values to display as text on the list view */
        private final String[] values;

        /**
         * Constructor to initialize MerchantArrayAdapter
         * @param context   Application context that contains context resources of the application
         * @param values    A array of values to display as text on the list view
         */
        public MerchantArrayAdapter(Context context, String[] values) {
            super(context, R.layout.landing_row, values);
            this.context = context;
            this.values = values;
        }

        /**
         * {@inheritDoc}
         *
         * @param position      Position of the view
         * @param convertView   View that is displayed for the row
         * @param parent        Parent element
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.landing_row, parent, false);

            //Adjust resolution
            RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.rowId);
            Display display = getWindowManager().getDefaultDisplay();
            layout.getLayoutParams().height = (display.getHeight() - 55*4)/4;

            // Set label for different options
            TextView label = (TextView) rowView.findViewById(R.id.landing_label);

            //Set Value for label
            label.setText(values[position]);

            //Changing to the different icons
            ImageView imageIcon = (ImageView) rowView.findViewById(R.id.landing_icon);
            switch(position) {
                case 0:
                    imageIcon.setImageResource(R.mipmap.ding);
                    break;
                case 1:
                    imageIcon.setImageResource(R.mipmap.ongoing);
                    break;
                case 2:
                    imageIcon.setImageResource(R.mipmap.statistic);
                    break;
                case 3:
                    imageIcon.setImageResource(R.mipmap.preferences);
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
