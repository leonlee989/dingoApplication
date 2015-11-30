/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DingedDeal;
import com.dinggoapplication.managers.DealManager;
import com.dinggoapplication.managers.DingnedDealManager;
import com.parse.ParseException;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

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
    TextView redeemByTV, dDiscountTV, referenceID,textTNC;
    ImageView dingeddealImage;
    Deal deal;
    Company merchant;
    Branch branch;
    DingedDeal dingedDeal;
    public static final String MyPREFERENCES = "MyPrefs" ;
    /**
     * String value that contains the tag name for this activity
     */
    private static final String TAG = makeLogTag(DingedDealDetailsActivity.class);

    /**
     * Date format for display purpose
     */
    DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
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

        //TODO retrieve and set TV to display dynamic values
        redeemByTV = (TextView) findViewById(R.id.redeemBy);
        referenceID = (TextView) findViewById(R.id.referenceID);
        dDiscountTV = (TextView) findViewById(R.id.dDiscountTextView);
        textTNC = (TextView) findViewById(R.id.termnCondition);
        dingeddealImage = (ImageView) findViewById(R.id.dingdealImage);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DealManager dealManager = DealManager.getInstance();
            DingnedDealManager dingnedDealManager = DingnedDealManager.getInstance();

            try {
                // Retrieve deal from the deal manager class
                int ref = extras.getInt("dingdeal_confirmationId");
                Log.d("REF", String.valueOf(ref));

                deal = dealManager.getDeal(extras.getString("deal_referenceCode"));
                Log.d("DEal RID",deal.getReferenceId());
              // String conId = String.valueOf(dingedDeal.getConfirmationId());
                branch = deal.getBranch().fetchIfNeeded();
                merchant = branch.getCompany();
                Bitmap coverImage = deal.getDealImage();
                if (coverImage != null)
                    dingeddealImage.setImageBitmap(deal.getDealImage());
                dDiscountTV.setText(deal.getDescription().toString());
                redeemByTV.setText("Redeem by "+dateFormat.format(deal.getRedeemBy()));
                textTNC.setText(deal.getTermConditions().toString());
                    referenceID.setText(String.valueOf(ref));


            }catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        else{
            Log.d("DINGEDDETAIL","Bundle null");
        }

//
//        final Toolbar toolbar = getActionBarToolbar();
//        toolbar.setNavigationIcon(R.drawable.ic_up);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

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