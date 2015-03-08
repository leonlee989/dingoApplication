package com.dinggoapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dinggoapplication.Bootstrap;
import com.dinggoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Constant Object
        Bootstrap initialization = new Bootstrap(this);
        setContentView(R.layout.activity_main);

        // When login button is clicked
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameControl = (EditText) findViewById(R.id.username);
                String username = usernameControl.getText().toString();

                // Determine customer or merchant activity to access
                if (username.equalsIgnoreCase("customer")) {

                    // Start activity for customer view
                    Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                    startActivity(intent);

                } else if (username.equalsIgnoreCase("merchant")) {

                    SharedPreferences sharedPreferences = getSharedPreferences("MerchantData", Context.MODE_PRIVATE);

                    // Todo: Predefine MerchantZ
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("merchantId", "merchant03");
                    editor.commit();

                    // Start activity for merchant view
                    Intent intent = new Intent(MainActivity.this, MerchantActivity.class);
                    startActivity(intent);

                } else {
                    // Toast box appear for invalid input
                    Toast.makeText(MainActivity.this, "Invalid username/password.\nPlease try again",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Demonstration of navigating to external activities
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
        /*
            Create an intent that ask user to pick a photo, but using
            FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
            the application from the device home screen does not return
            to the external activity
        */
            /*
            Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
            externalActivityIntent.setType("image/*");
            externalActivityIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(externalActivityIntent);
            */
            register();

        } else if (v.getId() == R.id.facebookText) {
            facebookLogin();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * ToDo: Register a new account with a new layout
     */
    public void register() {
        Toast.makeText(this, "Register an account", Toast.LENGTH_LONG).show();
    }

    /**
     * ToDo: Facebook integration to cater for facebook login
     */
    public void facebookLogin() {
        Toast.makeText(this, "Facebook Login", Toast.LENGTH_LONG).show();
    }
}
