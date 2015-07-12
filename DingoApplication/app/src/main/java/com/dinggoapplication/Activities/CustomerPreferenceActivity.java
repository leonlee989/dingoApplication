package com.dinggoapplication.Activities;

import android.content.Context;
import android.os.Bundle;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.Utils.LogUtils.makeLogTag;

public class CustomerPreferenceActivity extends BaseActivity {

    private static final String TAG = makeLogTag(CustomerPreferenceActivity.class);

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_PREFERENCES;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getActionBarToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
