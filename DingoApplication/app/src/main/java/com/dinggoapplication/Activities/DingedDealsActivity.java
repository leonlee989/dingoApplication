package com.dinggoapplication.Activities;

import android.content.Context;
import android.os.Bundle;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.Utils.LogUtils.makeLogTag;

public class DingedDealsActivity extends BaseActivity {

    private static final String TAG = makeLogTag(DingedDealsActivity.class);

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_DINGED_DEALS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinged_deals);
        getActionBarToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
