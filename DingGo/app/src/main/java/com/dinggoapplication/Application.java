/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import com.dinggoapplication.managers.PreferencesManager;
import com.dinggoapplication.utilities.Config;
import com.dinggoapplication.utilities.DAOUtil;
import com.parse.ParseInstallation;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Application class that customize android application class to cater for DingGo functionality
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 25/02/15.
 */
public class Application extends android.app.Application {

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Instantiation of Parse Database
        DAOUtil.initialize(this);

        /*
        ParseUser parseUser = ParseUser.getCurrentUser();
            if (parseUser != null) {
                parseUser.logOut();
            }
        */

        PreferencesManager.initializeInstance(getApplicationContext());

        // Bootstrapping
        Bootstrap bootstrapping = new Bootstrap(this);
        bootstrapping.execute(false);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Initializing custom font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(Config.FONT_PATH)
                        .setFontAttrId(com.dinggoapplication.R.attr.fontPath)
                        .build()
        );

    }

}