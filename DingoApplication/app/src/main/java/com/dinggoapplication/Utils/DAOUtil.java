package com.dinggoapplication.utils;

import android.content.Context;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.CuisineType;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DealType;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 27/6/2015.
 */
public class DAOUtil {

    /**
     * Initialization of Parse database for the application
     * @param context   Context object that contains all resources in the application
     */
    public static void initialize(Context context) {
        // Registration of sub classes extended to Parse Objects
        registerSubClasses();

        // Initialize Crash Reporting
        ParseCrashReporting.enable(context);
        // Enabling local data store
        Parse.enableLocalDatastore(context);
        // Add DingGo initialization code
        Parse.initialize(context, Config.APPLICATION_ID, Config.CLIENT_ID);

        // Initialize Facebook Utilities
        ParseFacebookUtils.initialize(context);
        //ParseUser.enableAutomaticUser();
    }

    /**
     * Register all sub classes in the applications
     */
    public static void registerSubClasses() {
        ParseObject.registerSubclass(CuisineType.class);

        ParseObject.registerSubclass(Company.class);
        ParseObject.registerSubclass(Branch.class);

        ParseObject.registerSubclass(DealType.class);
        ParseObject.registerSubclass(Deal.class);
    }
 }
