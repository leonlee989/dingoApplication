/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;

import com.dinggo.R;
import com.dinggo.fragments.AllCompanies;
import com.dinggo.fragments.CustomerViewAll;
import com.dinggo.fragments.CustomerViewMap;
import com.dinggo.managers.CompanyManager;
import com.dinggo.managers.DealManager;
import com.dinggo.widget.SlidingTabLayout;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggo.utilities.LogUtils.LOGD;
import static com.dinggo.utilities.LogUtils.makeLogTag;

/**
 * Activity class that executes activities within Eat and Drink page
 * <p>
 * Inflated layout that display two fragments, one of the fragment is to display a list of deals
 * according to user settings and the other fragment is to display a map view with the available
 * deals around user's proximity
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 10/2/2015.
 */
public class EatDrinkActivity extends BaseActivity implements
        CustomerViewAll.OnDealFragmentInteractionListener,
        CustomerViewMap.OnMapFragmentInteractionListener,
        AllCompanies.OnCompanyFragmentInteractionListener {

    private static final String TAG = makeLogTag(EatDrinkActivity.class);

    /** PagerAdapter that will provide fragments for each of the three primary section of the application */
    AppSectionPagerAdapter mAppSectionPagerAdapter;
    /** the {@link ViewPager} that will display the three primary sections of the application, one at a time */
    ViewPager mViewPager;

    SlidingTabLayout mSlidingTabLayout = null;

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_EAT_AND_DRINK;
    }

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState    Previous saved instance state of this layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_drink);

        Log.d("DataFlow", "Loading cache...");
        new LoadData(this).execute();
        Log.d("DataFlow", "Load cache completed");

        getActionBarToolbar();
    }

    public void inflateFragments() {
        if (mAppSectionPagerAdapter == null) {
            // Create the adapter that will return a fragment for each of the two primary sections
            mAppSectionPagerAdapter = new AppSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext());

            // Set up the viewpager, attaching the adapter and setting up a listener for when the user
            // swipe between the sections
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mAppSectionPagerAdapter);

            mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
            mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

            Resources res = getResources();
            mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.red));
            mSlidingTabLayout.setDistributeEvenly(true);
            mSlidingTabLayout.setViewPager(mViewPager);

            if (mSlidingTabLayout != null) {
                mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                                               int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        //enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
                    }
                });
            }
        }
    }

    /**
     * On shared preference changed in the backend
     * @param sp        Shared Preferences object
     * @param key       Key for the shared preferences
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        LOGD(TAG, "shared preferences key " + key + " changed, maybe reloading data.");
        //TODO repopulate deals according to changed user prefs
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
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
         * @param position  Position of item clicked
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch(position) {
                case 0:
                    fragment = new CustomerViewAll();
                    break;
                case 1:
                    //fragment = new CustomerViewMap();
                    fragment = new AllCompanies();
                    break;
                case 2:
                    fragment = new CustomerViewMap();
                    break;
            }
            return fragment;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 3;
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
     * Fragment interaction from all deals fragment
     *
     * @param id ID of the fragment to be identify in the activity class
     */
    @Override
    public void onMapFragmentInteraction(String id) {
        // Fragment interaction from all deals fragment on map
    }

    /**
     * This method allows interactions with the activity class who implements this listener by its id
     * <p>
     * Fragment interaction from all deals fragment
     *
     * @param id ID of the fragment to be identify in the activity class
     */
    @Override
    public void OnCompanyFragmentInteraction(String id) {
        // Fragment interaction from all company fragment
    }

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.eat_and_drink, menu);
        return true;
    }*/

    public class LoadData extends AsyncTask<Void, Integer, Integer> {
        ContentLoadingProgressBar progress;

        public LoadData(Activity activity) {
            Log.d("DataFlow", "Load data invoked");
            this.progress = (ContentLoadingProgressBar) activity.findViewById(R.id.eat_drink_loading);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Integer doInBackground(Void... params) {
            Log.d("DataFlow", "Do in background");
            CompanyManager companyManager = CompanyManager.getInstance();
            companyManager.updateCacheList();

            DealManager dealManager = DealManager.getInstance();
            return dealManager.updateCacheList().size();
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param dealsSize The size oft the deals
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Integer dealsSize) {
            progress.hide();
            Log.d("DataFlow", dealsSize + " deals are available in cache system");
            inflateFragments();
        }
    }

}