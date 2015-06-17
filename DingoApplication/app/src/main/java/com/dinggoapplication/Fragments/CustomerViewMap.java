/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 18/02/15.
 */
public class CustomerViewMap extends Fragment {
    /** Fragment listener for the activity that calls this fragment */
    private OnMapFragmentInteractionListener mListener;
    /** GoogleMap object that handles geo services from Google */
    private GoogleMap mMap;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerViewMap() {}

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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_view_map, container, false);

        initializeMap();
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
            mListener = (OnMapFragmentInteractionListener) activity;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnMapFragmentInteractionListener {
        /**
         * This method allows interactions with the activity class who implements this listener by its id
         */
        void onMapFragmentInteraction();
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
        /**
         * Upon changing of the coordinates of user location
         * @param location  New location on Google Map
         */
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
            }
        }
    };

    /**
     * Adding image and information with regards to every single deals onto the map
     */
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