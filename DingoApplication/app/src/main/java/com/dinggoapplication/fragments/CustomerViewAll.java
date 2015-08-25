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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.activities.DealDetailsActivity;
import com.dinggoapplication.custom_ui.DividerItemDecoration;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.managers.DealManager;
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
public class CustomerViewAll extends Fragment {

    /** Fragment listener for the activity that calls this fragment */
    private OnDealFragmentInteractionListener mListener;
    /** The fragment's recycler view to display deals */
    private RecyclerView mRecyclerView;
    /** Log tag */
    private static final String TAG = makeLogTag(CustomerViewAll.class);

    private View mView;

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
        setHasOptionsMenu(true);
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
        View view = inflater.inflate(R.layout.customer_all_tab, container, false);
        mView = view;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.eat_and_drink, menu);
        Log.d(TAG, "onCreateOptionsMenu() invoked");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() invoked");
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.eat_drink_menu:
                new loadDealList(mView).execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Adapter class for recycler view to populate deals information onto the interface
     */
    private class DealListAdapter extends RecyclerView.Adapter<DealListAdapter.ViewHolder> {
        /** List of deals to be populated onto recycler view */
        private ArrayList<Deal> mDealList;

        // Provide a suitable constructor (depends on the kind of data-set)
        /**
         * Constructor to instantiate the adapter
         * @param myDealList    List of deals to be populated onto the recycler view
         */
        public DealListAdapter(ArrayList<Deal> myDealList) {
            mDealList = myDealList;
        }

        /**
         * Adding a new deal object into the deal list and display onto the recycler view
         * @param position  Row index in the recycler view where the deal to be placed
         * @param item      Deal object to be added
         */
        public void add(int position, Deal item) {
            mDealList.add(position, item);
            notifyItemInserted(position);
        }

        /**
         * Setting and changing a new list of deals to be displayed onto the recycler view
         * @param dealListDataSet   A new list of deals
         */
        public void set(ArrayList<Deal> dealListDataSet) {
            mDealList = dealListDataSet;
            notifyDataSetChanged();
        }

        /**
         * Removing a Deal object from the recycler view
         * @param item  Deal object to be removed from the list
         */
        public void remove(Deal item) {
            int position = mDealList.indexOf(item);
            mDealList.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        /**
         * View holder class that format individual rows in the recycler view according to the deal information
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            // each data item is just a string in this case
            /** View controller that contains merchant logo */
            public ImageView mMerchantLogo;
            /** View controllers that contains all required information with regards to the deal */
            public TextView mCompanyName, mMerchantType, mDealName, mAdditionInfo;

            /**
             * Constructor that instantiate the View Holder object, creating a new row in the recycler view
             * @param v     Inflatable view
             */
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

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() invoked for deal at position " + String.valueOf(getPosition()));
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                    Deal deal = mDealList.get(getPosition());
                    mListener.onDealFragmentInteraction(deal.getReferenceId());

                    Intent intent = new Intent(getActivity().getBaseContext(), DealDetailsActivity.class);
                    intent.putExtra("deal_referenceCode", deal.getReferenceId());
                    startActivity(intent);
                }
            }
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
            // set the view's size, margins, padding and layout parameters
            return new ViewHolder(v);
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         * @param holder    ViewHolder object that contains an inflatable view
         * @param position  Position of the ViewHolder object
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your data set at this position
            // - replace the contents of the view with that element

            try {

                Deal deal = mDealList.get(position);
                Branch branch = deal.getBranch().fetchIfNeeded();
                Company company = branch.getCompany();
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
         * Retrieve the number of items in the recycler view
         * @return the size of your data-set (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mDealList.size();
        }
    }

    /**
     * Async activity to load the deal list from Parse database at the background and populate all
     * the information onto the recycle view
     * @deprecated TODO Move the loading of deal list from Parse database to the main activity
     */
    private class loadDealList extends AsyncTask<Void, ArrayList<Deal>, ArrayList<Deal>> {
        /** Object to activate loading bar */
        ContentLoadingProgressBar progress;
        /** Adapter class using recycler view to populate deals information onto the interface */
        DealListAdapter mRVAdapter;

        /**
         * Constructor to instantiate loadDealList class with the following argument
         * @param view  Inflatable view to inflate onto the recycle view
         */
        public loadDealList(View view){
            Log.d(TAG, "loadDealList() invoked");
            this.progress = (ContentLoadingProgressBar) view.findViewById(R.id.loading);

            // Set the adapter
            mRecyclerView = (RecyclerView) view.findViewById(R.id.deals_recycler_view);

            // use a linear layout manager
            RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mRVLayoutManager);

            mRVAdapter = new DealListAdapter(new ArrayList<Deal>());
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
        protected ArrayList<Deal> doInBackground(Void... params) {
            DealManager dealManager = DealManager.getInstance();
            return dealManager.updateCacheList();
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
         * @param deals The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<Deal> deals) {
            this.progress.hide();
            if(deals.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mRVAdapter.set(deals);
                RecyclerView.ItemDecoration itemDecoration =
                        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                mRecyclerView.addItemDecoration(itemDecoration);
            }

        }
    }
}