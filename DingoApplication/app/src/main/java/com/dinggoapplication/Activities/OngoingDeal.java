package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dinggoapplication.Constants;
import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Leon on 1/3/2015.
 */
public class OngoingDeal extends Activity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoingdeal);

        // Retrieve Merchant Id From savedinstances
        sharedPreferences = getSharedPreferences("MerchantData", Context.MODE_PRIVATE);
        String merchantId = sharedPreferences.getString("merchantId", "");

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        // Set Font for title text
        TextView title = (TextView) findViewById(R.id.actionbar_home_title);

        // Set title text for activity action bar
        title.setText("Ongoing Deals");

        ArrayList<Deal> dealListByMerchant = Constants.dealManager.retrieveDealByMerchant(merchantId);
        final OngoingDealArrayAdapter adapter = new OngoingDealArrayAdapter(this, dealListByMerchant);

        ListView list = (ListView) findViewById(R.id.ongoing_deal_list);
        list.setAdapter(adapter);
    }

    public class OngoingDealArrayAdapter extends ArrayAdapter<Deal> {
        private final Context context;
        private final ArrayList<Deal> values;

        public OngoingDealArrayAdapter(Context context, ArrayList<Deal> values) {
            super(context, R.layout.deal_view_row, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View rowView = inflater.inflate(R.layout.deal_view_row, parent, false);

            Deal deal = values.get(position);
            Merchant merchant = deal.getMerchant();

            ImageView image = (ImageView) rowView.findViewById(R.id.merchantLogo);
            image.setImageBitmap(merchant.getImage());

            TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
            companyName.setText(merchant.getCompanyName());

            TextView merchantType = (TextView) rowView.findViewById(R.id.merchantType);
            merchantType.setText("@ " + merchant.getAddress().getStreetName());

            TextView dealTextView = (TextView) rowView.findViewById(R.id.dealDetail);
            dealTextView.setText(deal.toString());
            dealTextView.setTypeface(dealTextView.getTypeface(), Typeface.BOLD);

            TextView additionInfo = (TextView) rowView.findViewById(R.id.addtionalInfo);
            additionInfo.setText("2h 45m\nLeft");

            return rowView;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
