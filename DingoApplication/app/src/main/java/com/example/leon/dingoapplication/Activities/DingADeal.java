package com.example.leon.dingoapplication.Activities;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leon.dingoapplication.R;

public class DingADeal extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_adeal);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the Action bar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_merchant_landing_custom);

        // Set image for activity action bar
        ImageView image = (ImageView) findViewById(R.id.merchant_actionbar_icon);
        image.setImageResource(R.drawable.bell);

        // Set title for the action bar title
        TextView title = (TextView) findViewById(R.id.merchant_actionbar_title);
        title.setText(getResources().getString(R.string.app_name));

        // Set sub title for the action bar sub title
        TextView subTitle = (TextView) findViewById(R.id.merchant_actionbar_subtitle);
        subTitle.setText("Merchant");
    }
}
