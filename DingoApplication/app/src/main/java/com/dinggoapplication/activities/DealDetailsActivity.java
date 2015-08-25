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
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
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
import com.dinggoapplication.managers.ReviewManager;
import com.dinggoapplication.utilities.TimeUtils;
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
import java.util.HashMap;
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
    TextView mMerchantDescription, mAddressTextView, mWebAddressTextView, mMobileNumber, dDealName,
            dSeatOffered, dTimeLeft, dRedeemBy, dDealDescription, dDealDescriptionReadMore,
            dMerchantAllReviews, mOverallRatingScore, mNumberOfReviews;
    /** Controller for all rating bar in the summarize review section */
    RatingBar mOverallRB, mOverallFoodDrinkRB, mOverallValueRB, mOverallAmbienceRB, mOverallServiceRB;
    /** Controller for the nested scroll view in the view */
    NestedScrollView nestedScrollView;
    /** Count down timer object to count down the expiry of the deal */
    CountDownTimer countDownTimer;
    /** Object that contains the resolution of the mobile's display */
    //DisplayMetrics metrics;
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
    /** Date format for display purpose */
    DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    /** Average ratings given to the merchant */
    HashMap<String, Float> averageStars;
    /** String value that contains the tag name for this activity */
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DealManager dealManager = DealManager.getInstance();
            try {
                // Retrieve deal from the deal manager class
                deal = dealManager.getDeal(extras.getString("deal_referenceCode"));
                branch = deal.getBranch();
                merchant = branch.getCompany();

                /* ************************* HEADER SECTION ************************************* */
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

                /* ************************ DEAL SECTION **************************************** */
                imageView = (ImageView) findViewById(R.id.dealImage);
                imageView.setImageBitmap(merchant.getCoverImage());
                dDealName = (TextView) findViewById(R.id.dealName);
                dDealName.setText(deal.getDealName());

                dSeatOffered = (TextView) findViewById(R.id.seatsOffered);
                dSeatOffered.setText("" + deal.getSeatToOffer());

                nestedScrollView = (NestedScrollView) findViewById(R.id.scrollView);
                dTimeLeft = (TextView) findViewById(R.id.timeLeft);
                countDownTimer = TimeUtils.setTimer(deal.getRedeemBy(), dTimeLeft, nestedScrollView);
                countDownTimer.start();

                dRedeemBy = (TextView) findViewById(R.id.redeemBy);
                dRedeemBy.setText(dateFormat.format(deal.getRedeemBy()));

                dDealDescription = (TextView) findViewById(R.id.dealDescription);
                dDealDescription.setText(deal.getDescription());

                dDealDescriptionReadMore = (TextView) findViewById(R.id.readMore);
                dDealDescriptionReadMore.setOnClickListener(dealDescriptionReadMoreOnClick);

                /* ******************* MERCHANT SECTION ***************************************** */
                mMerchantDescription = (TextView) findViewById(R.id.companyDescription);
                mMerchantDescription.setText(merchant.getDescription());

                mAddressTextView = (TextView) findViewById(R.id.branchAddress);
                String mAddress = branch.getAddress1();
                mAddressTextView.setText(mAddress + "\n" + "Singapore " + branch.getPostCode());

                mWebAddressTextView = (TextView) findViewById(R.id.companyWebsite);
                mWebAddressTextView.setText(merchant.getWebsiteUrl());

                mMobileNumber = (TextView) findViewById(R.id.branchPhoneNo);
                mMobileNumber.setText(String.valueOf(branch.getPhoneNo()));

                /* ****************** MAP SECTION TO DISPLAY ADDRESS **************************** */
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
                    Log.e(TAG, e.getMessage());
                }
                mLatLng = branch.getLatLng();

                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLatLng, 10);
                map.animateCamera(cameraUpdate);

                /* *********************** REVIEW SECTION *************************************** */
                // Get review according to deal reference Id
                ReviewManager reviewManager = ReviewManager.getInstance();
                averageStars = reviewManager.getAverageRatings(merchant.getCompanyId());

                mOverallRatingScore = (TextView) findViewById(R.id.overallRatingScore);
                mOverallRatingScore.setText(String.valueOf(averageStars.get("total")));

                mNumberOfReviews = (TextView) findViewById(R.id.numberOfReviews);
                mNumberOfReviews.setText(String.valueOf(averageStars.get("numReviews")));

                mOverallRB = (RatingBar) findViewById(R.id.overallRB);
                mOverallRB.setRating(averageStars.get("total"));

                mOverallFoodDrinkRB = (RatingBar) findViewById(R.id.overallFoodDrinkRB);
                mOverallFoodDrinkRB.setRating(averageStars.get("food_drink"));

                mOverallValueRB = (RatingBar) findViewById(R.id.overallValueRB);
                mOverallValueRB.setRating(averageStars.get("value"));

                mOverallAmbienceRB = (RatingBar) findViewById(R.id.overallAmbienceRB);
                mOverallAmbienceRB.setRating(averageStars.get("ambience"));

                mOverallServiceRB = (RatingBar) findViewById(R.id.overallServiceRB);
                mOverallServiceRB.setRating(averageStars.get("service"));

                dMerchantAllReviews = (TextView) findViewById(R.id.allReviews);
                dMerchantAllReviews.setOnClickListener(merchantAllReviewOnClick);

                /* ******************************** DING IT ************************************* */
                findViewById(R.id.dingItButton).setOnClickListener(dingItOnClick);
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }

    }

    /** On click listener for "Read More" text view when user plans to read more about deal details */
    private View.OnClickListener dealDescriptionReadMoreOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DealDescriptionDialogFragment frag = DealDescriptionDialogFragment
                    .newInstance(deal.getDescription(), deal.getTermConditions());

            frag.show(getSupportFragmentManager(), "dialog");
        }
    },
    /** On click listener for "All Reviews" text view when user plans to view the reviews */
    merchantAllReviewOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DealDetailsActivity.this, MerchantReviews.class);
            intent.putExtra("companyId", merchant.getCompanyId());
            intent.putExtra("averageStars", averageStars);
            startActivity(intent);
        }
    },
    /** On click listener for Ding It button when user plans to ding this deal */
    dingItOnClick = new View.OnClickListener() {
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
    };

    /** Upon the destruction of the activity, count down timer will be cancelled */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Methods to trigger when scrollable view is being swipe
     * @return  Boolean object with regards to the status of the swipe
     */
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }

    /**
     * Dialog fragment to display the description of the deal
     */
    static public class DealDescriptionDialogFragment extends DialogFragment {

        /**
         * Method to return a newly created instance of this dialog fragment back to the invoker
         * @param dealDescription   String value that contains the description of the deal
         * @param dealTC            String value that contains the term and conditions of the deal
         * @return                  Dialog fragment that will display the description and term and conditions of the deal
         */
        public static DealDescriptionDialogFragment newInstance(String dealDescription, String dealTC) {
            DealDescriptionDialogFragment frag = new DealDescriptionDialogFragment();

            Bundle args = new Bundle();
            args.putString("dealDescription", dealDescription);
            args.putString("dealTC", dealTC);
            frag.setArguments(args);
            return frag;
        }

        /**
         * Methods to execute during the creation of the dialog fragment
         * @param savedInstanceState    If the dialog fragment is being re-initialized after
         *                              previously being shut down then this Bundle contains the data it most
         *                              recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
        }

        /**
         * Methods to execute after the creation of the dialog fragment
         */
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

        /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null (which
         * is the default implementation).  This will be called between
         * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
         * <p/>
         * <p>If you return a View from here, you will later be called in
         * {@link #onDestroyView} when the view is being released.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate
         *                           any views in the fragment,
         * @param container          If non-null, this is the parent view that the fragment's
         *                           UI should be attached to.  The fragment should not add the view itself,
         *                           but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    //Close dialog
                    getDialog().dismiss();
                }
            });

            return view;
        }

        /**
         * The system calls this only when creating the layout in a dialog.
         *
         * Override to build your own custom Dialog container.  This is typically
         * used to show an AlertDialog instead of a generic Dialog; when doing so,
         * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
         * to be implemented since the AlertDialog takes care of its own content.
         * <p/>
         * <p>This method will be called after {@link #onCreate(Bundle)} and
         * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
         * default implementation simply instantiates and returns a {@link Dialog}
         * class.
         * <p/>
         *
         * @param savedInstanceState The last saved instance state of the Fragment,
         *                           or null if this is a freshly created Fragment.
         * @return Return a new Dialog instance to be displayed by the Fragment.
         */
        @NonNull
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