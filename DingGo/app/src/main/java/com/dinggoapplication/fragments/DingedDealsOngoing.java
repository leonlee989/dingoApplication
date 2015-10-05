package com.dinggoapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.activities.DingedDealDetailsActivity;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.managers.DealManager;
import com.parse.ParseException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DingedDealsOngoing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Siu Ngee on 18/8/2015.
 */
public class DingedDealsOngoing extends Fragment implements AbsListView.OnItemClickListener{

    /** The fragment's ListView/GridView */
    private AbsListView mListView;
    /** The Adapter which will be used to populate the ListView/GridView with Views */
    private ListAdapter mAdapter;
    /** All available deals in the system */
    private ArrayList<Deal> dealList;
    /** Fragment listener for the activity that calls this fragment */
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DingedDealsOngoing() {
        dealList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO change listview to recycleview
        //TODO retrieve list of dingedDeals from parse filtered by status == ongoing
        DealManager dealManager = DealManager.getInstance();
        this.dealList = dealManager.getDealsFromCache();
        mAdapter = new DealArrayAdapter(getActivity(), this.dealList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinged_deals_ongoing, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.dealList);
        mListView.setVisibility(View.GONE);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Deal deal = this.dealList.get((this.dealList.size()-1) - position);
            mListener.onFragmentInteraction(deal.getReferenceId());
            //Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity().getBaseContext(), DingedDealDetailsActivity.class);
            intent.putExtra("deal_referenceCode", deal.getReferenceId());
            startActivity(intent);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     *
     * @param emptyText     Text to display if the list is empty
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        /**
         * This method allows interactions with the activity class who implements this listener by its id
         * @param id    ID of the fragment to be identify in the activity class
         */
        void onFragmentInteraction(String id);
    }

    /**
     * Custom ArrayAdapter to display deal details in a list view
     */
    private class DealArrayAdapter extends ArrayAdapter<Deal> {
        /** Context object that stores all the resources */
        private final Context context;
        /** A list of deals to be displayed onto the list view */
        private final ArrayList<Deal> dealList;

        /**
         * Constructor that initialize DealArrayAdapter with the following parameters
         * @param context   Context object that stores all the resources
         * @param dealList    A list of deals to be displayed onto the list view
         */
        public DealArrayAdapter(Context context, ArrayList<Deal> dealList) {
            super(context, R.layout.deal_view_row, dealList);
            this.context = context;
            this.dealList = dealList;
        }

        /**
         * {@inheritDoc}
         * Customized view for different deals
         * @param position      Position/index of the row in the list view
         * @param convertView   Customized row layout on the list view
         * @param parent        Parent element of this view
         * @return              View object that represents one of the row in the list view
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View rowView = inflater.inflate(R.layout.deal_view_row, parent, false);

            try {
                Deal deal = dealList.get((dealList.size() - 1) - position);

                Branch branch = deal.getBranch().fetchIfNeeded();
                Company company = branch.getCompany();

                ImageView image = (ImageView) rowView.findViewById(R.id.merchantLogo);
                image.setImageBitmap(company.getLogoImage());

                TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
                companyName.setText(company.getCompanyName());

                TextView merchantType = (TextView) rowView.findViewById(R.id.merchantType);
                merchantType.setText(company.getCuisineType().getCuisineName());

                TextView dealTextView = (TextView) rowView.findViewById(R.id.dealName);
                dealTextView.setText(deal.getDealName());
                dealTextView.setTypeface(dealTextView.getTypeface(), Typeface.BOLD);

                TextView additionInfo = (TextView) rowView.findViewById(R.id.addtionalInfo);

                //TODO set to timeLeft
                additionInfo.setText("500 m");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return rowView;
        }
    }

}
