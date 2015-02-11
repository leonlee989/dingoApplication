package com.example.leon.dingoapplication;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /*
    PagerAdapter that will provide fragments for each of the three primary section of the application
     */
    AppSectionPagerAdapter mAppSectionPagerAdapter;

    /*
    the {@link ViewPager} that will display the three primary sections of the application, one at a time
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        mAppSectionPagerAdapter = new AppSectionPagerAdapter(getSupportFragmentManager());

        // Set up the action bar
        final ActionBar actionBar = getActionBar();


        // Specify that the home/up button should not be enables since there is no hierarchical parent
        actionBar.setHomeButtonEnabled(false);

        // Specify to display tabs in the action bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Set up the viewpager, attaching the adapter and setting up a listener for when the user
        // swipe between the sections
        mViewPager = (ViewPager) findViewById(R.id.pager);
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

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        */
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the view pager
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /*
    a @link FragmentPagerAdapter that returns a fragment corresponding to one of the primary
    sections of the app.
     */
    public static class AppSectionPagerAdapter extends FragmentStatePagerAdapter {
        public AppSectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
           switch(i) {
               case 0:
                   return new LaunchpadSectionFragment();

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
            return "Section " + (position + 1);
        }
    }

    // A fragment that launch other parts of the application
    public static class LaunchpadSectionFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_section_launchpah, container, false);

            // Demonstration of a collection-browsing activity
            rootView.findViewById(R.id.collection_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), CollectionActivity.class);
                            startActivity(intent);
                        }
                    });

            //Demonstration of navigating to external activities
            rootView.findViewById(R.id.external_activity_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*
                            Create an intent that ask user to pick a photo, but using
                            FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                            the application from the device home screen does not return
                            to the external activity
                             */
                            Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                            externalActivityIntent.setType("image/*");
                            externalActivityIntent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(externalActivityIntent);

                        }
                    });

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
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
}
