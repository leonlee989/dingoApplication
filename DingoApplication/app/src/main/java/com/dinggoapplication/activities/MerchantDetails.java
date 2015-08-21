package com.dinggoapplication.activities;

import android.content.Context;
import android.os.Bundle;

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/8/15.
 */
public class MerchantDetails extends BaseActivity {

    private static final String TAG = makeLogTag(MerchantDetails.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);

        setToolbarNavigationUp(getActionBarToolbar());

    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
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
