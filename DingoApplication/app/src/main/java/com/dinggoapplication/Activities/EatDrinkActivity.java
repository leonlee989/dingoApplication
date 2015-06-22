/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.Fragments.CustomerViewAll;
import com.dinggoapplication.Fragments.CustomerViewMap;
import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within Eat and Drink page
 * <p>
 * Inflated layout that display two fragments, one of the fragment is to display a list of deals
 * according to user preferences and the other fragment is to display a map view with the available
 * deals around user's proximity
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 10/2/2015.
 */
public class EatDrinkActivity extends FragmentActivity implements ActionBar.TabListener,
        CustomerViewAll.OnDealFragmentInteractionListener,
        CustomerViewMap.OnMapFragmentInteractionListener,
        View.OnClickListener {

    /** PagerAdapter that will provide fragments for each of the three primary section of the application */
    AppSectionPagerAdapter mAppSectionPagerAdapter;

    /** the {@link ViewPager} that will display the three primary sections of the application, one at a time */
    ViewPager mViewPager;

    /** Context variable to store resources */
    Context mContext;

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState    Previous saved instance state of this layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_drink);

        // Create the adapter that will return a fragment for each of the three primary sections
        mAppSectionPagerAdapter = new AppSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Customized title as TextView in the ActionBar
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_custom);

            // Specify that the home/up button should not be enables since there is no hierarchical parent
            actionBar.setHomeButtonEnabled(false);

            // Specify to display tabs in the action bar
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        // Set title text for activity action bar
        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText(getResources().getString(R.string.customer_optionText_1));

        //Set up the viewpager, attaching the adapter and setting up a listener for when the user
        // swipe between the sections
        mViewPager = (ViewPager) findViewById(R.id.customer_home_pager);
        mViewPager.setAdapter(mAppSectionPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * Upon selection of a tab by the user
             * @param position  The position of the tab user selected
             */
            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    // When swiping between different sections, select the corresponding tabs.
                    // We can also use ActionBar.Tab#Select() to do this if we have a reference to the tab
                    actionBar.setSelectedNavigationItem(position);
                }
            }
        });

        if (actionBar != null) {
            // For each of the section, add a tab to the action bar
            for (int i = 0; i < mAppSectionPagerAdapter.getCount(); i++) {

                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionPagerAdapter.getPageTitle(i))
                                .setTabListener(this));

            }
        }
        mViewPager.setCurrentItem(1);
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            during a tab switch. This tab's unselect and the newly selected tab's select
     *            will be executed in a single transaction. This FragmentTransaction does not
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Actions when tab is being unselected
    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            during a tab switch. The previous tab's unselect and this tab's select will be
     *            executed in a single transaction. This FragmentTransaction does not support
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // When the given tab is selected, switch to the corresponding page in the view pager
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Called when a tab that is already selected is chosen again by the user.
     * Some applications may use this action to return to the top level of a category.
     *
     * @param tab The tab that was reselected.
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            once this method returns. This FragmentTransaction does not support
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Actions when the tab is reselected
    }

    /**
     * A @link FragmentPagerAdapter that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionPagerAdapter extends FragmentStatePagerAdapter {
        /** Context variable to store resources */
        Context mContext;

        /**
         * Constructor to initialize AppSectionPagerAdapter object with the following parameters
         * @param fm        Manager class that handles fragment objects
         * @param context   Context variable to store resources
         */
        public AppSectionPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new CustomerViewAll();
                case 1:
                    return new CustomerViewMap();
                default:
                    Fragment fragment = new PlaceholderFragment();
                    Bundle args = new Bundle();
                    args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, position + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 2;
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            // Dynamic return of title name according to the tab number from string.xml
            String tabString = "eatDrink_tabText_" + (position + 1);
            int resID = mContext.getResources()
                    .getIdentifier(tabString, "string", mContext.getPackageName());
            return mContext.getResources().getString(resID);
        }
    }

    /**
     * A placeholder fragment containing default view in a section.
     * To be removed
     * @deprecated
     */
    public static class PlaceholderFragment extends Fragment {
        /** Text display on the tab */
        public static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null (which
         * is the default implementation).  This will be called between
         * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
         * <p/>
         * <p>If you return a View from here, you will later be called in
         * {@link #onDestroyView} when the view is being released.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate
         *                           any views in the fragment,
         * @param container          If non-null, this is the parent view that the fragment's
         *                           UI should be attached to.  The fragment should not add the view itself,
         *                           but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Bundle args = getArguments();

            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.display_fragment, args.getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    /**
     * This method allows interactions with the activity class who implements this listener by its id
     * <p>
     * Fragment interaction from all deals fragment
     *
     * @param id ID of the fragment to be identify in the activity class
     */
    @Override
    public void onDealFragmentInteraction(String id) {
        // Fragment interaction from all deals fragment
    }

    /**
     * This method allows interactions with the activity class who implements this listener by its id
     * <p>
     * Fragment interaction from Map Fragment
     */
    @Override
    public void onMapFragmentInteraction() {
        // Fragment interaction from Map Fragment
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // For advanced filtering feature in Map Fragment
        Toast.makeText(this, "Advanced Filtering", Toast.LENGTH_LONG).show();
    }

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}