package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerPreferences extends Activity {

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

        // Instantiate custom adapter
        CustomAdapter adapter =  initializeOptions();

        // Instantiate list view and bind the adapter
        ListView listView = (ListView) findViewById(R.id.preferencesListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemActions);
    }
    AdapterView.OnItemClickListener itemActions = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position) {
                case 7:
                    TextView eat = (TextView) view.findViewById(R.id.pOptionLabel);
                    Toast.makeText(CustomerPreferences.this, eat.getText(), Toast.LENGTH_SHORT).show();
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

        adapter.addOption(new PreferenceItem("Profile", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Edit Profile", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Change Password", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Clear Search History", RowType.SELECTOR));

        adapter.addOption(new PreferenceItem("Deals", RowType.HEADER));
        adapter.addOption(new PreferenceItem("Manage Notifications", RowType.SELECTOR));
        adapter.addOption(new PreferenceItem("Type of Deals", RowType.SUBHEADER));
        adapter.addOption(new PreferenceItem("Eat", RowType.TOGGLE));
        adapter.addOption(new PreferenceItem("Drink", RowType.TOGGLE));
        adapter.addOption(new PreferenceItem("Watch", RowType.TOGGLE));
        adapter.addOption(new PreferenceItem("Play", RowType.TOGGLE));
        adapter.addOption(new PreferenceItem("Buy", RowType.TOGGLE));

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
            PreferenceItem item = preferencesList.get(position);

            //if (view == null) {
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
                        break;
                }
            //}
            TextView option_label =  (TextView) view.findViewById(R.id.pOptionLabel);
            option_label.setText(preferencesList.get(position).getOptionName());
            return view;
        }
    }


    /**
     * PreferenceItem Object to represents a preference option
     */
    public class PreferenceItem{
        String optionName;
        RowType value;

        public PreferenceItem(String optionName, RowType value) {
            this.optionName = optionName;
            this.value = value;
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

}
