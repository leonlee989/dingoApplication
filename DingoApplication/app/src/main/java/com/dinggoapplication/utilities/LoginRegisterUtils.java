package com.dinggoapplication.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dinggoapplication.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Utility class that assist operations in user login and registrations
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 20/7/2015.
 */
public class LoginRegisterUtils {
    private final static String LOG_TAG = "Login";

    /** Request for friend list */
    private static boolean FB_REQUEST_USER_FRIENDS = false,
    /** Request permission to publish post or share */
            FB_REQUEST_PUBLISH_ACTIONS = false,
    /** Request information about user: Require review from facebook */
            FB_REQUEST_ABOUT_ME = false,
    /** Access the date and month of a person's birthday: Require review from facebook */
            FB_REQUEST_BIRTHDAY = false,
    /** Provides access to a person's current city: Require review from facebook */
            FB_REQUEST_LOCATION = false,
    /** Provides access to the photos a person has uploaded or been tagged in: Require review from facebook */
            FB_REQUEST_PHOTO = false;

    private static ProgressDialog progress;

    public static String getLogTag() {
        return LOG_TAG;
    }

    public static void loadingStart(Context context) {
        progress = new ProgressDialog(context, R.style.loadingTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
    }

    public static void loadingFinish() {
        progress.dismiss();
    }

    public static List<String> getFacebookPermissions() {
        List<String> permissions =  Arrays.asList("email", "public_profile");

        if (FB_REQUEST_USER_FRIENDS) permissions.add("user_friends");
        if (FB_REQUEST_PUBLISH_ACTIONS) permissions.add("publish_actions");
        if (FB_REQUEST_ABOUT_ME) permissions.add("user_about_me");
        if (FB_REQUEST_BIRTHDAY) permissions.add("user_birthday");
        if (FB_REQUEST_LOCATION) permissions.add("user_location");
        if (FB_REQUEST_PHOTO) permissions.add("user_photos");

        return permissions;
    }

    public static void fbRequestUserFriends(boolean hasAccess) {
        FB_REQUEST_USER_FRIENDS = hasAccess;
    }

    public static void fbRequestPublishActions(boolean hasAccess) {
        FB_REQUEST_PUBLISH_ACTIONS = hasAccess;
    }

    public static void fbFbRequestAboutMe(boolean hasAccess) {
        FB_REQUEST_ABOUT_ME = hasAccess;
    }

    public static void fbFbRequestBirthday(boolean hasAccess) {
        FB_REQUEST_BIRTHDAY = hasAccess;
    }

    public static void fbFbRequestLocation(boolean hasAccess){
        FB_REQUEST_LOCATION = hasAccess;
    }

    public static void fbFbRequestPhoto(boolean hasAccess) {
        FB_REQUEST_PHOTO = hasAccess;
    }

    public static void fbSaveUserProfile(final ParseUser user, final ProfileAccess... profileAccessList) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        try {
                            if (graphResponse.getError() == null) {
                                for (ProfileAccess profileAccess : profileAccessList) {
                                    String value = jsonObject.getString(profileAccess.mapper);
                                    if (value != null && !value.equalsIgnoreCase("")) {
                                        user.put(profileAccess.mapper, value);
                                    } else {
                                        Log.e(getLogTag(), "Unable to get " + profileAccess.mapper + " by graph request");
                                    }
                                }

                                setOtherInfo(user);
                                user.saveInBackground();
                            } else {
                                Log.e(getLogTag(), graphResponse.getError().getErrorMessage());
                            }
                        } catch (JSONException e) {
                            Log.e(getLogTag(), e.getMessage());
                        }
                    }
                });

        String graphRequestList = "";
        for (int i = 0; i < profileAccessList.length; i++) {
            graphRequestList += profileAccessList[i].mapper;

            if (i < profileAccessList.length-1) graphRequestList += ",";
        }

        Bundle parameters = new Bundle();
        parameters.putString("fields", graphRequestList);
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public static void setOtherInfo(ParseUser user) {
        user.put("latitude", 103.234354);
        user.put("longitude", 1.943892);
        user.put("lastVisited", new Date().getTime());
    }

    public enum ProfileAccess {
        ID("id"), NAME("name"), FIRST_NAME("first_name"), MIDDLE__NAME("middle_name"), LAST_NAME("last_name"),
        IS_VERIFIED("is_verified"), GENDER("gender"), FB_LINK("link"), COVER_IMG("cover"), EMAIL("email"),
        ADDRESS("address"), AGE_RANGE("age_range"), BIRTHDAY("birthday"), LOCATION("location"), ABOUT_ME("about");

        private final static LinkedHashMap<String, ProfileAccess> map = new LinkedHashMap<>(ProfileAccess.values().length, 1.0f);
        static {
            for (ProfileAccess profileAccess : ProfileAccess.values()) {
                map.put(profileAccess.mapper, profileAccess);
            }
        }

        private final String mapper;

        ProfileAccess(String mapper) {
            this.mapper = mapper;
        }

        private static ProfileAccess of(String mapper) {
            ProfileAccess profileAccess = map.get(mapper);
            if (profileAccess == null) {
                throw new IllegalArgumentException("No profile field defined in the application");
            }

            return profileAccess;
        }
    }
}
