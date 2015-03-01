package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;;

import com.dinggoapplication.Fragments.Dialogs.DingConfirmationDialogFragment;
import com.dinggoapplication.Fragments.Dialogs.ListDialogFragment;
import com.dinggoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Ding A Deal object
 *  Created by Leon on 02/28/2015.
 */
public class DingADeal extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_adeal);

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

        // Initialize options available
        CustomDingADealAdapter adapter =  initializeOptions();

        // Set list view to the adapter
        ListView list = (ListView) findViewById(R.id.dingadeal_List);
        list.setAdapter(adapter);

        list.setOnItemClickListener(itemActions);
    }

    AdapterView.OnItemClickListener itemActions = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    CharSequence[] valueList = new CharSequence[]{"Everyone", "Members", "Special"};
                    ListDialogFragment listDialogFragment = ListDialogFragment.newInstance("Assign To", valueList);

                    listDialogFragment.show(getFragmentManager(), "Assign");
                    break;
                default:
                    TextView text = (TextView) view.findViewById(R.id.option_label);
                    Toast.makeText(DingADeal.this, text.getText(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    /**
     * Method to call upon to set up the option available for ding a deal
     * @return
     */
    public CustomDingADealAdapter initializeOptions() {

        CustomDingADealAdapter adapter = new CustomDingADealAdapter();

        DingADealOptions option1 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_1), RowType.SELECTOR);
        adapter.addOption(option1);
        DingADealOptions option2 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_2), RowType.SELECTOR);
        adapter.addOption(option2);
        DingADealOptions option3 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_3), RowType.SELECTOR);
        adapter.addOption(option3);
        DingADealOptions option4 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_4), RowType.SELECTOR);
        adapter.addOption(option4);
        DingADealOptions option5 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_5), RowType.SELECTOR);
        adapter.addOption(option5);
        DingADealOptions option6 = new DingADealOptions(getResources()
                .getString(R.string.dingadeal_optionText_6), RowType.TOGGLE);
        adapter.addOption(option6);

        return adapter;
    }

    public void dingGo(View view) {
        // Display dialog box
        DingConfirmationDialogFragment dialogFragment = new DingConfirmationDialogFragment();
        dialogFragment.show(getFragmentManager(), "Alert");
    }

    /**
     * Custom adapter extended to BaseAdapter to display different row layout in a layout
     */
    public class CustomDingADealAdapter extends BaseAdapter {
        /**
         * Type item 1 index
         */
        private static final int TYPE_ITEM_1 = 0;
        /**
         * Type item last index
         */
        private static final int TYPE_ITEM_FINAL = 1;
        /**
         * Maximum count of option types available
         */
        private static final int TYPE_MAX_COUNT = TYPE_ITEM_FINAL + 1;

        /**
         * A list of options available for selection
         */
        ArrayList<DingADealOptions> optionList;
        /**
         * Layout inflater to inflate a row layout
         */
        private LayoutInflater inflater;

        /**
         * Constructor to initialize CustomDingADealAdapter
         */
        public CustomDingADealAdapter() {
            super();
            this.optionList = new ArrayList<DingADealOptions>();
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * Constructor to initialize CustomDingADealAdapter
         * @param optionList
         */
        public CustomDingADealAdapter(ArrayList<DingADealOptions> optionList) {
            super();
            this.optionList = optionList;
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * Adding a addition option into the option list
         * @param options
         */
        public void addOption(DingADealOptions options) {
            this.optionList.add(options);
        }

        @Override
        public int getViewTypeCount() {
            return this.TYPE_MAX_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            // Return 0 if row type is seletor, and 1 if row type is otherwise
            return optionList.get(position).getValue() == RowType.SELECTOR ? TYPE_ITEM_1 : TYPE_ITEM_FINAL;
        }

        @Override
        public int getCount() {
            return optionList.size();
        }

        @Override
        public Object getItem(int position) {
            return optionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            int option = getItemViewType(position);

            if (rowView == null) {
                // Switch the row type layout according to the type item
                switch (option) {
                    case TYPE_ITEM_1:
                        rowView = inflater.inflate(R.layout.dingadel_selection_row, parent, false);
                        break;
                    case TYPE_ITEM_FINAL:
                        rowView = inflater.inflate(R.layout.dingadeal_toggle_row, parent, false);
                        break;
                }
            }

            TextView option_label = (TextView) rowView.findViewById(R.id.option_label);
            option_label.setText(optionList.get(position).getOptionName());

            return rowView;
        }
    }

    /**
     * DingADealOption Object to represents a deal option
     */
    public class DingADealOptions {
        /**
         * Option's text
         */
        String optionName;
        /**
         * The row type for the corresponding option
         */
        RowType value;

        /**
         * Constructor to initialize a DingADealOption Object
         * @param optionName
         * @param value
         */
        public DingADealOptions(String optionName, RowType value) {
            this.optionName = optionName;
            this.value = value;
        }

        /**
         * Get the row type for the deal option
         * @return value
         */
        public RowType getValue() {
            return value;
        }

        /**
         * Set the row type of the deal option
         * @param value
         */
        public void setValue(RowType value) {
            this.value = value;
        }

        /**
         * Get the option's text for the deal option
         * @return optionName
         */
        public String getOptionName() {
            return optionName;
        }

        /**
         * Set the option's text for the deal option
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
        SELECTOR, TOGGLE;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
