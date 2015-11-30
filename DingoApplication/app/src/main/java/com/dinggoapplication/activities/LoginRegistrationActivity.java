/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dinggoapplication.R;
import com.dinggoapplication.utilities.AccountUtils;
import com.dinggoapplication.utilities.DeviceUtil;
import com.dinggoapplication.utilities.LoginRegisterUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
    /** Edit text control that contains user name */
    private EditText txtUsername;
    /** Edit text control that contains user's password */
    private EditText txtPassword;
    /** Context of the application */
    Context mContext;

    String userId;
    public static final String MyPREFERENCES = "MyPrefs" ;

    /**
     * Called when the activity is starting.  This is where most initialization
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

        if (!AccountUtils.isLogin()) {
            setContentView(R.layout.activity_login_registration);

            // Entered username and password by users
            txtUsername = (EditText) findViewById(R.id.username);
            txtPassword = (EditText) findViewById(R.id.password);

            // When login button is clicked
            findViewById(R.id.login).setOnClickListener(loginListener);
            findViewById(R.id.register).setOnClickListener(registerListener);
            findViewById(R.id.skip).setOnClickListener(skipListener);
            mContext = this;
        } else {
            loginSuccess();
        }
    }

    /** Login listener for user to access the application */
    private View.OnClickListener loginListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            //String username = "seahsiungee";
            //String password = "1234567";

            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            Log.d(LoginRegistrationActivity.class.getName(), username);
            if (editTextExceptionHandler(username, password)) {
                LoginRegisterUtils.loadingStart(mContext);

                if (username.contains("@")) {
                    // Login by email address
                    logInByEmail(username, password);
                } else {
                    // Login by user name
                    ParseUser.logInInBackground(username, password, manualLoginCallBack);
                }

            }
        }
    };

    /**
     * Login user by their email address
     * @param username  String that contains email address
     * @param password  String that contains user password
     */
    public void logInByEmail(String username, String password) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", username);

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        try {
            List<ParseUser> userList = query.find();

            if (userList.size() != 0) {
                ParseUser parseUser = userList.get(0);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                userId = parseUser.getObjectId();
                Log.d("UserNameID",userId);
                editor.putString("USERID", userId);
                editor.commit();
                ParseUser.logInInBackground(parseUser.getUsername(), password, manualLoginCallBack);
            } else {
                LoginRegisterUtils.loadingFinish();
                showToast(R.string.sign_up_failed_invalid_email);
            }

        } catch (ParseException e) {
            parseExceptionHandler(e.getCode());
        }
    }

    /** Login call back for user to access the application */
    public LogInCallback manualLoginCallBack = new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            LoginRegisterUtils.loadingFinish();

            if (parseUser != null) {
                //bootstrapReviewObjects();
                loginSuccess();
            } else {
                if (e != null) {
                    Log.e(LoginRegisterUtils.getLogTag(), e.toString());
                    parseExceptionHandler(e.getCode());
                    txtPassword.selectAll();
                    txtPassword.requestFocus();
                } else {
                    showToast(R.string.login_failed_unknown_toast);
                }
            }
        }
    };

    /** Register listener for user to register a account for login access */
    private View.OnClickListener registerListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            String email = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (editTextExceptionHandler(email, password)) {
                ParseUser parseUser = new ParseUser();

                // Set values for user's account
                if (email.contains("@")) {
                    parseUser.setEmail(email);
                    parseUser.setUsername(email.substring(0, email.indexOf("@")));
                    parseUser.put("name", email.substring(0, email.indexOf("@")));
                    parseUser.setPassword(password);
                    LoginRegisterUtils.setOtherInfo(parseUser);

                    LoginRegisterUtils.loadingStart(mContext);
                    parseUser.signUpInBackground(manualSignUpCallBack);
                } else {
                    showToast(R.string.sign_up_failed_invalid_email);
                }
            }
        }
    };

    /** Manual sign up call back for user to register a account for login access */
    private SignUpCallback manualSignUpCallBack = new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            LoginRegisterUtils.loadingFinish();

            if (e == null) {
                // TODO: Send email for verifications
                loginSuccess();
            } else parseExceptionHandler(e.getCode());

        }
    };

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.facebookText)
            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, LoginRegisterUtils.getFacebookPermissions(), facebookLoginCallBack);
    }

    /** Facebook log in call back for user to log into the account using facebook account */
    private LogInCallback facebookLoginCallBack = new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (e != null) {
                Log.e(LoginRegisterUtils.getLogTag(), e.getMessage());
                return;
            }

            if (parseUser == null) {
                Log.d("Login", "The user have cancelled the Facebook login!");
            } else if (parseUser.isNew()) {
                Log.d("Login", "The user signed up and logged into DingGo through facebook");

                LoginRegisterUtils.fbSaveUserProfile(parseUser,
                        LoginRegisterUtils.ProfileAccess.NAME,
                        LoginRegisterUtils.ProfileAccess.EMAIL
                );

                loginSuccess();
            } else {
                Log.d("DingGo", "User logged into DingGo through facebook");
                //bootstrapReviewObjects();
                loginSuccess();
            }
        }
    };

    /** Skip registration, allow user to trial use app with dinging related features blocked */
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
     * Method to execute upon success login, allowing user to access the Eat and Drink Activity
     */
    protected void loginSuccess() {
        DeviceUtil.installDevice(getApplicationContext());
        showToast(R.string.login_success_toast);
        // Start activity for customer view
        Intent intent = new Intent(LoginRegistrationActivity.this, EatDrinkActivity.class);
        startActivity(intent);
        finish();
    }

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

    /**
     * Exception handling for user input to log into the application
     * @param username  String value that contains the username
     * @param password  String value that contains user's password
     * @return          Boolean value that determines is user input valid for either login or register
     */
    protected boolean editTextExceptionHandler(String username, String password) {
        boolean isValid = true;
        Resources resources = getResources();

        // Field validations
        if (username.length() == 0) {
            isValid = false;
            showToast(R.string.no_username_toast);
        } else if (password.length() == 0) {
            isValid = false;
            showToast(R.string.no_password_toast);
        } else if (password.length() < resources.getInteger(R.integer.password_min_char)){
            isValid = false;
            showToast(R.string.length_of_password_toast);
        }

        return isValid;
    }

    /**
     * Parse Database exception handling for different error code
     * @param errorCode Integer value that contains the error code that represents a particular Parse Error
     */
    protected void parseExceptionHandler(int errorCode) {
        switch (errorCode) {
            case ParseException.OBJECT_NOT_FOUND:
                showToast(R.string.login_failed_invalid_credential_toast);
                break;
            case ParseException.INVALID_EMAIL_ADDRESS:
                showToast(R.string.sign_up_failed_invalid_email);
                break;
            case ParseException.USERNAME_TAKEN:
                showToast(R.string.sign_up_failed_username_taken);
                break;
            case ParseException.EMAIL_TAKEN:
                showToast(R.string.sign_up_failed_email_taken);
                break;
            case ParseException.CONNECTION_FAILED:
                showToast(R.string.login_failed_server_error_toast);
                break;
            default:
                Log.d("Error", "" + errorCode);
                showToast(R.string.login_failed_unknown_toast);
        }
    }

    /**
     * Show toast box in the application
     * @param id    ID of the resource string
     */
    protected void showToast(int id) {
        showToast(getString(id));
    }

    /**
     * Show toast box in the application
     * @param text  Text to display in the toast
     */
    protected void showToast(CharSequence text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
