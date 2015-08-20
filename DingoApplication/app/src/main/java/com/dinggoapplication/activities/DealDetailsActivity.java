/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.activities;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.managers.DealManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

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
    TextView mCompanyName ,mMerchantDescription, mAddressTextView, mWebAddressTextView, mMobileNumber, dDealName,
    dSeatOffered, dTimeLeft, dRedeemBy, dDealDescription, dDealDescriptionReadMore, dMerchantAllReviews;
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
    /** Object that contains the latitude and longitude of the merchant's location */
    LatLng mLatLng;

    RatingBar merchantRatingBar;

    DateFormat dateFormat;

    private static final String TAG = makeLogTag(DealDetailsActivity.class);
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

        setToolbarNavigationUp(getActionBarToolbar());

        dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // Retrieve deal from the deal manager class
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
                /*metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int width = metrics.widthPixels;
                int height = width * 300 / 480;*/
                imageView.setImageBitmap(merchant.getCoverImage());

                dDealName = (TextView) findViewById(R.id.dealName);
                dDealName.setText(deal.getDealName());

                dSeatOffered = (TextView) findViewById(R.id.seatsOffered);
                dSeatOffered.setText("" + deal.getSeatToOffer());
                dTimeLeft = (TextView) findViewById(R.id.timeLeft);
                dTimeLeft.setText("1 H 25 M");
                dRedeemBy = (TextView) findViewById(R.id.redeemBy);
                dRedeemBy.setText(dateFormat.format(deal.getRedeemBy()));

                dDealDescription = (TextView) findViewById(R.id.dealDescription);
                dDealDescription.setText(deal.getDesciption());

                dDealDescriptionReadMore = (TextView) findViewById(R.id.readMore);
                dDealDescriptionReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onclick for read more");

                        DealDescriptionDialogFragment frag = DealDescriptionDialogFragment
                                .newInstance(deal.getDesciption(), deal.getTermConditions());

                        frag.show(getSupportFragmentManager(), "dialog");

                    }
                });

                //get xml elements
                mMerchantDescription = (TextView) findViewById(R.id.companyDescription);
                mAddressTextView = (TextView) findViewById(R.id.branchAddress);
                mWebAddressTextView = (TextView) findViewById(R.id.companyWebsite);
                mMobileNumber = (TextView) findViewById(R.id.branchPhoneNo);

                //get & set merchant description, address, web address, mobile no.

                mMerchantDescription.setText(merchant.getDescription());

                String mAddress = branch.getAddress1();
                mAddressTextView.setText(mAddress + "\n" + "Singapore " + branch.getPostCode());

                mWebAddressTextView.setText(merchant.getWebsiteUrl());

                mMobileNumber.setText(String.valueOf(branch.getPhoneNo()));

                dMerchantAllReviews = (TextView) findViewById(R.id.allReviews);
                dMerchantAllReviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "onclick for all reviews");

                        Intent intent = new Intent(DealDetailsActivity.this, MerchantReviews.class);
                        /*intent.putExtra("deal_referenceCode", deal.getReferenceCode());
                        intent.putExtra("mCompanyName", merchant.getCompanyName());*/
                        startActivity(intent);

                    }
                });
            } catch (ParseException e) {
                Log.e(TAG, "Unable to parse cover image into Bitmap Object");
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
            mLatLng = branch.getLatLng();
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
                    /*Intent intent = new Intent(DealDetailsActivity.this, DingedDealDetailsActivity.class);
                    intent.putExtra("deal_referenceCode", deal.getReferenceCode());
                    intent.putExtra("mCompanyName", merchant.getCompanyName());
                    if(!discount.isEmpty()) {
                        intent.putExtra("discountString", discount);
                    }
                    startActivity(intent);*/
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

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }

    static public class DealDescriptionDialogFragment extends DialogFragment {

        public static DealDescriptionDialogFragment newInstance(String dealDescription, String dealTC) {
            DealDescriptionDialogFragment frag = new DealDescriptionDialogFragment();
            Bundle args = new Bundle();
            args.putString("dealDescription", dealDescription);
            args.putString("dealTC", dealTC);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout to use as dialog or embedded fragment
            final View view = inflater.inflate(R.layout.deal_description_dialog, container, false);

            //Set values for dialog components
            TextView dialogDealDescription = (TextView) view.findViewById(R.id.dealDescription);
            String dealDescription = getArguments().getString("dealDescription");
            dialogDealDescription.setText(dealDescription);

            TextView dialogTC = (TextView) view.findViewById(R.id.dealTC);
            String dealTC = getArguments().getString("dealTC");
            dialogDealDescription.setText(dealDescription);
            dialogTC.setText(dealTC);

            //close dialog button
            ImageView closeDialog = (ImageView) view.findViewById(R.id.closeDealDetailsDialog);
            closeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Close dialog
                    getDialog().dismiss();

                }
            });

            return view;
        }

        /** The system calls this only when creating the layout in a dialog. */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // The only reason you might override this method when using onCreateView() is
            // to modify any dialog characteristics. For example, the dialog includes a
            // title by default, but your custom layout might not need it. So here you can
            // remove the dialog title, but you must call the superclass to get the Dialog.
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            return dialog;
        }
    }
}