package com.dinggoapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.entities.Review;

import java.util.ArrayList;
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

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRVAdapter;
    private RecyclerView.LayoutManager mRVLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_reviews);

        final Toolbar toolbar = getActionBarToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.merchant_reviews_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRVLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRVLayoutManager);

        // specify an adapter (see also next example)
        mRVAdapter = new ReviewListAdapter(getReviewList());
        mRecyclerView.setAdapter(mRVAdapter);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private List<Review> getReviewList() {
        List<Review> results = new ArrayList<>();
        results.add(new Review());
        return results;
    }
    public  ReviewHeader getHeader()
    {
        ReviewHeader header = new ReviewHeader();
        header.setHeader("I'm header");
        return header;
    }

    private class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
        private List<Review> mReviewList;

        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mUsername, mReviewedDate, mUserReviewDescription;
            public RatingBar mUserOverallRB, mFoodAndDrinkRB, mValueRB, mAmbienceRB, mServiceRB;

            public ViewHolder(View v) {
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

        public void add(int position, Review item) {
            mReviewList.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(String item) {
            int position = mReviewList.indexOf(item);
            mReviewList.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ReviewListAdapter(List<Review> myReviewList) {
            mReviewList = myReviewList;
        }

        /**
         * Create new views (invoked by the layout manager)
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_reviews_single_row, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            /*final String name = mDataset.get(position);
            holder.mUsername.setText(mDataset.get(position));

            holder.txtFooter.setText("Footer: " + mDataset.get(position));*/
            holder.mUsername.setText((CharSequence) mReviewList.get(position));
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

    public class ReviewHeader {
        //    More fields can be defined here after your need
        private String header;

        public ReviewHeader(){}

        public String getHeader() {
            return header;
        }
        public void setHeader(String header) {
            this.header = header;
        }
    }
}

