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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.R;

import net.glxn.qrgen.android.QRCode;

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
public class CustomerDingedDeal extends Activity {

    /** TextView object that displays the company name and discount offered */
    TextView mCompanyNameTV, dDiscountTV;

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
        setContentView(R.layout.activity_customer_dinged_deal);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);
        }

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText(getResources().getString(R.string.app_name));
        mCompanyNameTV = (TextView) findViewById(R.id.mCNameTextView);
        dDiscountTV = (TextView) findViewById(R.id.dDiscountTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //set TextView for QR code from deal details
            mCompanyNameTV.setText(extras.getString("mCompanyName"));
            dDiscountTV.setText(extras.getString("discountString") + " Your Bill");

            //set QR code to image view
            Bitmap bmp = QRCode.from(extras.getString("deal_referenceCode")).bitmap();
            ImageView myImage = (ImageView) findViewById(R.id.qrCodeImageView);
            myImage.setImageBitmap(bmp);

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