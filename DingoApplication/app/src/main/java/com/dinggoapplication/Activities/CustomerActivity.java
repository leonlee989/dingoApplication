package com.dinggoapplication.Activities;

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

import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_landing_custom);

        // Set image for activity action bar
        ImageView image = (ImageView) findViewById(R.id.actionbar_icon);
        image.setImageResource(R.drawable.bell);

        // Set Font for title text
        TextView title = (TextView) findViewById(R.id.actionbar_title);

        // Set title text for activity action bar
        title.setText(getResources().getString(R.string.app_name));

        // Menu context to be display on customer landing page
        final int noOfOptions = 6;
        String[] values = new String[noOfOptions];
        for (int i=0; i < noOfOptions; ++i) {
            String resName = "customer_optionText_" + (i+1);
            int resId = getResources().getIdentifier(resName, "string", getPackageName());

            values[i] = getResources().getString(resId);
        }

        final CustomerArrayAdapter adapter = new CustomerArrayAdapter(this, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Actions for individual actions
        switch (position) {
            case 0:
                Intent intent = new Intent(CustomerActivity.this, EatDrinkActivity.class);
                startActivity(intent);
                break;
            default:
                String item = (String) getListAdapter().getItem(position);
                Toast.makeText(CustomerActivity.this, item + " selected", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * ArrayAdapter class to display rows of option for customer landing page
     */
    private class CustomerArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public CustomerArrayAdapter(Context context, String[] values) {
            super(context, R.layout.landing_row, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.landing_row, parent, false);

            // Adjust resolution
            RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.rowId);
            Display display = getWindowManager().getDefaultDisplay();
            layout.getLayoutParams().height = (display.getHeight() - 40*6) /6;

            // Set label for different options
            TextView label = (TextView) rowView.findViewById(R.id.landing_label);

            // Set value for label
            label.setText(values[position]);

            // Changing to the different icons
            ImageView imageIcon = (ImageView) rowView.findViewById(R.id.landing_icon);
            switch (position) {
                case 0:
                    imageIcon.setImageResource(R.drawable.meal);
                    break;
                case 1:
                    imageIcon.setImageResource(R.drawable.watch);
                    break;
                case 2:
                    imageIcon.setImageResource(R.drawable.play);
                    break;
                case 3:
                    imageIcon.setImageResource(R.drawable.buy);
                    break;
                case 4:
                    imageIcon.setImageResource(R.drawable.ding);
                    break;
                case 5:
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
