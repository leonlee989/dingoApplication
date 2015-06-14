/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dinggoapplication.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Manager class that handles shared preference for the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 06/03/15.
 */
public class PreferencesManager {
    /** Name of the preferences */
    private static final String PREF_NAME = "com.example.app.PREF_NAME";
    /** Instance of Preference Manager Class */
    private static PreferencesManager sInstance;
    /** SharedPreferences object for the application */
    private final SharedPreferences mPref;
    /** Editor object that handle amendments of data in the shared preferences */
    private final Editor editor;

    /**
     * Constructor that initialized PreferenceManager with the following parameters
     * @param context   Application context
     */
    @SuppressLint("CommitPrefEdits")
    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mPref.edit();
    }

    /**
     * Initialization of PreferenceManage object
     * @param context   Application context
     */
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    /**
     * Retrieve PreferenceManager instance
     * @return  PreferenceManager Object which is initialized during runtime
     */
    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    /**
     * Retrieve shared preferences object
     * @return  SharedPreferences object
     */
    public synchronized SharedPreferences getSPInstance() {
        return mPref;
    }

    /**
     * Set value in the shared preferences object
     * @param key           String value that contains the identifier for the stored data
     * @param arrayList     List of objects to be stored in shared preferences
     */
    public void setValue(String key, ArrayList arrayList) {
        try {
            editor.putString(key, ObjectSerializer.serialize(arrayList))
                    .commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value in the shared preferences object
     * @param key           String value that contains the identifier for the stored data
     * @param arrayList     A hash of object to be stored in the shared preferences
     */
    public void setValue(String key, LinkedHashMap arrayList) {
        try {
            editor.putString(key, ObjectSerializer.serialize(arrayList))
                    .commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public Object getValue(String key) {
        Object object = new Object();
        try {
            object = (Object) ObjectSerializer.deserialize(mPref.getString(key, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }*/

    /**
     * Retrieve a hash of object by the respective identifier
     * @param key   String value that contains the identifier of the stored data
     * @return      A hash of objects stored in the Shared Preferences
     */
    public LinkedHashMap getValue(String key) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap = (LinkedHashMap) ObjectSerializer.deserialize(mPref.getString(key, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

    /**
     * Remove data from the Shared Preferences
     * @param key   String value that contains the identifier of the stored data
     */
    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .apply();
    }

    /**
     * Clear all data in the shared preferences
     * @return  Status on the clearing of data
     */
    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}