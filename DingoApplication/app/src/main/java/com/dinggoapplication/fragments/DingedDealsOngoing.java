package com.dinggoapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dinggoapplication.R;
import com.dinggoapplication.activities.DingedDealDetailsActivity;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DingedDeal;
import com.dinggoapplication.managers.DealManager;
import com.dinggoapplication.managers.DingnedDealManager;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DingedDealsOngoing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 *          Created by Siu Ngee on 18/8/2015.
 */
public class DingedDealsOngoing extends Fragment  {

    /**
     * The fragment's ListView/GridView
     */
    private ListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with Views
     */
    private ListAdapter mAdapter;
    /**
     * All available deals in the system
     */
    private List<DingedDeal> dealList;
    /**
     * Fragment listener for the activity that calls this fragment
     */
    private OnFragmentInteractionListener mListener;

    private TextView emptyText;

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
        String status = "Not Redeemed";
        ParseUser user = ParseUser.getCurrentUser();
        DingnedDealManager dingnedDealManager = DingnedDealManager.getInstance();
        dealList = dingnedDealManager.getOngoingDeals(user, status);
//        Log.d("ONGOING DING SIZE", String.valueOf(dingedDeals.size()));
        Collections.reverse(dealList);
        Log.d("Deal List", String.valueOf(dealList.size()));
        mAdapter = new DealArrayAdapter(getActivity(), dealList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinged_deals_ongoing, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(R.id.dealList);
        emptyText = (TextView) view.findViewById(R.id.empty);
        if (dealList.size() == 0) {
            mListView.setVisibility(View.GONE);
            setEmptyText("You have no dinged deals at this moment.");
        }
        else {
            setEmptyText("");
        }
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                Log.d("Position", String.valueOf((dealList.size() - 1) - position));
                DingedDeal dingdeal = dealList.get((dealList.size() - 1) - position);
                Log.d("Ongoing Deal", String.valueOf(dingdeal.getConfirmationId()));

                Deal deal = dingdeal.getDealReferenceId();
                String refff = dingdeal.getDealReferenceId().getReferenceId();
                mListener.onFragmentInteraction(deal.getObjectId());
                Log.d("Deal Ref",deal.getReferenceId());
                //Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity().getBaseContext(), DingedDealDetailsActivity.class);
                intent.putExtra("deal_referenceCode", deal.getObjectId());
                intent.putExtra("dingdeal_confirmationId",dingdeal.getConfirmationId());
                startActivity(intent);
            }
        });

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
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            DingedDeal deal = this.dealList.get((this.dealList.size() - 1) - position);
//
//            mListener.onFragmentInteraction(deal.getReferenceId());
//            //Toast.makeText(getActivity(), merchant.getMerchantId() + " : " + merchant.getCompanyName(), Toast.LENGTH_LONG).show();
//
//            Intent intent = new Intent(getActivity().getBaseContext(), DingedDealDetailsActivity.class);
//            intent.putExtra("deal_referenceCode", deal.getReferenceId());
//            startActivity(intent);
//        }
//    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     *
     * @param emptyText Text to display if the list is empty
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
         *
         * @param id ID of the fragment to be identify in the activity class
         */
        void onFragmentInteraction(String id);
    }

    /**
     * Custom ArrayAdapter to display deal details in a list view
     */
    private class DealArrayAdapter extends ArrayAdapter<DingedDeal> {
        /**
         * Context object that stores all the resources
         */
        private final Context context;
        /**
         * A list of deals to be displayed onto the list view
         */
        private final List<DingedDeal> dealList;

        /**
         * Constructor that initialize DealArrayAdapter with the following parameters
         *
         * @param context  Context object that stores all the resources
         * @param dealList A list of deals to be displayed onto the list view
         */
        public DealArrayAdapter(Context context, List<DingedDeal> dealList) {
            super(context, R.layout.deal_view_row, dealList);
            this.context = context;
            this.dealList = dealList;
        }

        /**
         * {@inheritDoc}
         * Customized view for different deals
         *
         * @param position    Position/index of the row in the list view
         * @param convertView Customized row layout on the list view
         * @param parent      Parent element of this view
         * @return View object that represents one of the row in the list view
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View rowView = inflater.inflate(R.layout.deal_view_row, parent, false);

            try {
                DingedDeal dingdeal = dealList.get((dealList.size() - 1) - position);
                Deal refIdDing = dingdeal.getDealReferenceId();
                DealManager dealManager = DealManager.getInstance();
                Deal deal = dealManager.getDeal(refIdDing.getReferenceId());
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

//                TODO set to timeLeft
                additionInfo.setText("500 m");


            } catch (ParseException e) {
                e.printStackTrace();
            }

            return rowView;
        }
    }

}
