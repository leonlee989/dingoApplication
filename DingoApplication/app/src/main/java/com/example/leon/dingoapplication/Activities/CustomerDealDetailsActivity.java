package com.example.leon.dingoapplication.Activities;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leon.dingoapplication.Constants;
import com.example.leon.dingoapplication.Entity.Address;
import com.example.leon.dingoapplication.Entity.Deal;
import com.example.leon.dingoapplication.Entity.Merchant;
import com.example.leon.dingoapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.LatLng;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerDealDetailsActivity extends Activity {

    ImageView imageView;
    TextView mCompanyName,mDescriptionTextView, mAddressTextView, mWebAddressTextView, mMobileNumber, dDiscount;
    DisplayMetrics metrics;
    Merchant merchant;
    Deal deal;
    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_deal_details);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText(getResources().getString(R.string.app_name));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Toast.makeText(this, extras.getString("deal_referenceCode"), Toast.LENGTH_LONG).show();

            //get deal
            deal = Constants.dealManager.getDeal(extras.getString("deal_referenceCode"));
            merchant = deal.getMerchant();
            imageView = (ImageView) findViewById(R.id.dealImage);

            //get device application screen resolution
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = width*200/480;

            //resize image resolution to device resolution
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(metrics.widthPixels,height);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.peachgardendealdetail);
            //imageView.setImageBitmap(deal.getImage);

            //get xml elements
            mCompanyName = (TextView) findViewById(R.id.companyName);
            mDescriptionTextView = (TextView) findViewById(R.id.merchantDescription);
            mAddressTextView = (TextView) findViewById(R.id.merchantAddress);
            mWebAddressTextView = (TextView) findViewById(R.id.merchantWebAddress);
            mMobileNumber = (TextView) findViewById(R.id.merchantMobileNumber);
            dDiscount = (TextView) findViewById(R.id.discount);


            //get & set merchant companyname, description, address, web address, mobile no.
            mCompanyName.setText(merchant.getCompanyName());

            mDescriptionTextView.setText("Peach Garden @ Chinatown Point is renown for their " +
                    "quality culinary offerings and distinguished fine dining experience");
            //mDescriptionTextView.setText(merchant.getDescription);

            Address mAddress = merchant.getAddress();
            mAddressTextView.setText(mAddress.getUnitNumber() + "\n" + mAddress.getHouseNumber() + " " +
                    mAddress.getStreetName()+ "\n" + "Singapore " + mAddress.getPostalCode());

            mWebAddressTextView.setText("peachgarden.com.sg");
            //mWebAddressTextView.setText(merchant.getWebAddress);

            mMobileNumber.setText("67020603");
            //mMobileNumber.setText(merchant.getContactNumber);

            // Gets the MapView from the XML layout and creates it
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);

            // Gets to GoogleMap from the MapView and does initialization stuff
            map = mapView.getMap();
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);

            GoogleMapOptions options = new GoogleMapOptions();
            options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                    .liteMode(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            try {
                MapsInitializer.initialize(CustomerDealDetailsActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
