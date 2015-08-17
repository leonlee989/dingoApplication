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

import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

public class MerchantReviews extends BaseActivity {

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
        mRVAdapter = new ReviewListAdapter(getDataSet());
        mRecyclerView.setAdapter(mRVAdapter);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private ArrayList<String> getDataSet() {
        ArrayList<String> results = new ArrayList<>();
        results.add("user1");
        results.add("user2");
        results.add("user3");
        results.add("user4");
        return results;
    }

    private class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
        private ArrayList<String> mDataset;

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

        public void add(int position, String item) {
            mDataset.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(String item) {
            int position = mDataset.indexOf(item);
            mDataset.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ReviewListAdapter(ArrayList<String> myDataset) {
            mDataset = myDataset;
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
            holder.mUsername.setText(mDataset.get(position));
        }

        /**
         *
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mDataset.size();
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

