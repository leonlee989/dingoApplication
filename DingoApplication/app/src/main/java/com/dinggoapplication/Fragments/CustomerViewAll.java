/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Fragments;

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

import com.dinggoapplication.Activities.CustomerDealDetailsActivity;
import com.dinggoapplication.Config;
import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.R;

import java.util.ArrayList;

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
public class CustomerViewAll extends Fragment implements AbsListView.OnItemClickListener {

    /** Fragment listener for the activity that calls this fragment */
    private OnDealFragmentInteractionListener mListener;

    /** The fragment's ListView/GridView */
    private AbsListView mListView;
    /** The Adapter which will be used to populate the ListView/GridView with Views */
    private ListAdapter mAdapter;
    /** All available deals in the system */
    private ArrayList<Deal> dealList;

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
        this.dealList = Config.dealManager.getDealList();
        mAdapter = new DealArrayAdapter(getActivity(), this.dealList);
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

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.dealList);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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
            mListener.onDealFragmentInteraction(deal.getReferenceCode());
            //Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity().getBaseContext(), CustomerDealDetailsActivity.class);
            intent.putExtra("deal_referenceCode", deal.getReferenceCode());
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
    public interface OnDealFragmentInteractionListener {
        // TODO: Update argument type and name

        /**
         * This method allows interactions with the activity class who implements this listener by its id
         * @param id    ID of the fragment to be identify in the activity class
         */
        void onDealFragmentInteraction(String id);
    }

    /**
     * Custom ArrayAdapter to display deal details in a list view
     */
    private class DealArrayAdapter extends ArrayAdapter<Deal> {
        /** Context object that stores all the resources */
        private final Context context;
        /** A list of deals to be displayed onto the list view */
        private final ArrayList<Deal> values;

        /**
         * Constructor that initialize DealArrayAdapter with the following parameters
         * @param context   Context object that stores all the resources
         * @param values    A list of deals to be displayed onto the list view
         */
        public DealArrayAdapter(Context context, ArrayList<Deal> values) {
            super(context, R.layout.deal_view_row, values);
            this.context = context;
            this.values = values;
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

            Deal deal = values.get((values.size()-1) - position);
            Merchant merchant = deal.getMerchant();

            ImageView image = (ImageView) rowView.findViewById(R.id.merchantLogo);
            image.setImageBitmap(merchant.getImage());

            TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
            companyName.setText(merchant.getCompanyName());

            TextView merchantType = (TextView) rowView.findViewById(R.id.merchantType);
            merchantType.setText(merchant.getMerchantType());

            TextView dealTextView = (TextView) rowView.findViewById(R.id.dealDetail);
            dealTextView.setText(deal.toString());
            dealTextView.setTypeface(dealTextView.getTypeface(), Typeface.BOLD);

            TextView additionInfo = (TextView) rowView.findViewById(R.id.addtionalInfo);
            additionInfo.setText("500 m");

            return rowView;
        }
    }
}