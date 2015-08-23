/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.activities.DealDetailsActivity;
import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.managers.DealManager;
import com.dinggoapplication.managers.ReviewManager;
import com.parse.ParseException;

import java.util.ArrayList;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnDealFragmentInteractionListener}
 * interface.
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 18/02/15.
 */
public class CustomerViewAll extends Fragment
        //implements AbsListView.OnItemClickListener
{

    /** Fragment listener for the activity that calls this fragment */
    private OnDealFragmentInteractionListener mListener;

    /** The fragment's ListView/GridView */
    //private AbsListView mListView;
    /** The Adapter which will be used to populate the ListView/GridView with Views */
    //private ListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    DealManager dealManager;
    private static final String TAG = makeLogTag(CustomerViewAll.class);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerViewAll() {}

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() invoked");
        dealManager = DealManager.getInstance();

    }

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
        Log.d(TAG, "onCreateView() invoked");
        View view = inflater.inflate(R.layout.customer_all_tab, container, false);

        new loadDealList(view).execute();

        return view;
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param activity  Activity class that the fragment is attached to
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDealFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     *
     * @param emptyText     Text to display if the list is empty
     */
    public void setEmptyText(CharSequence emptyText) {
        /*View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }*/
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnDealFragmentInteractionListener {
        // TODO: Update argument type and name

        /**
         * This method allows interactions with the activity class who implements this listener by its id
         * @param id    ID of the fragment to be identify in the activity class
         */
        void onDealFragmentInteraction(String id);
    }

    private class DealListAdapter extends RecyclerView.Adapter<DealListAdapter.ViewHolder> {
        private ArrayList<Deal> mDealList;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            // each data item is just a string in this case
            public ImageView mMerchantLogo;
            public TextView mCompanyName, mMerchantType, mDealName, mAdditionInfo;

            public ViewHolder(View v) {
                super(v);
                v.setClickable(true);
                v.setOnClickListener(this);

                mMerchantLogo = (ImageView) v.findViewById(R.id.merchantLogo);
                mCompanyName = (TextView) v.findViewById(R.id.companyName);
                mMerchantType = (TextView) v.findViewById(R.id.merchantType);
                mDealName = (TextView) v.findViewById(R.id.dealName);
                mAdditionInfo = (TextView) v.findViewById(R.id.addtionalInfo);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() invoked for deal at position " + String.valueOf(getPosition()));
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.

                    Deal deal = mDealList.get((mDealList.size() - 1) - getPosition());
                    mListener.onDealFragmentInteraction(deal.getReferenceId());

                    ReviewManager reviewManager = ReviewManager.getInstance();
                    reviewManager.retrieveReviews(deal.getBranch().getCompany());

                    Intent intent = new Intent(getActivity().getBaseContext(), DealDetailsActivity.class);
                    intent.putExtra("deal_referenceCode", deal.getReferenceId());
                    startActivity(intent);
                }
            }
        }

        public void add(int position, Deal item) {
            mDealList.add(position, item);
            notifyItemInserted(position);
        }

        public void set(ArrayList<Deal> dealListDataSet) {
            mDealList = dealListDataSet;
            notifyDataSetChanged();
        }

        public void remove(Deal item) {
            int position = mDealList.indexOf(item);
            mDealList.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public DealListAdapter(ArrayList<Deal> myDealList) {
            mDealList = myDealList;
        }

        /**
         * Create new views (invoked by the layout manager)
         * @param parent    View group object that is the parent for this view holder
         * @param viewType  Type of inflated view
         * @return          ViewHolder object which contains a inflatable view
         */
        @Override
        public DealListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_view_row, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder(v);
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         * @param holder    ViewHolder object that contains an inflatable view
         * @param position  POsition of the ViewHolder object
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            try {

                Deal deal = mDealList.get((mDealList.size() - 1) - position);
                Company company = deal.getBranch().getCompany();
                holder.mMerchantLogo.setImageBitmap(company.getLogoImage());
                holder.mCompanyName.setText(company.getCompanyName());
                holder.mMerchantType.setText(company.getCuisineType().getCuisineName());
                holder.mDealName.setText(deal.getDealName());
                holder.mAdditionInfo.setText("500m"); //TODO get distance between user & merchant

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        /**
         *
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mDealList.size();
        }

    }


    private class loadDealList extends AsyncTask<Void, ArrayList<Deal>, ArrayList<Deal>> {

        ContentLoadingProgressBar progress;
        DealListAdapter mRVAdapter;

        public loadDealList(View view){
            Log.d(TAG, "loadDealList() invoked");
            this.progress = (ContentLoadingProgressBar) view.findViewById(R.id.loading);

            // Set the adapter
            //mListView = (AbsListView) view.findViewById(R.id.dealList);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.deals_recycler_view);

            // use a linear layout manager
            RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mRVLayoutManager);

            mRVAdapter = new DealListAdapter(new ArrayList<Deal>());
            mRecyclerView.setAdapter(mRVAdapter);
        }

        @Override
        protected ArrayList<Deal> doInBackground(Void... params) {
            Log.d(TAG, "doInBackground() invoked");
            DealManager dealManager = DealManager.getInstance();
            return dealManager.updateCacheList();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute() invoked");

            progress.show();
            Log.d(TAG, progress.toString());
        }

        @Override
        protected void onPostExecute(ArrayList<Deal> deals) {
            Log.d(TAG, "onPostExecute() invoked: " + deals.toString());

            this.progress.hide();

            mRVAdapter.set(deals);
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);

        }
    }
}