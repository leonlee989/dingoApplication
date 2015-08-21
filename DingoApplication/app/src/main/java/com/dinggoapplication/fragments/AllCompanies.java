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

import java.util.ArrayList;
import java.util.List;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 20/8/15.
 */
public class AllCompanies extends Fragment{

    /** Fragment listener for the activity that calls this fragment */
    private OnCompanyFragmentInteractionListener mListener;

    /** All available deals in the system */
    private ArrayList<Company> companyList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRVAdapter;
    private RecyclerView.LayoutManager mRVLayoutManager;

    private static final String TAG = makeLogTag(AllCompanies.class);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllCompanies() {
    }

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
        View view = inflater.inflate(R.layout.fragment_all_companies, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.allCompaniesRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRVLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mRVLayoutManager);

        // specify an adapter
        mRVAdapter = new CompanyListAdapter(getCompanyList());
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

    private List<Company> getCompanyList() {
        List<Company> results = new ArrayList<>();
        results.add(new Company());
        results.add(new Company());
        results.add(new Company());
        return results;
    }

    private class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
        private List<Company> mCompanyList;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            // each data item is just a string in this case
            public CardView mCardView;
            public TextView mCompanyName, mCompanyDescription;
            public ImageView mCompanyCoverImage, mfavouriteIcon;
            public RatingBar mMerchantOverallRating;

            public ViewHolder(View v) {
                super(v);
                v.setClickable(true);
                v.setOnClickListener(this);
                mCardView = (CardView) v.findViewById(R.id.cv);
                mCompanyName = (TextView) v.findViewById(R.id.companyName);
                mCompanyDescription = (TextView) v.findViewById(R.id.companyDescription);
                mCompanyCoverImage = (ImageView) v.findViewById(R.id.companyCoverImage);
                mfavouriteIcon = (ImageView) v.findViewById(R.id.favouriteIcon);
                //mCompanyLogo = (ImageView) v.findViewById(R.id.companyLogo);
                mMerchantOverallRating = (RatingBar) v.findViewById(R.id.merchantOverallRating);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, String.valueOf(getPosition()));
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //Company company = companyList.get((companyList.size() - 1) - getPosition());
            /*mListener.OnCompanyFragmentInteraction(company.getReferenceId());
            //Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity().getBaseContext(), MerchantDetails.class);
            intent.putExtra("deal_referenceCode", company.getReferenceId());
            startActivity(intent);*/
                    Intent intent = new Intent(getActivity().getBaseContext(), MerchantDetails.class);
                    startActivity(intent);
                }
            }
        }

        public void add(int position, Company item) {
            mCompanyList.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(Company item) {
            int position = mCompanyList.indexOf(item);
            mCompanyList.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public CompanyListAdapter(List<Company> myCompanyList) {
            mCompanyList = myCompanyList;
        }

        /**
         * Create new views (invoked by the layout manager)
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.companies_single_row, parent, false);
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
            //holder.mUsername.setText((CharSequence) mCompanyList.get(position));
        }

        /**
         *
         * @return the size of your dataset (invoked by the layout manager)
         */
        @Override
        public int getItemCount() {
            return mCompanyList.size();
        }

    }

    /*private class loadCompanyList extends AsyncTask<Void, ArrayList<Deal>, ArrayList<Deal>> {

        ContentLoadingProgressBar progress;
        AdapterView.OnItemClickListener listener;
        public loadCompanyList( View view, AdapterView.OnItemClickListener listener){
            Log.d(TAG, "loadDealList() invoked");
            this.progress = (ContentLoadingProgressBar) view.findViewById(R.id.loading);
            // Set the adapter
            mListView = (AbsListView) view.findViewById(R.id.dealList);
            this.listener = listener;
        }

        @Override
        protected ArrayList<Deal> doInBackground(Void... params) {
            Log.d(TAG, "doInBackground() invoked");
            HashMap<String, Deal> cachedDealList = new HashMap<>();
            new Thread(new Runnable() {
                public void run() {
                    dealManager.updateCacheList();

                }
            }).start();
            try {
                cachedDealList = dealManager.getFromCache();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new ArrayList<>(cachedDealList.values());
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute() invoked");
            this.progress.show();
            //progress.setVisibility(View.VISIBLE);
            //progress.setIndeterminate(true);
            Log.d(TAG, progress.toString());
        }

        @Override
        protected void onPostExecute(ArrayList<Deal> deals) {
            Log.d(TAG, "onPostExecute() invoked: " + deals.toString());
            this.progress.hide();
            dealList = dealManager.getDealList();
            Log.d(TAG, dealList.toString());
            mAdapter = new DealArrayAdapter(getActivity(), dealList);
            mListView.setAdapter(mAdapter);

            // Set OnItemClickListener so we can be notified on item clicks
            mListView.setOnItemClickListener(this.listener);
        }
    }*/

}
