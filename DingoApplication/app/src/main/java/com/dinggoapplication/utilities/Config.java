/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.utilities;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * Constant class that contains global variable applied for the whole application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 16/2/2015.
 */
public class Config {
    // Context set in Main Activity Class
    /** Standard date formatter */
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    /** Application Key for Parse Database */
    public static final String APPLICATION_ID = "knp8Hwmkxu7JoYA7fcZt6ABGT1Gw07W6Oyaxy4gn";
    /** Client Key */
    public static final String CLIENT_ID = "MJUWjMhwj5pUKaOCWXoyQOyhHphPYRe8PowhFjXU";

    /** Custom font path for the application */
    public static final String FONT_PATH = "fonts/Nexa Light.otf";
}
