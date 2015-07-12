/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.Config;
import com.dinggoapplication.Entity.Address;
import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.Entity.PercentageDiscount;
import com.dinggoapplication.Entity.TierDiscount;
import com.dinggoapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within the single detail page for deals
 * <p>
 * Inflated layout that displays full details on a particular deal
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/2/2015.
 */
public class DealDetailsActivity extends BaseActivity {

    /** Element that contains the cover image for the deal */
    ImageView imageView;
    /** Text view that contains the information about the merchant */
    TextView mCompanyName,mDescriptionTextView, mAddressTextView, mWebAddressTextView, mMobileNumber, dDiscount;
    /** Object that contains the resolution of the mobile's diaplay */
    DisplayMetrics metrics;
    /** Merchant object that contains information about the merchant offering the respective deal */
    Merchant merchant;
    /** Deal object that contains information about the deals */
    Deal deal;
    /** Element that contains a map view to display the location of the merchant */
    MapView mapView;
    /** Object /  instance that provide geo services */
    GoogleMap map;
    /** Discount information of the deal */
    String discount;
    /** Object that contains the latitude and longitude of the merchant's location */
    LatLng mLatLng;

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
        setContentView(R.layout.activity_deal_details);

        final Toolbar toolbar = getActionBarToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //get deal
            deal = Config.dealManager.getDeal(extras.getString("deal_referenceCode"));
            merchant = deal.getMerchant();
            imageView = (ImageView) findViewById(R.id.dealImage);

            //get device application screen resolution
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = width*200/480;

            //resize image resolution to device resolution
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(metrics.widthPixels,height);
            imageView.setLayoutParams(layoutParams);
            System.out.println("LayoutParams: "+layoutParams);
            imageView.setImageBitmap(deal.getCoverImage());

            //get xml elements
            mCompanyName = (TextView) findViewById(R.id.companyName);
            mDescriptionTextView = (TextView) findViewById(R.id.merchantDescription);
            mAddressTextView = (TextView) findViewById(R.id.merchantAddress);
            mWebAddressTextView = (TextView) findViewById(R.id.merchantWebAddress);
            mMobileNumber = (TextView) findViewById(R.id.merchantMobileNumber);
            dDiscount = (TextView) findViewById(R.id.discount);

            //get & set merchant companyname, description, address, web address, mobile no.
            mCompanyName.setText(merchant.getCompanyName());

            mDescriptionTextView.setText(merchant.getMerchantDescription());

            Address mAddress = merchant.getAddress();
            mAddressTextView.setText(mAddress.getUnitNumber() + "\n" + mAddress.getHouseNumber() + " " +
                    mAddress.getStreetName()+ "\n" + "Singapore " + mAddress.getPostalCode());

            mWebAddressTextView.setText(merchant.getWebsite());

            mMobileNumber.setText(String.valueOf(merchant.getContactNumber()));

            if(deal instanceof PercentageDiscount){
                double pDiscount = ((PercentageDiscount) deal).getPercentage();
                discount = String.valueOf(pDiscount) + "% Off";
                dDiscount.setText(discount);
            } else if(deal instanceof TierDiscount){
                double tierAmount = ((TierDiscount) deal).getTierAmount();
                double tDiscount =  ((TierDiscount) deal).getDiscountAmount();
                discount = "Spend $" + String.valueOf(tierAmount) + " get $" + String.valueOf(tDiscount) + " Off";
                dDiscount.setText(discount);
            }

            // Gets the MapView from the XML layout and creates it
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);

            // Gets to GoogleMap from the MapView and does initialization stuff
            map = mapView.getMap();
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);

            GoogleMapOptions options = new GoogleMapOptions();
            options.mapType(GoogleMap.MAP_TYPE_NORMAL).liteMode(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            try {
                MapsInitializer.initialize(DealDetailsActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLatLng = merchant.getLatLng();
            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLatLng, 10);
            map.animateCamera(cameraUpdate);

            findViewById(R.id.dingItButton).setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DealDetailsActivity.this, DingedDealDetailsActivity.class);
                    intent.putExtra("deal_referenceCode", deal.getReferenceCode());
                    intent.putExtra("mCompanyName", merchant.getCompanyName());
                    if(!discount.isEmpty()) {
                        intent.putExtra("discountString", discount);
                    }
                    startActivity(intent);
                }
            });
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