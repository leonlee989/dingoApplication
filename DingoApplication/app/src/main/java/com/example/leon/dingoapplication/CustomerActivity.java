package com.example.leon.dingoapplication;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        // Set title text for activity
        TextView title = (TextView) findViewById(R.id.customer_home_title);
        title.setText(getResources().getString(R.string.app_name));

        /**
         * Default setting of values for list view
         * TO BE REMOVED
         */
        String[] values = new String[] { "Eat & Drink", "Watch", "Play",
                "Buy", "Dinged", "Preferences"};

        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(CustomerActivity.this, item + " selected", Toast.LENGTH_LONG).show();
    }
    /**
     * Demonstration for ArrayAdapter by default
     */
    private class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.customer_landing_row, values);
            this.context = context;
            this.values = values;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.customer_landing_row, parent, false);

            TextView label = (TextView) rowView.findViewById(R.id.landing_label);
            ImageView imageIcon = (ImageView) rowView.findViewById(R.id.landing_icon);

            String s = values[position];

            label.setText(s);
            if (s.startsWith("Watch") || s.startsWith("Dinged")) {
                imageIcon.setImageResource(R.drawable.minus);
            } else {
                imageIcon.setImageResource(R.drawable.plus);
            }

            return rowView;
        }
    }
}
