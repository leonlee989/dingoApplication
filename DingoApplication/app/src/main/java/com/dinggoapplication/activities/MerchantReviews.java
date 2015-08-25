package com.dinggoapplication.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Review;
import com.dinggoapplication.managers.CompanyManager;
import com.dinggoapplication.managers.ReviewManager;
import com.parse.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * Activity class that display all review according to a single merchant
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Siu Ngee on 8/8/2015.
 */
public class MerchantReviews extends BaseActivity {
    /** The activity class's recycler view to display the reviews */
    RecyclerView mRecyclerView;
    /** String value that contains the name of the tag */
    private static final String TAG = makeLogTag(MerchantReviews.class);
    /** Date to format into for display */
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_reviews);
        setToolbarNavigationUp(getActionBarToolbar());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                CompanyManager companyManager = CompanyManager.getInstance();
                Company company = companyManager.getCompany(extras.getString("companyId"));
                HashMap<String, Float> headerValues = (HashMap<String, Float>) extras.getSerializable("averageStars");

                new loadReviewList(company, headerValues).execute();
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * Adapter class for recycler view to populate reviews onto the interface
     */
    private class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        /** List of average ratings to be display on the header of the recycler view */
        private HashMap<String, Float> mReviewHeaderAttributes;
        /** List of reviews to be populated onto the recycler view */
        private List<Review> mReviewList;
        /** Integer value that contains the index for header type */
        private static final int TYPE_HEADER = 0;
        /** Integer value that contains the index for item type */
        private static final int TYPE_ITEM = 1;

        /**
         * Constructor to instantiate the adapter with the following arguments
         * @param myHeader      Hash of average ratings to be display on the header of the recycler view
         * @param myReviewList  List of reviews to be populated onto the recycler view
         */
        public ReviewListAdapter(HashMap<String, Float> myHeader, List<Review> myReviewList) {
            mReviewHeaderAttributes = myHeader;
            mReviewList = myReviewList;
        }

        /**
         * Adding a new review object into the deal list and display onto the recycler view
         * @param position  Row index in the recycler view where the review is bring placed
         * @param item      Review object to be placed
         */
        public void add(int position, Review item) {
            mReviewList.add(position, item);
            notifyItemInserted(position);
        }

        /**
         * Setting and changing a new list of review to be displayed onto the recycler view
         * @param reviewList    List of reviews to be populated onto the recycler view
         */
        public void set(List<Review> reviewList) {
            mReviewList = reviewList;
            notifyDataSetChanged();
        }

        /**
         * Removing a review object from the recycler view
         * @param item  Review object to be removed from the list
         */
        public void remove(Review item) {
            int position = mReviewList.indexOf(item);
            mReviewList.remove(position);
            notifyItemRemoved(position);
        }

        /**
         * View holder class that format the header row in the recycler view according to the average ratings given to the merchant
         */
        class VHHeader extends RecyclerView.ViewHolder{
            public TextView mOverallRatingScore, mNumberOfReviews;
            public RatingBar mOverallRB, mOverallFoodDrinkRB, mOverallValueRB, mOverallAmbienceRB, mOverallServiceRB;
            public VHHeader(View v) {
                super(v);
                mOverallRatingScore = (TextView) v.findViewById(R.id.overallRatingScore);
                mNumberOfReviews = (TextView) v.findViewById(R.id.numberOfReviews);
                mOverallRB = (RatingBar) v.findViewById(R.id.overallRB);
                mOverallFoodDrinkRB = (RatingBar) v.findViewById(R.id.overallFoodDrinkRB);
                mOverallValueRB = (RatingBar) v.findViewById(R.id.overallValueRB);
                mOverallAmbienceRB = (RatingBar) v.findViewById(R.id.overallAmbienceRB);
                mOverallServiceRB = (RatingBar) v.findViewById(R.id.overallServiceRB);
            }
        }

        /**
         * View holder class that format the individual item rows in the recycler view according to the review object
         */
        class VHItem extends RecyclerView.ViewHolder {
            public TextView mUsername, mReviewedDate, mUserReviewDescription;
            public RatingBar mUserOverallRB, mFoodAndDrinkRB, mValueRB, mAmbienceRB, mServiceRB;
            public VHItem(View v) {
                super(v);
                mUsername = (TextView) v.findViewById(R.id.username);
                mReviewedDate = (TextView) v.findViewById(R.id.reviewedDate);
                mUserReviewDescription = (TextView) v.findViewById(R.id.userReviewDescription);
                mUserOverallRB = (RatingBar) v.findViewById(R.id.userOverallRating);
                mFoodAndDrinkRB = (RatingBar) v.findViewById(R.id.foodAndDrinkRB);
                mValueRB = (RatingBar) v.findViewById(R.id.valueRB);
                mAmbienceRB = (RatingBar) v.findViewById(R.id.ambienceRB);
                mServiceRB = (RatingBar) v.findViewById(R.id.serviceRB);
            }
        }

        /**
         * Create new views (invoked by the layout manager)
         * @param parent        View group object that is the parent for this view holder
         * @param viewType      Type of inflated view
         * @return              ViewHolder object which contains a inflatable view
         */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_reviews_overall, parent, false);
                return  new VHHeader(v);
            } else if(viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_reviews_single_row, parent, false);
                return new VHItem(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         * @param holder    ViewHolder object that contains an inflatable view
         * @param position  Position of the ViewHolder object
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof VHHeader) {
                VHHeader VHheader = (VHHeader)holder;

                VHheader.mOverallRatingScore.setText(String.valueOf(mReviewHeaderAttributes.get("total")));
                VHheader.mNumberOfReviews.setText(String.valueOf(mReviewHeaderAttributes.get("numReviews")));

                VHheader.mOverallRB.setRating(mReviewHeaderAttributes.get("total"));
                VHheader.mOverallFoodDrinkRB.setRating(mReviewHeaderAttributes.get("food_drink"));
                VHheader.mOverallValueRB.setRating(mReviewHeaderAttributes.get("value"));
                VHheader.mOverallAmbienceRB.setRating(mReviewHeaderAttributes.get("ambience"));
                VHheader.mOverallServiceRB.setRating(mReviewHeaderAttributes.get("service"));

            } else if(holder instanceof VHItem) {
                Review currentReview = getItem(position - 1);
                VHItem VHitem = (VHItem) holder;

                VHitem.mUsername.setText(currentReview.getRaterName());
                VHitem.mReviewedDate.setText(dateFormat.format(currentReview.getCreatedAt()));

                VHitem.mUserReviewDescription.setText(currentReview.getComments());
                VHitem.mUserOverallRB.setRating(currentReview.getAverageRating());
                VHitem.mFoodAndDrinkRB.setRating(currentReview.getFoodRating());
                VHitem.mValueRB.setRating(currentReview.getValueRating());
                VHitem.mAmbienceRB.setRating(currentReview.getAmbienceRating());
                VHitem.mServiceRB.setRating(currentReview.getServiceRating());
            }
        }

        /**
         * Retrieve the review object based on the row index in the recycler view
         * @param position  Row index in the recycler view
         * @return          Review object that contains all the data with regards to a single review
         */
        private Review getItem(int position) {
            return mReviewList.get(position);
        }

        /**
         * Return the view type of the item at <code>position</code> for the purposes
         * of view recycling.
         * <p/>
         * <p>The default implementation of this method returns 0, making the assumption of
         * a single view type for the adapter. Unlike ListView adapters, types need not
         * be contiguous. Consider using id resources to uniquely identify item view types.
         *
         * @param position position to query
         * @return integer value identifying the type of the view needed to represent the item at
         * <code>position</code>. Type codes need not be contiguous.
         */
        @Override
        public int getItemViewType(int position) {
            if(isPositionHeader(position)) {
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        }

        /**
         * Method to check whether the row index provided in the argument is a header type
         * @param position  Row index in the recycler view
         * @return          Boolean object to determine whether the row index provided in the argument is a header type
         */
        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        /**
         * Retrieve the number of items in the recycler view
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mReviewList.size();
        }
    }

    /**
     * Methods to trigger when scrollable view is being swipe
     * @return  Boolean object with regards to the status of the swipe
     */
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
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
     * Async activity to load the review list from Parse database at the background and populate all
     * the information onto the recycle view
     * Retrieval of average rating score and display them in the header
     */
    private class loadReviewList extends AsyncTask<Void, ArrayList<Review>, ArrayList<Review>> {
        /** Object to activate loading bar */
        ContentLoadingProgressBar progress;
        /** Adapter class using recycler view to populate deals information onto the interface */
        ReviewListAdapter mRVAdapter;
        /** Company object that contains all information with regards to the merchant */
        Company company;

        /**
         * Constructor to instantiate loadDealList class with the following argument
         * @param company       Company object that contains all information with regards to the merchant
         * @param headerValues  Header values for the review page which consist of the average ratings
         */
        public loadReviewList(Company company, HashMap<String, Float> headerValues) {
            Log.d(TAG, "loadDealList() invoked");
            this.progress = (ContentLoadingProgressBar) findViewById(R.id.loading);
            this.company = company;

            mRecyclerView = (RecyclerView) findViewById(R.id.merchant_reviews_recycler_view);
            /* use this setting to improve performance if you know that changes in content do not change
            the layout size of the RecyclerView */
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mRVLayoutManager);

            // specify an adapter
            mRVAdapter = new ReviewListAdapter(headerValues, new ArrayList<Review>());
            mRecyclerView.setAdapter(mRVAdapter);
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
        protected ArrayList<Review> doInBackground(Void... params) {
            ReviewManager reviewManager = ReviewManager.getInstance();
            return reviewManager.updateCacheList(company);
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
         * @param reviews The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            this.progress.hide();

            mRVAdapter.set(reviews);
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }
}