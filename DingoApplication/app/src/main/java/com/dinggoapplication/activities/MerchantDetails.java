package com.dinggoapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dinggoapplication.R;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.managers.CompanyManager;
import com.parse.ParseException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/8/15.
 */
public class MerchantDetails extends BaseActivity {
    /** String value that contains the name of the tag for this class */
    private static final String TAG = makeLogTag(MerchantDetails.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);
        setToolbarNavigationUp(getActionBarToolbar());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Retrieve company object from company manager class
            CompanyManager companyManager = CompanyManager.getInstance();

            try {
                Company company = companyManager.getCompany(extras.getString("companyId"));
                Log.d(TAG, company.getCompanyId() + ": " + company.getCompanyName() + "\n" + company.getDescription());

            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }
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
