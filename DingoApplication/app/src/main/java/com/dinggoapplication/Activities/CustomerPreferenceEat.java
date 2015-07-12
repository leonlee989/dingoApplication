/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dinggoapplication.CustomInterface.RangeSeekBar;
import com.dinggoapplication.Manager.PreferencesManager;
import com.dinggoapplication.ObjectSerializer;
import com.dinggoapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within eat settings page
 * <p>
 * Inflated layout that displays the configuration for prioritizing a certain category of deals for eat and drink
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/2/2015.
 */
public class CustomerPreferenceEat extends Activity {
    /** Manager class that handles shared settings */
    PreferencesManager preferenceManager;
    /** Object that deals with caching data in the device per instance */
    SharedPreferences sp;
    /** Adapter that allows customization of rows in the list view */
    CustomAdapter adapter;
    /** Hash of boolean values to track the toggle values in eat settings */
    LinkedHashMap<String,Boolean> eatToggleStateList;
    /** Silder element to allow user to select a range of values */
    RangeSeekBar<Integer> rangeSeekBar;
    /** A list of integer value that contains the budget range for deals */
    ArrayList<Integer> eatBudgetRange;

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
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preference_eat);

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);
        }

        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText("Buy");

        //Instantiate preference manager and shared settings
        preferenceManager = PreferencesManager.getInstance();
        sp = preferenceManager.getSPInstance();

        // Instantiate custom adapter
        ArrayList<String> buyOptions = new ArrayList<>(Arrays.asList("All",
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
            } else {
                eatToggleStateList = new LinkedHashMap<>();
                for (String optionName : buyOptions) {
                    eatToggleStateList.put(optionName, false);
                }
            }
        }

        // Instantiate list view and bind the adapter
        ListView listView = (ListView) findViewById(R.id.eatListView);
        listView.setAdapter(adapter);

        if (rangeSeekBar == null) {
            // Setup the range seek bar from layout
            rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.eatRangeSeekBar);
            if (sp.contains("eatBudget") && sp.getString("eatBudget", "")!=null) {
                try {
                    eatBudgetRange = (ArrayList<Integer>) ObjectSerializer.deserialize(sp.getString("eatBudget", ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                eatBudgetRange = new ArrayList<>();
                //position 0 = min range value
                eatBudgetRange.add(0, 5);
                //position 1 = max range value
                eatBudgetRange.add(1, 100);
            }
        }

        rangeSeekBar.setSelectedMinValue(eatBudgetRange.get(0));
        rangeSeekBar.setSelectedMaxValue(eatBudgetRange.get(1));
        rangeSeekBar.setOnRangeSeekBarChangeListener(
            new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                /**
                 * Event Listener for any change of values in range seek bar
                 * @param bar       RangeSeekBar object
                 * @param minValue  Minimum value for the slider
                 * @param maxValue  Maximum value for the slider
                 */
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    // handle changed range values
                    eatBudgetRange.set(0, minValue);
                    eatBudgetRange.set(1, maxValue);
                }
            }
        );
    }

    /**
     * Adapter class that allows customization of row in the list view
     * <p>
     * Provides flexibility in design
     */
    private class CustomAdapter extends BaseAdapter {
        /** A list of string that contains options for buying */
        ArrayList<String> buyOptionList;
        /** LayoutInflater object for display layout onto the interface */
        private LayoutInflater inflater;

        /** Number of view types available to inflate for the rows */
        private static final int ITEM_VIEW_TYPE_COUNT = 1;

        /**
         * Default constructor to initialize CustomAdapter
         */
        public CustomAdapter() {
            super();
            buyOptionList = new ArrayList<>();
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * Constructor to initialize CustomAdapter with the following parameters
         * @param arrayList A list of string that contains options for buying
         */
        public CustomAdapter(ArrayList<String> arrayList) {
            buyOptionList = arrayList;
            this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return buyOptionList.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return buyOptionList.get(position);
        }

        /** Return the number of view types available to inflate for the rows */
        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Adding a new options for buying
         * @param options   String value that contains the new option for buying
         */
        public void addOption(String options) {
            this.buyOptionList.add(options);
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
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

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in {@link #onCreate} or
     * {@link #onRestoreInstanceState} (the {@link Bundle} populated by this method
     * will be passed to both).
     * <p/>
     * <p>This method is called before an activity may be killed so that when it
     * comes back some time in the future it can restore its state.  For example,
     * if activity B is launched in front of activity A, and at some point activity
     * A is killed to reclaim resources, activity A will have a chance to save the
     * current state of its user interface via this method so that when the user
     * returns to activity A, the state of the user interface can be restored
     * via {@link #onCreate} or {@link #onRestoreInstanceState}.
     * <p/>
     * <p>Do not confuse this method with activity lifecycle callbacks such as
     * {@link #onPause}, which is always called when an activity is being placed
     * in the background or on its way to destruction, or {@link #onStop} which
     * is called before destruction.  One example of when {@link #onPause} and
     * {@link #onStop} is called and not this method is when a user navigates back
     * from activity B to activity A: there is no need to call {link #onSaveInstanceState}
     * on B because that particular instance will never be restored, so the
     * system avoids calling it.  An example when {@link #onPause} is called and
     * not {link #onSaveInstanceState} is when activity B is launched in front of activity A:
     * the system may avoid calling {link #onSaveInstanceState} on activity A if it isn't
     * killed during the lifetime of B since the state of the user interface of
     * A will stay intact.
     * <p/>
     * <p>The default implementation takes care of most of the UI per-instance
     * state for you by calling {@link View#onSaveInstanceState()} on each
     * view in the hierarchy that has an id, and by saving the id of the currently
     * focused view (all of which is restored by the default implementation of
     * {@link #onRestoreInstanceState}).  If you override this method to save additional
     * information not captured by each individual view, you will likely want to
     * call through to the default implementation, otherwise be prepared to save
     * all of the state of each view yourself.
     * <p/>
     * <p>If called, this method will occur before {@link #onStop}.  There are
     * no guarantees about whether it will occur before or after {@link #onPause}.
     *
     * @param state Bundle in which to place your saved state.
     * @see #onCreate
     * @see #onRestoreInstanceState
     * @see #onPause
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        try {
            state.putString("eatToggleState", ObjectSerializer.serialize(eatToggleStateList));
            state.putString("eatBudget", ObjectSerializer.serialize(eatBudgetRange));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p/>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            eatToggleStateList = (LinkedHashMap<String, Boolean>) ObjectSerializer.deserialize(savedInstanceState.getString("eatToggleState"));
            eatBudgetRange = (ArrayList<Integer>) ObjectSerializer.deserialize(savedInstanceState.getString("eatBudget"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when you are no longer visible to the user.  You will next
     * receive either {@link #onRestart}, {@link #onDestroy}, or nothing,
     * depending on later user activity.
     * <p/>
     * <p>Note that this method may never be called, in low memory situations
     * where the system does not have enough memory to keep your activity's
     * process running after its {@link #onPause} method is called.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestart
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onDestroy
     */
    @Override
    protected void onStop() {
        super.onStop();
        //save toggle state to shared settings
        preferenceManager.setValue("eatToggleState", eatToggleStateList);
        preferenceManager.setValue("eatBudget", eatBudgetRange);
    }

    /**
     * Perform any final cleanup before an activity is destroyed.  This can
     * happen either because the activity is finishing (someone called
     * {@link #finish} on it, or because the system is temporarily destroying
     * this instance of the activity to save space.  You can distinguish
     * between these two scenarios with the {@link #isFinishing} method.
     * <p/>
     * <p><em>Note: do not count on this method being called as a place for
     * saving data! For example, if an activity is editing data in a content
     * provider, those edits should be committed in either {@link #onPause} or
     * {@link #onSaveInstanceState}, not here.</em> This method is usually implemented to
     * free resources like threads that are associated with an activity, so
     * that a destroyed activity does not leave such things around while the
     * rest of its application is still running.  There are situations where
     * the system will simply kill the activity's hosting process without
     * calling this method (or any others) in it, so it should not be used to
     * do things that are intended to remain around after the process goes
     * away.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onPause
     * @see #onStop
     * @see #finish
     * @see #isFinishing
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save toggle state to shared settings
        preferenceManager.setValue("eatToggleState", eatToggleStateList);
        preferenceManager.setValue("eatBudget", eatBudgetRange);
    }
}