package com.dinggo.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.dinggo.R;
import com.dinggo.fragments.DingedDealsOngoing;
import com.dinggo.fragments.DingedDealsRewards;
import com.dinggo.widget.SlidingTabLayout;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggo.utilities.LogUtils.makeLogTag;

public class DingedDealsActivity extends BaseActivity implements
        DingedDealsOngoing.OnFragmentInteractionListener,
        DingedDealsRewards.OnFragmentInteractionListener {

    private static final String TAG = makeLogTag(DingedDealsActivity.class);

    /** PagerAdapter that will provide fragments for each of the three primary section of the application */
    AppSectionPagerAdapter mAppSectionPagerAdapter;

    /** the {@link ViewPager} that will display the three primary sections of the application, one at a time */
    ViewPager mViewPager;

    /** Context variable to store resources */
    Context mContext;

    SlidingTabLayout mSlidingTabLayout = null;

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_DINGED_DEALS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinged_deals);
        getActionBarToolbar();

        // Create the adapter that will return a fragment for each of the two primary sections
        mAppSectionPagerAdapter = new AppSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        //Set up the viewpager, attaching the adapter and setting up a listener for when the user
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch(position) {
                case 0:
                    fragment = new DingedDealsOngoing();
                    break;
                case 1:
                    fragment = new DingedDealsRewards();
                    break;
            }
            return fragment;
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
            String tabString = "dingedDeals_tabText_" + (position + 1);
            int resID = mContext.getResources()
                    .getIdentifier(tabString, "string", mContext.getPackageName());
            return mContext.getResources().getString(resID);
        }
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
