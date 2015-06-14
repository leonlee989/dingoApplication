/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import com.dinggoapplication.Manager.DealManager;
import com.dinggoapplication.Manager.MerchantManager;
import com.dinggoapplication.Manager.PreferencesManager;

import java.text.SimpleDateFormat;

/**
 * Constant class that contains global variable applied for the whole application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 16/2/2015.
 */
public class Constants {
    // Context set in Main Activity Class
    /** Standard date formatter */
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    /** Merchant manager class that handles all merchant objects */
    public static MerchantManager merchantManager = new MerchantManager();
    /** Deal manager class that handles all deal objects */
    public static DealManager dealManager = new DealManager();
    /** Preference manager class that manage shared preferences */
    public static PreferencesManager preferencesManager;

    // ToDo: To be removed
    public static boolean newItemAdded = false;
}
