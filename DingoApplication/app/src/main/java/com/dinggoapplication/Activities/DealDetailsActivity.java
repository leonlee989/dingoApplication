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
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.managers.DealManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;

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
public class DealDetailsActivity extends BaseActivity{

    /** Element that contains the cover image for the deal */
    ImageView imageView;
    /** Text view that contains the information about the merchant */
    TextView mCompanyName,mDescriptionTextView, mAddressTextView, mWebAddressTextView, mMobileNumber, dDealName,
    dSeatOffered, dTimeLeft, dRedeemBy;
    /** Object that contains the resolution of the mobile's diaplay */
    DisplayMetrics metrics;
    /** Company object that contains information about the company whose branch is offering the respective deal */
    Company merchant;
    /** Branch object that contains information about the branch that offers the respective deal */
    Branch branch;
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

    RatingBar merchantRatingBar;

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

        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

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
            //deal = Config.dealManager.getDeal(extras.getString("deal_referenceCode"));
            DealManager dealManager = DealManager.getInstance();
            deal = dealManager.getDeal(extras.getString("deal_referenceCode"));
            branch = deal.getBranch();
            merchant = branch.getCompany();

            try {
                imageView = (ImageView) findViewById(R.id.dealImage);
                toolbarLayout.setTitle(merchant.getCompanyName());
                toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
                toolbarLayout.setCollapsedTitleTextAppearance(R.attr.fontPath);
                toolbarLayout.setExpandedTitleTextAppearance(R.attr.fontPath);
                //get device application screen resolution
                metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int width = metrics.widthPixels;
                int height = width * 300 / 480;
                imageView.setImageBitmap(merchant.getCoverImage());

                dDealName = (TextView) findViewById(R.id.dealName);
                dDealName.setText(deal.getDealName());

                dSeatOffered = (TextView) findViewById(R.id.seatsOffered);
                dSeatOffered.setText("" + deal.getSeatToOffer());
                dTimeLeft = (TextView) findViewById(R.id.timeLeft);
                dTimeLeft.setText("1 H 25 M");
                dRedeemBy = (TextView) findViewById(R.id.redeemBy);
                dRedeemBy.setText("2.10 PM");

            } catch (ParseException e) {
                Log.e("Deal", "Unable to parse cover image into Bitmap Object");
            }

            /*if(deal instanceof PercentageDiscount){
                double pDiscount = ((PercentageDiscount) deal).getPercentage();
                discount = String.valueOf(pDiscount) + "% Off";
                dDiscount.setText(discount);
            } else if(deal instanceof TierDiscount){
                double tierAmount = ((TierDiscount) deal).getTierAmount();
                double tDiscount =  ((TierDiscount) deal).getDiscountAmount();
                discount = "Spend $" + String.valueOf(tierAmount) + " get $" + String.valueOf(tDiscount) + " Off";
                dDiscount.setText(discount);
            }
/*
            //resize image resolution to device resolution
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(metrics.widthPixels,height);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(deal.getCoverImage());

            //get rating
            merchantRatingBar = (RatingBar) findViewById(R.id.merchantRating);

            //get xml elements
            mCompanyName = (TextView) findViewById(R.id.companyName);*//*
            mDescriptionTextView = (TextView) findViewById(R.id.merchantDescription);
            mAddressTextView = (TextView) findViewById(R.id.merchantAddress);
            mWebAddressTextView = (TextView) findViewById(R.id.merchantWebAddress);
            mMobileNumber = (TextView) findViewById(R.id.merchantMobileNumber);*//*
            dDiscount = (TextView) findViewById(R.id.discount);

            //get & set merchant companyname, description, address, web address, mobile no.
            mCompanyName.setText(merchant.getCompanyName());

            *//*mDescriptionTextView.setText(merchant.getMerchantDescription());

            Address mAddress = merchant.getAddress();
            mAddressTextView.setText(mAddress.getUnitNumber() + "\n" + mAddress.getHouseNumber() + " " +
                    mAddress.getStreetName()+ "\n" + "Singapore " + mAddress.getPostalCode());

            mWebAddressTextView.setText(merchant.getWebsite());

            mMobileNumber.setText(String.valueOf(merchant.getContactNumber()));*//*

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
            *//*mapView = (MapView) findViewById(R.id.mapview);
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
            map.animateCamera(cameraUpdate);*//*

            findViewById(R.id.dingItButton).setOnClickListener(new View.OnClickListener() {
                *//**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 *//*
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
            });*/

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

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }

}