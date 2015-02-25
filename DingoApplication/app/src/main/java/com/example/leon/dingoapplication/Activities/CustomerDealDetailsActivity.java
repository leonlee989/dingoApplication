package com.example.leon.dingoapplication.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leon.dingoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerDealDetailsActivity extends FragmentActivity {

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
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
