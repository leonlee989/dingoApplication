package com.example.leon.dingoapplication.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leon.dingoapplication.Fragments.CustomerViewAll;
import com.example.leon.dingoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class EatDrinkActivity extends FragmentActivity implements ActionBar.TabListener,
        CustomerViewAll.OnFragmentInteractionListener {

    /*
    PagerAdapter that will provide fragments for each of the three primary section of the application
     */
    AppSectionPagerAdapter mAppSectionPagerAdapter;

    /*
    the {@link ViewPager} that will display the three primary sections of the application, one at a time
     */
    ViewPager mViewPager;

    /*
    Context variable to store resources
     */
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_drink);

        // Create the adapter that will return a fragment for each of the three primary sections
        mAppSectionPagerAdapter = new AppSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        // Set up the action bar
        final ActionBar actionBar = getActionBar();

        // Customized title as TextView in the ActionBar
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        // Set title text for activity action bar
        TextView title = (TextView) findViewById(R.id.actionbar_home_title);
        title.setText(getResources().getString(R.string.customer_optionText_1));

        // Specify that the home/up button should not be enables since there is no hierarchical parent
        actionBar.setHomeButtonEnabled(false);

        // Specify to display tabs in the action bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Set up the viewpager, attaching the adapter and setting up a listener for when the user
        // swipe between the sections
        mViewPager = (ViewPager) findViewById(R.id.customer_home_pager);
        mViewPager.setAdapter(mAppSectionPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different sections, select the corresponding tabs.
                // We can also use ActionBar.Tab#Select() to do this if we have a reference to the tab
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the section, add a tab to the action bar
        for (int i = 0; i < mAppSectionPagerAdapter.getCount(); i++) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionPagerAdapter.getPageTitle(i))
                            .setTabListener(this));

        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Actions when tab is being unselected
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the view pager
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Actions when the tab is reselected
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    /*
    a @link FragmentPagerAdapter that returns a fragment corresponding to one of the primary
    sections of the app.
     */
    public static class AppSectionPagerAdapter extends FragmentStatePagerAdapter {
        Context mContext;
        public AppSectionPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0:
                    return new CustomerViewAll();
                case 1:
                    return new CustomerViewDings();
                case 2:
                    return new CustomerViewMap();
                default:
                    Fragment fragment = new PlaceholderFragment();
                    Bundle args = new Bundle();
                    args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Dynamic return of title name according to the tab number from string.xml
            String tabString = "eatDrink_tabText_" + (position + 1);
            int resID = mContext.getResources()
                    .getIdentifier(tabString, "string", mContext.getPackageName());
            return mContext.getResources().getString(resID);
        }
    }

    public static class CustomerViewDings extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.customer_view_dings, container, false);

            return rootView;
        }
    }

    public static class CustomerViewMap extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.customer_view_map, container, false);

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing default view in a section.
     * To be removed
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Bundle args = getArguments();

            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.display_fragment, args.getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}