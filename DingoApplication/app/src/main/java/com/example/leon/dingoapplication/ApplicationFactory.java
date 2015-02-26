package com.example.leon.dingoapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Leon on 26/2/2015.
 */
public class ApplicationFactory {

    public static LatLng getLocationFromAddress(String strAddress, Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address = null;
        LatLng latLng = null;

        try {
            address = geocoder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }
}
