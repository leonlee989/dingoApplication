package com.dinggoapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.entities.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Siu Ngee on 8/8/2015.
 */
public class MerchantReviews extends BaseActivity {
//TODO use review class in adapter list
    private static final String TAG = makeLogTag(MerchantReviews.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_reviews);

        setToolbarNavigationUp(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.merchant_reviews_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRVLayoutManager);

        // specify an adapter
        RecyclerView.Adapter mRVAdapter = new ReviewListAdapter(getHeader(), getReviewList());
        mRecyclerView.setAdapter(mRVAdapter);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private List<String> getReviewList() { //TODO change List<String> to  List<Review>
        List<String> results = new ArrayList<>();
        results.add("test");
        results.add("test2");
        results.add("test3");
        results.add("test4");
        return results;
    }
    private HashMap<String, Integer> getHeader() {
        HashMap<String, Integer> reviewHeaderAttributes = new HashMap<>();
        //TODO to be retrieved from ReviewsManager
        reviewHeaderAttributes.put("overallRatingScore", 4);
        reviewHeaderAttributes.put("numberOfReviews", 24);
        reviewHeaderAttributes.put("foodDrinkAverageScore", 4);
        reviewHeaderAttributes.put("valueAverageScore", 3);
        reviewHeaderAttributes.put("ambienceAverageScore", 4);
        reviewHeaderAttributes.put("serviceAverageScore", 5);
        return reviewHeaderAttributes;
    }

    private class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> mReviewList; //TODO change List<String> to List<Review>
        private HashMap<String, Integer> mReviewHeaderAttributes;

        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

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

        class VHItem extends RecyclerView.ViewHolder{
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

        public void add(int position, String item) { //TODO change String to Review
            mReviewList.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(Review item) {
            int position = mReviewList.indexOf(item);
            mReviewList.remove(position);
            notifyItemRemoved(position);
        }

        public ReviewListAdapter(HashMap<String, Integer> myHeader, List<String> myReviewList) { //TODO change List<String> to List<Review>
            mReviewHeaderAttributes = myHeader;
            mReviewList = myReviewList;
        }

        /**
         * Create new views (invoked by the layout manager)
         * @param parent
         * @param viewType
         * @return
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
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof VHHeader) {
                VHHeader VHheader = (VHHeader)holder;

                VHheader.mOverallRatingScore.setText(String.valueOf(mReviewHeaderAttributes.get("overallRatingScore")));
                VHheader.mNumberOfReviews.setText(String.valueOf(mReviewHeaderAttributes.get("numberOfReviews")));
                VHheader.mOverallRB.setRating(Float.valueOf(mReviewHeaderAttributes.get("overallRatingScore")));
                VHheader.mOverallFoodDrinkRB.setRating(Float.valueOf(mReviewHeaderAttributes.get("foodDrinkAverageScore")));
                VHheader.mOverallValueRB.setRating(Float.valueOf(mReviewHeaderAttributes.get("valueAverageScore")));
                VHheader.mOverallAmbienceRB.setRating(Float.valueOf(mReviewHeaderAttributes.get("ambienceAverageScore")));
                VHheader.mOverallServiceRB.setRating(Float.valueOf(mReviewHeaderAttributes.get("serviceAverageScore")));

            } else if(holder instanceof VHItem) {
                String currentReview = getItem(position - 1); //TODO change String to Review
                VHItem VHitem = (VHItem)holder;
                //TODO set textview and ratingbar values for single review item
            }
        }

        private String getItem(int position) {
            return mReviewList.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            if(isPositionHeader(position)) {
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        /**
         *
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mReviewList.size();
        }
    }

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
}