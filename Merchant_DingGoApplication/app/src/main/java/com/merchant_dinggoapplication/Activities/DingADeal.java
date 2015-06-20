/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.merchant_dinggoapplication.Entity.Deal.DealType;
import com.merchant_dinggoapplication.Fragments.Dialogs.CustomLayoutDialogFragment;
import com.merchant_dinggoapplication.Fragments.Dialogs.DialogListener;
import com.merchant_dinggoapplication.Fragments.Dialogs.DingConfirmationDialogFragment;
import com.merchant_dinggoapplication.Fragments.Dialogs.DiscountDialogFragment;
import com.merchant_dinggoapplication.Fragments.Dialogs.ListDialogFragment;
import com.merchant_dinggoapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within DingADeal Page
 * <p>
 * Inflated layout that allow merchant to create a deal for customer to ding
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 1.5
 * Created by Leon on 02/28/2015.
 */
public class DingADeal extends Activity implements DialogListener {

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_adeal);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the Action bar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_merchant_landing_custom);
        }

        // Set image for activity action bar
        ImageView image = (ImageView) findViewById(R.id.merchant_actionbar_icon);
        image.setImageResource(R.mipmap.bell);

        // Set title for the action bar title
        TextView title = (TextView) findViewById(R.id.merchant_actionbar_title);
        title.setText(getResources().getString(R.string.company_name));

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

    /**
     * Method to call upon to set up the option available for ding a deal
     * @return  Custom DinADealAdapter to populate the list view
     */
    public CustomDingADealAdapter initializeOptions() {

        CustomDingADealAdapter adapter = new CustomDingADealAdapter();

        DingADealOptions option1 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_1),
                RowType.SELECTOR, "Choose Who To Ding To");
        adapter.addOption(option1);
        DingADealOptions option2 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_2),
                RowType.SELECTOR, "Choose A Discount Type");
        adapter.addOption(option2);
        DingADealOptions option3 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_3),
                RowType.SELECTOR, "# Of Seats To Allocate");
        adapter.addOption(option3);
        DingADealOptions option4 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_4),
                RowType.SELECTOR, "Ding To Be Use By");
        adapter.addOption(option4);
        DingADealOptions option5 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_5),
                RowType.SELECTOR, "Ding To Be End By");
        adapter.addOption(option5);
        DingADealOptions option6 = new DingADealOptions(getResources().getString(R.string.dingadeal_optionText_6),
                RowType.TOGGLE, "Select A Deal");
        adapter.addOption(option6);

        return adapter;
    }

    AdapterView.OnItemClickListener itemActions = new AdapterView.OnItemClickListener() {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p/>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent   The AdapterView where the click happened.
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    CharSequence[] valueList = new CharSequence[]{"Everyone", "Nearby Users", "Members", "Custom"};
                    ListDialogFragment listDialogFragment = ListDialogFragment.newInstance("Assign To", valueList, position);

                    listDialogFragment.show(getFragmentManager(), "Assign");
                    break;
                case 1:

                    HashMap<CharSequence, DealType> valueList2 =
                            new HashMap<>();
                    valueList2.put("Tiered Discount", DealType.TIER_DISCOUNT);
                    valueList2.put("Percentage Discount", DealType.PERCENTAGE_DISCOUNT);

                    DiscountDialogFragment discount_listDialogFragment =
                            DiscountDialogFragment.newInstance("Select an option", valueList2, position);

                    discount_listDialogFragment.show(getFragmentManager(), "Discount");

                    break;
                case 2:

                    CustomLayoutDialogFragment maxSeatDialog =
                            CustomLayoutDialogFragment.newInstance(R.layout.dialog_number_picker,
                                    "Select a value",
                                    getResources().getString(R.string.maxSeatText1),
                                    getResources().getString(R.string.maxSeatText2), 100, 1, position);

                    maxSeatDialog.show(getFragmentManager(), "maxSeat");
                    break;
                case 3:

                    CustomLayoutDialogFragment endTimeDialog = CustomLayoutDialogFragment
                            .newInstance(R.layout.dialog_time_picker, "Select a time",
                                    getResources().getString(R.string.endText), position);

                    endTimeDialog.show(getFragmentManager(), "endTime");
                    break;
                case 4:

                    CustomLayoutDialogFragment useByTimeDialog = CustomLayoutDialogFragment
                            .newInstance(R.layout.dialog_time_picker, "Select a time",
                                    getResources().getString(R.string.useByText), position);

                    useByTimeDialog.show(getFragmentManager(), "useByTime");
                    break;
                case 5:

                    CharSequence[] dealList = {"Normal Deal", "Seasonal Deal", "Mystery Deal"};
                    ListDialogFragment useDeal = ListDialogFragment.newInstance("Select a deal",
                            dealList, position);

                    useDeal.show(getFragmentManager(), "DealType");
                    break;
                default:

                    TextView text = (TextView) view.findViewById(R.id.option_label);
                    Toast.makeText(DingADeal.this, text.getText(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    /**
     * Display confirmation dialog for merchant to have final confirmation on the deal to be released
     * @param view  View to display as confirmation dialog
     */
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
         * Maximum count of option types available
         */
        private static final int TYPE_MAX_COUNT = 2;

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
            this.optionList = new ArrayList<>();
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * Constructor to initialize CustomDingADealAdapter
         * @param optionList    A list of options
         */
        public CustomDingADealAdapter(ArrayList<DingADealOptions> optionList) {
            super();
            this.optionList = optionList;
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * Adding a addition option into the option list
         * @param options   Options to select
         */
        public void addOption(DingADealOptions options) {
            this.optionList.add(options);
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        public RowType getViewType(int position) {
            // Return 0 if row type is seletor, and 1 if row type is otherwise
            return optionList.get(position).getValue();
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
            RowType option = getViewType(position);

            if (rowView == null) {
                // Switch the row type layout according to the type item
                switch (option) {
                    case SELECTOR:
                        rowView = inflater.inflate(R.layout.dingadel_selection_row, parent, false);
                        break;
                    case TOGGLE:
                        rowView = inflater.inflate(R.layout.dingadeal_toggle_row, parent, false);
                        break;
                }
            }

            TextView option_label = (TextView) rowView.findViewById(R.id.option_label);
            DingADealOptions options = optionList.get(position);
            option_label.setHint(options.getHintText());
            option_label.setText(options.getOptionName());

            return rowView;
        }
    }

    /**
     * If merchant provide a positive response
     * @param dialogFragment    Dialog fragment to display
     * @param position          The position of the selection
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, int position) {
        // Retrieve the adapter from the list view in this fragment
        ListView lv = (ListView) findViewById(R.id.dingadeal_List);
        CustomDingADealAdapter adapter = (CustomDingADealAdapter) lv.getAdapter();

        // Retrieve the row view in the listview being selected by user
        DingADealOptions options = (DingADealOptions) adapter.getItem(position);

        // If dialog fragment is a custom dialog fragment
        if (dialogFragment instanceof CustomLayoutDialogFragment) {
            // Update the new text according to user selection
            options.setOptionName(customDialogUserInput(dialogFragment));

        } else if (dialogFragment instanceof ListDialogFragment) {
            options.setOptionName("" + dialogFragment.getArguments().getCharSequence("selection"));
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * Format user input into string format
     * @param dialogFragment    Dialog fragment to display
     * @return                  A String value to display user's input
     */
    public String customDialogUserInput(DialogFragment dialogFragment) {
        int resId = dialogFragment.getArguments().getInt("resId");
        Dialog dialog = dialogFragment.getDialog();

        if (resId == R.layout.dialog_number_picker) {

            NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberpicker);

            TextView text1 = (TextView) dialog.findViewById(R.id.dialog_text_1);
            TextView text2 = (TextView) dialog.findViewById(R.id.dialog_text_2);

            return "" +  text1.getText() + np.getValue() + text2.getText();

        } else if (resId == R.layout.dialog_number_picker_2) {

            NumberPicker np1 = (NumberPicker) dialog.findViewById(R.id.numberpicker2_1);
            NumberPicker np2 = (NumberPicker) dialog.findViewById(R.id.numberpicker2_2);

            TextView text1 = (TextView) dialog.findViewById(R.id.dialog_text_1);
            TextView text2 = (TextView) dialog.findViewById(R.id.dialog_text_2);
            TextView text3 = (TextView) dialog.findViewById(R.id.dialog_text_3);

            return "" + text1.getText() + np1.getValue() + text2.getText() + np2.getValue() + text3.getText();

        } else if (resId == R.layout.dialog_time_picker) {
            TimePicker tp = (TimePicker) dialog.findViewById(R.id.timePicker);
            TextView text1 = (TextView) dialog.findViewById(R.id.time_dialog);

            return "" + text1.getText() + tp.getCurrentHour().toString() + "h " + tp.getCurrentMinute().toString() + "m";
        }

        return "";
    }

    /**
     * If merchant provide a negative response
     * @param dialog        Dialog fragment to display
     * @param position      Position of the merchant's selection
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog, int position) {
        dialog.getDialog().cancel();
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
         * String to display text as hint
         */
        String hintText;

        /**
         * Constructor to initialize a DingADealOption Object with the following parameters
         * @param optionName    Value to diplay as title
         * @param value         Type of value to display in the list view
         * @param hintText      Value to display as hint text
         */
        public DingADealOptions(String optionName, RowType value, String hintText) {
            this.optionName = optionName;
            this.value = value;
            this.hintText = hintText;
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
         * @param value     Type of value to display in the list view
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
         * @param optionName    String value tha contains text to be display as title
         */
        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        /**
         * Get the hint text to display
         * @return      String value that contain text that is display as hint text
         */
        public String getHintText() {
            return hintText;
        }

        /**
         * Set the hint text to display
         * @param hintText  String value that contain text that is display as hint text
         */
        public void setHintText(String hintText) {
            this.hintText = hintText;
        }
    }

    /**
     * The row type available for deal option
     */
    public enum RowType {
        /** View type that is selectable */
        SELECTOR,
        /** View type that cater to toggle */
        TOGGLE
    }

    /**
     * Attachment of base context to change the font for the activity
     * @param newBase   Base context for customization
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
