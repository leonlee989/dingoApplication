/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by siungee on 25/02/15.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bootstrap initialization = new Bootstrap(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Nexa Light.otf")
            .setFontAttrId(com.dinggoapplication.R.attr.fontPath)
            .build()
        );
    }
}