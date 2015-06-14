/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Factory class that contains global methods that are used across the whole application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 26/2/2015.
 */
public class ApplicationFactory {

    /**
     * Method to retrieve the latitude and longitude of a location according to the address provided
     * @param strAddress    Address of the location
     * @param context       Application context
     * @return              LatLng object that contain the latitude and longitude of the location
     */
    public static LatLng getLocationFromAddress(String strAddress, Context context) {
        Geocoder geocoder = new Geocoder(context);
        LatLng latLng = null;
        List<Address> address;

        try {
            address = geocoder.getFromLocationName(strAddress, 1);

            if (address == null || address.size() <= 0) {
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