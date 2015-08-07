package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 2/8/2015.
 */
@SuppressWarnings("SynchronizeOnNonFinalField")
public class DealManager {
    private static DealManager instance;
    private static int checkOut = 0;
    private final static String parseClassName = "deal";

    private HashMap<String, Deal> dealList;

    private DealManager() {
        dealList = new HashMap<>();
    }

    public static DealManager getInstance() {
        synchronized (DealManager.class) {
            if(instance == null) {
                instance = new DealManager();
            }
        }

        synchronized (instance) {
            checkOut++;
        }

        return instance;
    }

    public int getCheckOut() {
        return checkOut;
    }

    public void updateCacheList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    Log.e("Deal", "Unable to find deals from Parse Database");
                    return;
                }

                // Remove previously cache data
                ParseObject.unpinAllInBackground("dealList", new DeleteCallback() {

                    @Override
                    public void done(ParseException e) {
                        // Cache new results
                        ParseObject.pinAllInBackground("dealList", parseObjects);
                        afterQueryProcessing(parseObjects);
                    }
                });
            }
        });
    }

    public void getFromCache() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);

        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Log.e("Deal", "Unable to find deals from Local Database");
                    return;
                }

                afterQueryProcessing(list);
            }
        });
    }


    public void afterQueryProcessing(List<ParseObject> parseObjects) {
        for (ParseObject parseObject : parseObjects) {
            Deal deal = (Deal) parseObject;
            fetchRegionalDataIfNeeded(deal);
            this.dealList.put(deal.getReferenceId(), deal);
        }

        Log.d("Deal", "Caching of data is completed!");
    }

    public void fetchRegionalDataIfNeeded(Deal deal) {
        try {
            Branch branch;
            if (!deal.getBranch().isDataAvailable()) {
                branch = deal.getBranch().fetchIfNeeded();
            } else {
                branch = deal.getBranch();
            }

            if (branch != null) {
                Company company;
                if (!branch.getCompany().isDataAvailable()) {
                    company = branch.getCompany().fetchIfNeeded();
                } else {
                    company = branch.getCompany();
                }

                if (company != null) {
                    if (!company.getCuisineType().isDataAvailable()) {
                        company.getCuisineType().fetchIfNeeded();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Deal> getDealList() {
        return new ArrayList<>(dealList.values());
    }

    public Deal getDeal(String referenceId) {
        return dealList.get(referenceId);
    }
}
