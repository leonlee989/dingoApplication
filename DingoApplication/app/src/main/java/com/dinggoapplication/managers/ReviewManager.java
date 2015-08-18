package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Company;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version x.x
 * Created by Leon on 17/8/2015.
 */
public class ReviewManager {
    private static ReviewManager instance;
    private static int checkout;
    private final static String PARSE_NAME = "review";

    private ReviewManager() {}

    public static ReviewManager getInstance() {
        synchronized (ReviewManager.class) {
            if (instance == null) {
                instance = new ReviewManager();
            }
        }

        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) {
            checkout++;
        }

        return instance;
    }

    public int getCheckout() {
        return checkout;
    }

    public void retrieveReviews(final Company company) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("companyId", company);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    Log.e("Deal", "Unable to find deals from Parse Database:\n" + e.getMessage());
                    return;
                }
                // Cache new results
                ParseObject.pinAllInBackground(company.getCompanyID(), parseObjects);
                //afterQueryProcessing(parseObjects);
            }
        });
        //ParseObject.pinAllInBackground(company.getCompanyID(), query.find());

        ParseQuery<ParseObject> queryCache = ParseQuery.getQuery(PARSE_NAME);
        queryCache.fromLocalDatastore()
                .whereEqualTo("companyId", company)
                .findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            Log.d("Review", "Size of the list: " + list.size());

                            if (list.isEmpty()) {

                            } else {
                                for (ParseObject objects : list) {
                                    Log.d("Review", objects.getString("comments"));
                                }
                            }
                        } else {
                            Log.e("Review", e.getMessage());
                        }
                    }
                });
    }
}
