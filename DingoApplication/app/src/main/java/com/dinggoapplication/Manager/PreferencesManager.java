package com.dinggoapplication.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dinggoapplication.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by siungee on 06/03/15.
 */

public class PreferencesManager {

    private static final String PREF_NAME = "com.example.app.PREF_NAME";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;
    private final Editor editor;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mPref.edit();
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }
    public synchronized SharedPreferences getSPInstance() {
        return mPref;
    }

    public void setValue(String key, ArrayList arrayList) {
        try {
            editor.putString(key, ObjectSerializer.serialize(arrayList))
                    .commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    public LinkedHashMap getValue(String key) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap = (LinkedHashMap) ObjectSerializer.deserialize(mPref.getString(key, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

}