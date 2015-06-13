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
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.R;

import net.glxn.qrgen.android.QRCode;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerDingedDeal extends Activity {

    Deal deal;
    TextView mCompanyNameTV, dDiscountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dinged_deal);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText(getResources().getString(R.string.app_name));
        mCompanyNameTV = (TextView) findViewById(R.id.mCNameTextView);
        dDiscountTV = (TextView) findViewById(R.id.dDiscountTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //set textview for QR code from deal details
            mCompanyNameTV.setText(extras.getString("mCompanyName"));
            dDiscountTV.setText(extras.getString("discountString") + " Your Bill");

            //set QR code to image view
            Bitmap bmp = QRCode.from(extras.getString("deal_referenceCode")).bitmap();
            ImageView myImage = (ImageView) findViewById(R.id.qrCodeImageView);
            myImage.setImageBitmap(bmp);

        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
