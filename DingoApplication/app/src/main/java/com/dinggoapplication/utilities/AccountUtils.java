package com.dinggoapplication.utilities;

import com.parse.ParseUser;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 29/6/2015.
 */
public class AccountUtils {
    public static ParseUser getUser() {
        return ParseUser.getCurrentUser();
    }

    public static boolean isLogin() {
        return getUser() != null;
    }

    public static void logOut() {
        DeviceUtil.removeDevice();
        ParseUser.logOut();
    }
}
