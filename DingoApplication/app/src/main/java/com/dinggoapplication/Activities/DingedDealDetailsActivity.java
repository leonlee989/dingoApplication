/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within dinged deal page
 * <p>
 * Inflated layout that display a list of deal that is dinged by the customers
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/2/2015.
 */
public class DingedDealDetailsActivity extends BaseActivity {

    /** TextView object that displays the company name and discount offered */
    TextView redeemByTV, dDiscountTV, referenceID;

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
        setContentView(R.layout.activity_dinged_deal_details);

        final Toolbar toolbar = getActionBarToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        redeemByTV = (TextView) findViewById(R.id.redeemBy);
        referenceID = (TextView) findViewById(R.id.referenceID);
        dDiscountTV = (TextView) findViewById(R.id.dDiscountTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //TODO retrieve and set TV to display dynamic values
            dDiscountTV.setText(extras.getString("discountString") + " Your Bill");
            redeemByTV.setText("Redeem by 2.00pm");
            referenceID.setText("D1234567");
            //referenceID.setText(extras.getString("referenceID"));
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