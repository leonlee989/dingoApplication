package com.dinggoapplication.Fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dinggoapplication.Constants;
import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerViewMap.OnMapFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CustomerViewMap extends Fragment {

    private OnMapFragmentInteractionListener mListener;
    private GoogleMap mMap;

    public CustomerViewMap() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = (RelativeLayout) inflater.inflate(R.layout.customer_view_map, container, false);

        initializeMap();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMapFragmentInteractionListener) activity;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnMapFragmentInteractionListener {
        public void onMapFragmentInteraction();
    }

    /**
     * Sets up the map if it is possible to do so if the map has not already been instantiated.
     * This will ensure that we only ever call {@link #setUpMap()} once when {@link #mMap}
     * is not null.
     */
    public void initializeMap() {

        // Do a null check to confirm that map has not been instantiated
        if (mMap == null) {

            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Setting up of map, which include performing features such as marker, lines or listener
     * to enhance the use of map
     */
    public void setUpMap() {
        // enabling location marking
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);

        // Set current location tracking on map view
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        setAllDeals();
    }

    /**
     * Listener for any change in current location
     * New lat lng is provided on updated location and passed into animateCamera
     */
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
            }
        }
    };

    public void setAllDeals() {
        // Sample displaying
        ArrayList<Deal> dealList = Constants.dealManager.getDealList();

        for (Deal deal: dealList) {
            Merchant merchant = deal.getMerchant();

            mMap.addMarker(new MarkerOptions()
                    .position(merchant.getLatLng())
                    .title(merchant.getCompanyName())
                    .snippet(deal.toString())
                    .icon(BitmapDescriptorFactory.fromBitmap(merchant.getImage())));
        }
    }
}
