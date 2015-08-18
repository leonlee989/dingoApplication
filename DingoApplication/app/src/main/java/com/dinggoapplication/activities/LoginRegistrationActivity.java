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
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dinggoapplication.R;
import com.dinggoapplication.utilities.LoginRegisterUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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

    private EditText txtUsername;
    private EditText txtPassword;
    Context mContext;
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

        // Entered username and password by users
        txtUsername = (EditText) findViewById(R.id.username);
        txtPassword = (EditText) findViewById(R.id.password);

        // When login button is clicked
        findViewById(R.id.login).setOnClickListener(loginListener);
        findViewById(R.id.register).setOnClickListener(registerListener);
        findViewById(R.id.skip).setOnClickListener(skipListener);
        mContext = this;
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            String username = "seahsiungee";
            String password = "1234567";

            if (editTextExceptionHandler(username, password)) {
                LoginRegisterUtils.loadingStart(mContext);
                // TODO: Login in by email address
                if (username.contains("@")) {

                } else {
                    // Login by user name
                    ParseUser.logInInBackground(username, password, manualLoginCallBack);
                }
            }
        }
    };

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

    protected void loginSuccess() {
        showToast(R.string.login_success_toast);

        // Start activity for customer view
        Intent intent = new Intent(LoginRegistrationActivity.this, EatDrinkActivity.class);
        startActivity(intent);
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

    /*
    protected void bootstrapReviewObjects() {
        DealManager dealManager = DealManager.getInstance();

        Random randomGenerator = new Random();
        for (Deal deal : dealManager.getDealList()) {
            Company company = deal.getBranch().getCompany();

            ParseObject review = new ParseObject("review");
            review.put("user", ParseUser.getCurrentUser());
            review.put("referenceId", deal);
            review.put("companyId", company);
            review.put("food_drink", randomGenerator.nextInt(4) + 2);
            review.put("value", randomGenerator.nextInt(4) + 2);
            review.put("ambience", randomGenerator.nextInt(4) + 2);
            review.put("service", randomGenerator.nextInt(4) + 2);
            review.put("comments", randomComments(randomGenerator.nextInt(10)));

            review.saveInBackground();
        }
    }

    public String randomComments(int index) {
        switch (index) {
            case 0:
                return "Their service is great, serving their customers with utmost respect and " +
                        "courtesy. Their staffs are very friendly and lovely. More importantly, " +
                        "their served great good that is really worth the amount!";
            case 1:
                return "Deal is very reasonable and affordable! I am glad I am able to grab the deal " +
                        "so promptly to enjoy a great lunch! The serving portion is good and the " +
                        "food is wonderful.";
            case 2:
                return "The ambiance in the restaurant is amazing! The place is cosy and relaxing, " +
                        "and they serve delicious food which really made my day after a long day of " +
                        "work! It will be worth going there for a good meal with or without any deals.";
            case 3:
                return "The food here is value for money and is worth going again. The food is great, " +
                        "good ambiance, lovely staff and wonderful service.";
            case 4:
                return "This charming little restaurant has little to offer on the menu, which make " +
                        "selection a breeze! Food is simple but great!";
            case 5:
                return "This restaurant is overpriced and overrated. Although some of their food is tasty, " +
                        "but most are too oily for consumption. Will not go again since i don't think" +
                        "it was a value for money lunch.";
            case 6:
                return "The food are more than ok, and it has done its best to combine itself with the " +
                        "classy environment around it. The service is v good.  The restaurant is with " +
                        "waiters and waitresses and they have been doing a good job here.";
            case 7:
                return "Great food, nice ambience, and attentive and friendly staff!";
            case 8:
                return "I find out the food is very delicious (not salty and not too sweet). Waiter " +
                        "and waitress also friendly and good service). Price is very reasonable. " +
                        "Restaurant's decoration also very peaceful and relaxing. When my friends " +
                        "pay the bills is no service charge and no GST. So I will be coming back " +
                        "this restaurant with my family.";
            case 9:
                return "Awesome place!";
            default:
                return "";
        }
    }
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
                showToast(R.string.login_failed_unknown_toast);
        }
    }
    protected void showToast(int id) {
        showToast(getString(id));
    }

    protected void showToast(CharSequence text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

}
