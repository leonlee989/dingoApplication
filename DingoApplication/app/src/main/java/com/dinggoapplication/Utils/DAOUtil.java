package com.dinggoapplication.Utils;

import android.content.Context;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.CuisineType;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DealType;
import com.parse.Parse;
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
        registerSubClasses();
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, Config.APPLICATION_ID, Config.CLIENT_ID);
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
