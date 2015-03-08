package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.Constants;
import com.dinggoapplication.Manager.PreferencesManager;
import com.dinggoapplication.ObjectSerializer;
import com.dinggoapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferences extends Activity {

    PreferencesManager preferenceManager;
    SharedPreferences sp;
    CustomAdapter adapter;
    LinkedHashMap<String,Boolean> toggleStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preferences);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText("Preferences");

        //Instantiate preference manager and shared preferences
        preferenceManager = Constants.preferencesManager.getInstance();
        sp = preferenceManager.getSPInstance();

        // Instantiate custom adapter
        if (adapter == null) {
            adapter = initializeOptions();
            if (sp.contains("dealTypeToggleState")) {
                try {
                    toggleStateList = (LinkedHashMap<String, Boolean>) ObjectSerializer.deserialize(sp.getString("dealTypeToggleState", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Instantiate list view and bind the adapter
        ListView listView = (ListView) findViewById(R.id.preferencesListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemActions);

    }
    AdapterView.OnItemClickListener itemActions = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch(position) {
                case 6:
                    Intent intent = new Intent(CustomerPreferences.this, CustomerPreferenceManageNotifications.class);
                    startActivity(intent);
                    break;
                case 7:
                    Intent eat = new Intent(CustomerPreferences.this, CustomerPreferenceEat.class);
                    startActivity(eat);
                    break;
                default:
                    TextView text = (TextView) view.findViewById(R.id.pOptionLabel);
                    Toast.makeText(CustomerPreferences.this, text.getText(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public CustomAdapter initializeOptions() {
        CustomAdapter adapter = new CustomAdapter();
        toggleStateList = new LinkedHashMap<String, Boolean>();

        adapter.addOption(new PreferenceItem("Profile", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Edit Profile", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Change Password", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Clear Search History", RowType.SELECTOR));

        adapter.addOption(new PreferenceItem("Deals", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Manage Notifications", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Type of Deals", RowType.SUBHEADER));
        adapter.addOption(new PreferenceItem("Eat", false));
        adapter.addOption(new PreferenceItem("Drink", false));
        adapter.addOption(new PreferenceItem("Watch", false));
        adapter.addOption(new PreferenceItem("Play", false));
        adapter.addOption(new PreferenceItem("Buy", false));

        toggleStateList.put("Eat", false);
        toggleStateList.put("Drink", false);
        toggleStateList.put("Watch", false);
        toggleStateList.put("Play", false);
        toggleStateList.put("Buy", false);

        adapter.addOption(new PreferenceItem("Support", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Feedback", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Report a Problem", RowType.SELECTOR));

        adapter.addOption(new PreferenceItem("About", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Privacy Policy", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Terms of Service", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Open Source Libraries", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Logout", RowType.SELECTOR));
        return adapter;
    }

    private class CustomAdapter extends BaseAdapter {

        ArrayList<PreferenceItem> preferencesList;
        private LayoutInflater inflater;

        //Types of view that need to be handled
        private static final int ITEM_VIEW_TYPE_COUNT = 4;

        public CustomAdapter() {
            super();
            preferencesList = new ArrayList<PreferenceItem>();
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CustomAdapter(ArrayList<PreferenceItem> arrayList) {
            preferencesList = arrayList;
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return preferencesList.size();
        }

        @Override
        public Object getItem(int position) {
            return preferencesList.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addOption(PreferenceItem options) {
            this.preferencesList.add(options);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Switch toggle = null;
            PreferenceItem item = preferencesList.get(position);
            final String optionName = item.getOptionName();

                switch(item.getValue()) {
                    case HEADER:
                        view = inflater.inflate(R.layout.preferences_header, parent, false);
                        view.setOnClickListener(null);
                        break;
                    case SUBHEADER:
                        view = inflater.inflate(R.layout.preferences_subheader, parent, false);
                        view.setOnClickListener(null);
                        break;
                    case SELECTOR:
                        view = inflater.inflate(R.layout.preferences_single_row, parent, false);
                        break;
                    case TOGGLE:
                        view = inflater.inflate(R.layout.preferences_toggle_row, parent, false);
                        toggle = (Switch) view.findViewById(R.id.ptoggle);
                        toggle.setChecked(toggleStateList.get(optionName));
                        break;
                }

            TextView option_label =  (TextView) view.findViewById(R.id.pOptionLabel);
            option_label.setText(optionName);

            if (toggle !=null) {
                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        toggleStateList.put(optionName, isChecked);
                    }
                });
            }
            return view;
        }
    }


    /**
     * PreferenceItem Object to represents a preference option
     */
    public class PreferenceItem{
        String optionName;
        RowType value;
        Boolean state;

        public PreferenceItem(String optionName, RowType value) {
            this.optionName = optionName;
            this.value = value;
        }

        public PreferenceItem(String optionName, Boolean state) {
            this.optionName = optionName;
            this.value = RowType.TOGGLE;
            this.state = state;
        }

        public RowType getValue() {
            return value;
        }

        /**
         * Set the row type of the PreferenceItem option
         * @param value
         */
        public void setValue(RowType value) {
            this.value = value;
        }

        /**
         * Get the option's text for the PreferenceItem option
         * @return optionName
         */
        public String getOptionName() {
            return optionName;
        }

        /**
         * Set the option's text for the PreferenceItem option
         * @param optionName
         */
        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }
    }

    /**
     * The row type available for deal option
     */
    public enum RowType {
       HEADER, SUBHEADER, SELECTOR, TOGGLE, ;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        try {
            state.putString("dealTypeToggleState", ObjectSerializer.serialize(toggleStateList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            toggleStateList = (LinkedHashMap<String, Boolean>) ObjectSerializer.deserialize(savedInstanceState.getString("dealTypeToggleState"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //save toggle state to shared preferences
        preferenceManager.setValue("dealTypeToggleState", toggleStateList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save toggle state to shared preferences
        preferenceManager.setValue("dealTypeToggleState", toggleStateList);
    }
}
