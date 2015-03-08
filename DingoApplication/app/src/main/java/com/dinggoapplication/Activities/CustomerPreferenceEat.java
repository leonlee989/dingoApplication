package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dinggoapplication.Constants;
import com.dinggoapplication.Manager.PreferencesManager;
import com.dinggoapplication.ObjectSerializer;
import com.dinggoapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferenceEat extends Activity {

    PreferencesManager preferenceManager;
    SharedPreferences sp;
    CustomAdapter adapter;
    LinkedHashMap<String,Boolean> eatToggleStateList;

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

        //Instantiate preference manager and shared preferences
        preferenceManager = Constants.preferencesManager.getInstance();
        sp = preferenceManager.getSPInstance();

        // Instantiate custom adapter
        ArrayList<String> buyOptions = new ArrayList<String>(Arrays.asList("All",
                "Chinese", "French", "Fusion", "Halal", "Indonesian", "Italian",
                "Japanese", "Korean", "Thai", "Seafood", "Others"));

        // Instantiate custom adapter
        if (adapter == null) {
            adapter = new CustomAdapter(buyOptions);
            if (sp.contains("eatToggleState")) {
                try {
                    eatToggleStateList = (LinkedHashMap<String, Boolean>) ObjectSerializer.deserialize(sp.getString("eatToggleState", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                eatToggleStateList = new LinkedHashMap<String, Boolean>();
                for (String optionName: buyOptions) {
                    eatToggleStateList.put(optionName, false);
                }
            }
        }

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
            final String optionName = buyOptionList.get(position);

            view = inflater.inflate(R.layout.preferences_eat_toggle_row, parent, false);

            Switch toggle = (Switch) view.findViewById(R.id.eatToggle);
            toggle.setChecked(eatToggleStateList.get(optionName));

            TextView buyToggleTV =  (TextView) view.findViewById(R.id.eatToggleTV);
            buyToggleTV.setText(optionName);

            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    eatToggleStateList.put(optionName, isChecked);
                }
            });


            return view;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        try {
            state.putString("eatToggleState", ObjectSerializer.serialize(eatToggleStateList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            eatToggleStateList = (LinkedHashMap<String, Boolean>) ObjectSerializer.deserialize(savedInstanceState.getString("eatToggleState"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //save toggle state to shared preferences
        preferenceManager.setValue("eatToggleState", eatToggleStateList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save toggle state to shared preferences
        preferenceManager.setValue("eatToggleState", eatToggleStateList);
    }

}
