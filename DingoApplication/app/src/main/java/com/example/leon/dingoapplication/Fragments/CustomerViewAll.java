package com.example.leon.dingoapplication.Fragments;

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
import android.widget.Toast;

import com.example.leon.dingoapplication.Activities.CustomerDealDetailsActivity;
import com.example.leon.dingoapplication.Constants;
import com.example.leon.dingoapplication.Entity.Deal;
import com.example.leon.dingoapplication.Entity.Merchant;
import com.example.leon.dingoapplication.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnDealFragmentInteractionListener}
 * interface.
 */
public class CustomerViewAll extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * Fragment listener for the activity that calls this fragment
     */
    private OnDealFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerViewAll() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DealArrayAdapter(getActivity().getApplicationContext(),
                Constants.dealManager.getDealList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_all_tab, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.dealList);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    /**
     * Attach to the corresponding activity, for this instance the EatDrinkActivity
     * @param activity
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
     * Detach from the corresponding activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * On item click to pass intent to the Single Deal Details
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Deal deal = Constants.dealManager.getDeal(position);
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
        public void onDealFragmentInteraction(String id);
    }
    /**
     * Custom ArrayAdapter to display deal details in a list view
     */
    private class DealArrayAdapter extends ArrayAdapter<Deal> {
        private final Context context;
        private final ArrayList<Deal> values;

        public DealArrayAdapter(Context context, ArrayList<Deal> values) {
            super(context, R.layout.customer_view_all_row, values);
            this.context = context;
            this.values = values;
        }

        /**
         * Customized view for different deals
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View rowView = inflater.inflate(R.layout.customer_view_all_row, parent, false);

            Deal deal = values.get(position);
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

            return rowView;
        }
    }
}
