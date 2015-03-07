package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dinggoapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferenceEat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preference_eat);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText("Buy");

        // Instantiate custom adapter
        ArrayList<String> buyOptions = new ArrayList<String>(Arrays.asList("All",
                "Chinese", "French", "Fusion", "Halal", "Indonesian", "Italian",
                "Japanese", "Korean", "Thai", "Seafood", "Others"));
        CustomAdapter adapter =  new CustomAdapter(buyOptions);

        // Instantiate list view and bind the adapter
        ListView listView = (ListView) findViewById(R.id.eatListView);
        listView.setAdapter(adapter);
    }

    private class CustomAdapter extends BaseAdapter {

        ArrayList<String> buyOptionList;
        private LayoutInflater inflater;

        //Types of view that need to be handled
        private static final int ITEM_VIEW_TYPE_COUNT = 1;

        public CustomAdapter() {
            super();
            buyOptionList = new ArrayList<String>();
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CustomAdapter(ArrayList<String> arrayList) {
            buyOptionList = arrayList;
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return buyOptionList.size();
        }

        @Override
        public Object getItem(int position) {
            return buyOptionList.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addOption(String options) {
            this.buyOptionList.add(options);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view==null){
                view = inflater.inflate(R.layout.preferences_eat_toggle_row, parent, false);
            }

            TextView buyToggleTV =  (TextView) view.findViewById(R.id.eatToggleTV);
            buyToggleTV.setText(buyOptionList.get(position));
            return view;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
