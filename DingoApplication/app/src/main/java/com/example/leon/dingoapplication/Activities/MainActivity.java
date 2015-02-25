package com.example.leon.dingoapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.example.leon.dingoapplication.Bootstrap;
import com.example.leon.dingoapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity {

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

                    // Start activity for merchant view
                    Intent intent = new Intent(MainActivity.this, MerchantActivity.class);
                    startActivity(intent);

                } else {
                    // Toast box appear for invalid input
                }
            }
        });
        // Demonstration of a collection-browsing activity
        findViewById(R.id.collection_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });

        //Demonstration of navigating to external activities
        findViewById(R.id.external_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /*
                    Create an intent that ask user to pick a photo, but using
                    FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                    the application from the device home screen does not return
                    to the external activity
                    */
                Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                externalActivityIntent.setType("image/*");
                externalActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(externalActivityIntent);

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
