package com.example.leon.dingoapplication.Activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leon.dingoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MerchantActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Assign option text for merchant to an array of string
        final int noOfOption = 4;
        String[] values = new String[noOfOption];
        for (int i=0; i < noOfOption; i++) {
            String resName = "merchant_optionText_" + (i+1);
            int resId = getResources().getIdentifier(resName, "string", getPackageName());

            values[i] = getResources().getString(resId);
        }

        final MerchantArrayAdapter adapter = new MerchantArrayAdapter(this, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Action for individual actions
        switch(position) {
            case 0:
                Intent intent = new Intent(MerchantActivity.this, DingADeal.class);
                startActivity(intent);
                break;
            default:
                String item = (String) getListAdapter().getItem(position);
                Toast.makeText(MerchantActivity.this, item + " selected", Toast.LENGTH_LONG).show();
        }
    }

    public class MerchantArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MerchantArrayAdapter(Context context, String[] values) {
            super(context, R.layout.landing_row, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.landing_row, parent, false);

            //Adjust resolution
            RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.rowId);
            Display display = getWindowManager().getDefaultDisplay();
            layout.getLayoutParams().height = (display.getHeight() - 55*4)/4;

            // Set label for different options
            TextView label = (TextView) rowView.findViewById(R.id.landing_label);

            //Set Value for label
            label.setText(values[position]);

            //Changing to the different icons
            ImageView imageIcon = (ImageView) rowView.findViewById(R.id.landing_icon);
            switch(position) {
                case 0:
                    imageIcon.setImageResource(R.drawable.ding);
                    break;
                case 1:
                    imageIcon.setImageResource(R.drawable.ongoing);
                    break;
                case 2:
                    imageIcon.setImageResource(R.drawable.statistic);
                    break;
                case 3:
                    imageIcon.setImageResource(R.drawable.preferences);
                    break;
            }
            return rowView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}