package com.example.leon.dingoapplication.Fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leon.dingoapplication.Constants;
import com.example.leon.dingoapplication.Entity.Deal;
import com.example.leon.dingoapplication.Entity.Merchant;
import com.example.leon.dingoapplication.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CustomerViewAll extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * Fragment listener for the activity that calls this fragment
     */
    private OnFragmentInteractionListener mListener;

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
                Constants.merchantManager.getMerchantList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_all_tab, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
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
        Toast.makeText(getActivity(), "Attached", Toast.LENGTH_LONG).show();
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
        Toast.makeText(getActivity(), "Detached", Toast.LENGTH_LONG).show();
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
            Merchant merchant = Constants.merchantManager.getMerchant(position);
            mListener.onFragmentInteraction(merchant.getMerchantId());
            Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(),
                    Toast.LENGTH_LONG).show();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    /**
     * Custom ArrayAdapter to display deal details in a list view
     */
    private class DealArrayAdapter extends ArrayAdapter<Merchant> {
        private final Context context;
        private final ArrayList<Merchant> values;

        public DealArrayAdapter(Context context, ArrayList<Merchant> values) {
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
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.customer_view_all_row, parent, false);

            Merchant merchant = values.get(position);
            ImageView image = (ImageView) rowView.findViewById(R.id.merchantLogo);
            image.setImageBitmap(merchant.getImage());

            TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
            companyName.setText(merchant.getCompanyName());

            TextView merchantType = (TextView) rowView.findViewById(R.id.merchantType);
            merchantType.setText(merchant.getMerchantType());

            ArrayList<Deal> dealList = merchant.getDealList();
            for (Deal deal:dealList) {
                TextView dealTextView = new TextView(getActivity());
                dealTextView.setText(deal.toString());
                dealTextView.setTypeface(dealTextView.getTypeface(), Typeface.BOLD);
                LinearLayout container = (LinearLayout) rowView.findViewById(R.id.dealList);
                container.addView(dealTextView);
            }

            return rowView;
        }
    }
}
