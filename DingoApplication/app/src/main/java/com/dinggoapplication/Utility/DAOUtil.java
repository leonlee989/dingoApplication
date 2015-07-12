package com.dinggoapplication.Utility;

import android.content.Context;
import android.util.Log;

import com.dinggoapplication.Config;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 27/6/2015.
 */
public class DAOUtil {

    public static void initialize(Context context) {
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, Config.APPLICATION_ID, Config.CLIENT_ID);
    }

    public static void insertObj(final String tableName, HashMap<String, Object> values) {
        ParseObject parseObject = new ParseObject(tableName);

        for (Map.Entry<String, Object> value : values.entrySet()) {
            parseObject.put(value.getKey(), value.getValue());
        }

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Database", tableName + " table is updated");
                } else {
                    Log.d("Database", tableName + " table update error: " + e.getMessage());
                }
            }
        });
    }

    public static ParseObject getObject(final String tableName, String objectId) throws Exception {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        final ParseObject[] obj = {null};

        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Log.d("Database", tableName + " object is found");
                } else {
                    Log.d("Database", "Error: " + e.getMessage());
                }
            }
        });
        return obj[0];
    }
 }
