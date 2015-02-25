package com.example.leon.dingoapplication;

import android.content.Context;
import android.graphics.Typeface;
import com.example.leon.dingoapplication.Manager.MerchantManager;

import java.text.SimpleDateFormat;

/**
 * Created by Leon on 16/2/2015.
 */
public class Constants {
    // Context set in Main Activity Class
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static MerchantManager merchantManager = new MerchantManager();
}
