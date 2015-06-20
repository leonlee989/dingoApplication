/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.merchant_dinggoapplication.Constants;
import com.merchant_dinggoapplication.Entity.Deal;
import com.merchant_dinggoapplication.Entity.Merchant;
import com.merchant_dinggoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within Ongoing Deal page
 * <p>
 * Inflated layout that provide a list of deals offered by merchant that are ongoing
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 1.5
 * Created by Leon on 1/3/2015.
 */
public class OngoingDeal extends Activity {

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
        setContentView(R.layout.activity_ongoingdeal);

        // Retrieve Merchant Id From saved instances
        /* SharedPreferences object that deal with caching of data in android application */
        SharedPreferences sharedPreferences = getSharedPreferences("MerchantData", Context.MODE_PRIVATE);
        String merchantId = sharedPreferences.getString("merchantId", "");

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);
        }

        // Set Font for title text
        TextView title = (TextView) findViewById(R.id.actionbar_home_title);

        // Set title text for activity action bar
        title.setText("Ongoing Deals");

        ArrayList<Deal> dealListByMerchant = Constants.dealManager.retrieveDealByMerchant(merchantId);
        final OngoingDealArrayAdapter adapter = new OngoingDealArrayAdapter(this, dealListByMerchant);

        ListView list = (ListView) findViewById(R.id.ongoing_deal_list);
        list.setAdapter(adapter);
    }

    /**
     * Array adapter to customize list view that is displayed for merchant selection
     */
    public class OngoingDealArrayAdapter extends ArrayAdapter<Deal> {
        /** Application context that contains context resources of the application */
        private final Context context;
        /** A array of values to display as text on the list view */
        private final ArrayList<Deal> values;

        /**
         * Constructor to initialize OngoingDealArrayAdapter
         * @param context   Application context that contains context resources of the application
         * @param values    A array of values to display as text on the list view
         */
        public OngoingDealArrayAdapter(Context context, ArrayList<Deal> values) {
            super(context, R.layout.deal_view_row, values);
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
            LayoutInflater inflater = getLayoutInflater();

            View rowView = inflater.inflate(R.layout.deal_view_row, parent, false);

            Deal deal = values.get(position);
            Merchant merchant = deal.getMerchant();

            ImageView image = (ImageView) rowView.findViewById(R.id.merchantLogo);
            image.setImageBitmap(merchant.getImage());

            TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
            companyName.setText(merchant.getCompanyName());

            TextView merchantType = (TextView) rowView.findViewById(R.id.merchantType);
            merchantType.setText("@ " + merchant.getAddress().getStreetName());

            TextView dealTextView = (TextView) rowView.findViewById(R.id.dealDetail);
            dealTextView.setText(deal.toString());
            dealTextView.setTypeface(dealTextView.getTypeface(), Typeface.BOLD);

            TextView additionInfo = (TextView) rowView.findViewById(R.id.addtionalInfo);
            additionInfo.setText("2h 45m\nLeft");

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