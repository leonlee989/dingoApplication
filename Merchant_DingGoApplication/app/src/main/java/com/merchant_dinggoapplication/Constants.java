/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication;

import com.merchant_dinggoapplication.Manager.DealManager;
import com.merchant_dinggoapplication.Manager.MerchantManager;

/**
 * Constant class that contains global variable applied for the whole application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 16/2/2015.
 */
public class Constants {
    // Context set in Main Activity Class
    /** Merchant manager class that handles all merchant objects */
    public static MerchantManager merchantManager = new MerchantManager();
    /** Deal manager class that handles all deal objects */
    public static DealManager dealManager = new DealManager();

    // ToDo: To be removed
    public static boolean newItemAdded = false;
}
