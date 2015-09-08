/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggo.custom_ui;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Utility class for converting between density independent pixel (dp), pixel (px) and other
 * magical pixel units
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 09/03/15.
 */
public class PixelUtil {

    /**
     * Default constructor to initialize PixelUtil Object
     */
    private PixelUtil() {}

    /**
     * Conversion from density-independent pixel (dp) to physical pixel (px)
     * @param context   Application context that store application resources
     * @param dp        Integer value that contains the value for density-independent pixel (dp)
     * @return          Integer value that contains the converted value for physical pixel (px)
     */
    public static int dpToPx(Context context, int dp) {
        return Math.round(dp * getPixelScaleFactor(context));
    }

    /**
     * Conversion from physical pixel (px) to density-independent pixel (dp)
     * @param context   Application context that store application resources
     * @param px        Integer value that contain the value for physical pixel (px)
     * @return          Integer value that contain the converted value for density-independent pixel
     */
    public static int pxToDp(Context context, int px) {
        return Math.round(px / getPixelScaleFactor(context));
    }

    /**
     * Retrieve the pixel scale factor based on the resolution of the device
     * @param context   Application context that store application resources
     * @return          Float value that contains the pixel scale factor based on the resolution of the device
     */
    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}