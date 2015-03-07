package com.dinggoapplication.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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

    /*public void setValue(long value) {
        mPref.edit()
                .putLong(KEY_VALUE, value)
                .commit();
    }
    public void setValue(String key, ArrayList arrayList) {
        try {
            editor.putString(key, ObjectSerializer.serialize(currentTasks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getValue() {
        return mPref.getLong(KEY_VALUE, 0);
    }*/

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