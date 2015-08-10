/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dinggoapplication.R;
import com.dinggoapplication.Utils.LoginRegisterUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity class that executes activities within login page
 * <p>
 * Inflated layout that requires user authentication in order to gain access to the application for usage
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by leon on 10/2/2015.
 */
public class LoginRegistrationActivity extends Activity implements View.OnClickListener {

    /**
     * Called when the activity is starting.  This is where most initializationboss
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        // When login button is clicked
        findViewById(R.id.login).setOnClickListener(loginListener);
        findViewById(R.id.register).setOnClickListener(registerListener);
        findViewById(R.id.skip).setOnClickListener(skipListener);
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            EditText usernameControl = (EditText) findViewById(R.id.username);
            String username = usernameControl.getText().toString();

            // Determine customer or merchant activity to access
            //if (username.equalsIgnoreCase("customer")) {

            // Start activity for customer view
            Intent intent = new Intent(LoginRegistrationActivity.this, EatDrinkActivity.class);
            startActivity(intent);

            /*} else {
                // Toast box appear for invalid input
                Toast.makeText(LoginRegistrationActivity.this, "Invalid username/password.\nPlease try again",
                        Toast.LENGTH_LONG).show();
            }*/
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            try {
                EditText usernameText = (EditText) findViewById(R.id.username);
                if (LoginRegisterUtils.usernameIsExist(usernameText.getText().toString())) {

                    Toast.makeText(v.getContext(), "Registration", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Skip registration, allow user to trial use app with dinging related features blocked
     */
    private View.OnClickListener skipListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Skip Registration & use App", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.facebookText) {
            Toast.makeText(this, "Facebook Login", Toast.LENGTH_LONG).show();

            List<String> permissions = Arrays.asList(
                    "email",
                    "public_profile",
                    "user_friends"
            );

            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, facebookLoginCallBack);
        }
    }

    private LogInCallback facebookLoginCallBack = new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (e != null) {
                Log.e("Login", e.getMessage());
                return;
            }
            if (parseUser == null) {
                Log.d("DingGo", "The user have cancelled the Facebook login!");
            } else if (parseUser.isNew()) {
                Log.d("DingGo", "The user signed up and logged into DingGo through facebook");
            } else {
                Log.d("DingGo", "User logged into DingGo through facebook");
            }
        }
    };
    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Customization for the UI settings
     * @param newBase New context object for the UI
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
