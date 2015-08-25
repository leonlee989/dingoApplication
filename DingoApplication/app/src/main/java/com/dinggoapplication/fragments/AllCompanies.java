package com.dinggoapplication.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.activities.MerchantDetails;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.managers.CompanyManager;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * A fragment that display all merchants' information into a view list
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/8/15.
 */
public class AllCompanies extends Fragment{

    /** Fragment listener for the activity that calls this fragment */
    private OnCompanyFragmentInteractionListener mListener;
    /** Name of the Log Tag for this class */
    private static final String TAG = makeLogTag(AllCompanies.class);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllCompanies() {}

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
        View view = inflater.inflate(R.layout.fragment_all_companies, container, false);
        /* The fragment's recycler view to display company */
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.allCompaniesRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mRVLayoutManager);

        // specify an adapter
        RecyclerView.Adapter mRVAdapter = new CompanyListAdapter(getCompanyList());
        mRecyclerView.setAdapter(mRVAdapter);

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
            mListener = (OnCompanyFragmentInteractionListener) activity;
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
    public interface OnCompanyFragmentInteractionListener {
        // TODO: Update argument type and name
        /**
         * This method allows interactions with the activity class who implements this listener by its id
         * @param id    ID of the fragment to be identify in the activity class
         */
        void OnCompanyFragmentInteraction(String id);
    }

    /**
     * Retrieve all companies from Parse and populate the recycle view
     * @return  A list of companies retrieve from Parse
     * TODO: Leverage on Fragment Interaction Listener to retrieve the deals Parse on the main activity, where this method will be pulling from cache
     */
    private List<Company> getCompanyList() {
        CompanyManager companyManager = CompanyManager.getInstance();
        return companyManager.updateCacheList();
    }

    /**
     * Adapter class for Recycler view to populate company information onto the interface
     */
    private class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
        /** List of companies that will be populated onto recycle view */
        private List<Company> mCompanyList;

        // Provide a suitable constructor (depends on the kind of data-set)
        /**
         * Constructor to instantiate the adapter with the following argument
         * @param myCompanyList     List of companies' that will be populated onto the recycler view
         */
        public CompanyListAdapter(List<Company> myCompanyList) {
            mCompanyList = myCompanyList;
        }

        /**
         * Adding a new company object into the company list and display onto the recycler view
         * @param position  Row index in the recycle view where the company will be placed on
         * @param item      Company object to be placed
         */
        public void add(int position, Company item) {
            mCompanyList.add(position, item);
            notifyItemInserted(position);
        }

        /**
         * Setting and changing a new list of companies to be displayed onto the recycler view
         * @param mCompanyList   A new list of companies
         */
        public void set(ArrayList<Company> mCompanyList) {
            this.mCompanyList = mCompanyList;
            notifyDataSetChanged();
        }

        /**
         * Removing a Company object from the recycler view
         * @param item  Company object to be removed from list
         */
        public void remove(Company item) {
            int position = mCompanyList.indexOf(item);
            mCompanyList.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        /**
         * View holder class that format individual rows in the recycler view according to the company information
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public CardView mCardView;
            /** Text information with regards to the company */
            public TextView mCompanyName, mCompanyDescription;
            /** Image requried for the company */
            public ImageView mCompanyCoverImage, mFavouriteIcon;
            /** Average rating values for the company */
            public RatingBar mMerchantOverallRating;

            /**
             * Constructor that instantiates the View Holder object, creating a new row in the recycler view
             * @param v     Inflatable view object
             */
            public ViewHolder(View v) {
                super(v);
                v.setClickable(true);
                v.setOnClickListener(this);

                mCardView = (CardView) v.findViewById(R.id.cv);
                mCompanyName = (TextView) v.findViewById(R.id.companyName);
                mCompanyDescription = (TextView) v.findViewById(R.id.companyDescription);
                mCompanyCoverImage = (ImageView) v.findViewById(R.id.companyCoverImage);
                //mFavouriteIcon = (ImageView) v.findViewById(R.id.favouriteIcon);
                mMerchantOverallRating = (RatingBar) v.findViewById(R.id.merchantOverallRating);
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Log.d(TAG, String.valueOf(getPosition()));
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Company company = mCompanyList.get(getPosition());
                    mListener.OnCompanyFragmentInteraction(company.getCompanyId());

                    Intent intent = new Intent(getActivity().getBaseContext(), MerchantDetails.class);
                    intent.putExtra("companyId", company.getCompanyId());
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
        public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.companies_single_row, parent, false);
            // set the view's size, margins, padding and layout parameters
            return new ViewHolder(v);
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         * @param holder        ViewHolder object that contains an inflatable view
         * @param position      Position of the ViewHolder object
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your data set at this position
            // - replace the contents of the view with that element
            Company company = mCompanyList.get(position);

            try {
                holder.mCompanyCoverImage.setImageBitmap(company.getCoverImage());
                holder.mCompanyName.setText(company.getCompanyName());
                holder.mCompanyDescription.setText(company.getDescription());
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        /**
         * Retrieve the number of items in the recycler view
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mCompanyList.size();
        }
    }

    /*
    private class loadCompanyList extends AsyncTask<Void, ArrayList<Company>, ArrayList<Company>> {

        ContentLoadingProgressBar progress;
        CompanyListAdapter mRVAdapter;

        public loadCompanyList(View view){
            this.progress = (ContentLoadingProgressBar) view.findViewById(R.id.loading);

            // Set the adapter
            mRecyclerView = (RecyclerView) view.findViewById(R.id.allCompaniesRV);

            // use a linear layout manager
            RecyclerView.LayoutManager mRVLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mRVLayoutManager);

            mRVAdapter = new CompanyListAdapter(new ArrayList<Company>());
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
         * /
        @Override
        protected ArrayList<Company> doInBackground(Void... params) {
            CompanyManager companyManager = CompanyManager.getInstance();
            return companyManager.updateCacheList();
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         * /
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
         * @param companies The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         * /
        @Override
        protected void onPostExecute(ArrayList<Company> companies) {
            progress.hide();

            mRVAdapter.set(companies);
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }
    */
}
