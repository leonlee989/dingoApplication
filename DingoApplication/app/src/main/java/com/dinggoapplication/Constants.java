package com.dinggoapplication;

import com.dinggoapplication.Manager.DealManager;
import com.dinggoapplication.Manager.MerchantManager;

import java.text.SimpleDateFormat;

/**
 * Created by Leon on 16/2/2015.
 */
public class Constants {
    // Context set in Main Activity Class
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static MerchantManager merchantManager = new MerchantManager();
    public static DealManager dealManager = new DealManager();

    // ToDo: To be removed
    public static boolean newItemAdded = false;
}
