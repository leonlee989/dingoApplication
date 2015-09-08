package com.dinggo.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggo.R;
import com.dinggo.entities.Company;
import com.dinggo.managers.CompanyManager;
import com.parse.ParseException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggo.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/8/15.
 */
public class MerchantDetails extends BaseActivity {
    /** String value that contains the name of the tag for this class */
    private static final String TAG = makeLogTag(MerchantDetails.class);
    TextView mMerchantDescription, mAddressTextView, mWebAddressTextView, mMobileNumber;
    ImageView mCompanyCoverImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);
        setToolbarNavigationUp(getActionBarToolbar());

        mCompanyCoverImage = (ImageView) findViewById(R.id.companyCoverImage);
        mMerchantDescription = (TextView) findViewById(R.id.companyDescription);
        mAddressTextView = (TextView) findViewById(R.id.branchAddress);
        mWebAddressTextView = (TextView) findViewById(R.id.companyWebsite);
        mMobileNumber = (TextView) findViewById(R.id.branchPhoneNo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Retrieve company object from company manager class
            CompanyManager companyManager = CompanyManager.getInstance();

            try {
                Company company = companyManager.getCompany(extras.getString("companyId"));
                //TODO get Branch and fill up everything

                mCompanyCoverImage.setImageBitmap(company.getCoverImage());
                mMerchantDescription.setText(company.getDescription());
                //String mAddress = branch.getAddress1();
                //mAddressTextView.setText(mAddress + "\n" + "Singapore " + branch.getPostCode());
                mWebAddressTextView.setText(company.getWebsiteUrl());
                //mMobileNumber.setText(String.valueOf(branch.getPhoneNo()));

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
