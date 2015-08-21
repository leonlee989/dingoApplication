package com.dinggoapplication.managers;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Deal Manager class that handles all deals in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 2/8/2015.
 */
@SuppressWarnings("SynchronizeOnNonFinalField")
public class DealManager {
    /** Singleton instance of Deal Manager Object */
    private static DealManager instance;
    /** Number of checked out for the instance */
    private static int checkOut = 0;
    /** The name of the object the manager class is handling */
    private final static String parseClassName = Deal.TABLE_NAME;

    /**
     * Default constructor for Deal Manager class
     */
    private DealManager() {}

    /**
     * Singleton handler that retrieves the single instance of DealManager object
     * @return  Single instance of DealManager Object
     */
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

    /**
     * Retrieve the number of checkouts for the DealManager Object
     * @return  Integer value that contains the number of checkouts for the DealManager Object
     */
    public int getCheckOut() {
        return checkOut;
    }

    /**
     * Update the deal list and the local database pulled from Parse database according to user's
     * settings and preferences
     */
    public HashMap<String, Deal> updateCacheList() throws ParseException {
        //Handler mHandler = new Handler(Looper.myLooper());
        //ExecutorService executorService = Executors.newFixedThreadPool(1);
        //final Runnable worker = new UpdateCache();
        //mHandler.post(worker);
        //executorService.execute(worker);

        //while (!executorService.isTerminated()) {}
        //activity.runOnUiThread(worker);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);
                    Deal.pinAll(query.find());
                    Log.d("CustomerViewAll", "Deal Retrieved");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return getFromCache();

        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    Log.e("Deal", "Unable to find deals from Parse Database:\n" + e.getMessage());
                    return;
                }
                // Remove previously cache data
                ParseObject.unpinAllInBackground("dealList", new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        // Cache new results
                        ParseObject.pinAllInBackground("dealList", parseObjects);

                        Log.d("CustomerViewAll", "Retrieve Deals 2");
                    }
                });
            }
        });
        */
    }

    /**
     * Method that retrieve deals from local data store and update the deal list in the deal manager object
     * @return  A hash of deal objects that is according to user settings and preferences
     * @throws ParseException
     */
    public HashMap<String, Deal> getFromCache() throws ParseException {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);
        query.fromLocalDatastore();

        return afterQueryProcessing(query.find());
    }

    public ArrayList<Deal> getDealList() throws ParseException {
        return new ArrayList<>(getFromCache().values());
    }

    /**
     * Retrieve the deal according to the provided reference ID
     * @param referenceId   String value that contains the reference ID to identify a Deal in the list
     * @return              Deal object that contains the provided reference ID
     */
    public Deal getDeal(String referenceId)  throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);
        query.fromLocalDatastore();
        return (Deal) query.get(referenceId);
    }

    /**
     * Method to cast parse objects into deal object after query execution
     * @param parseObjects  List of Parse Object that belongs to Deal object
     * @return              A hash of deal objects that is according to user settings and preferences
     */
    private HashMap<String, Deal> afterQueryProcessing(List<ParseObject> parseObjects) {
        HashMap<String, Deal> dealList = new HashMap<>();

        for (ParseObject parseObject : parseObjects) {
            Deal deal = (Deal) parseObject;
            fetchRegionalDataIfNeeded(deal);
            dealList.put(deal.getReferenceId(), deal);
        }

        return dealList;
    }

    /**
     * Fetch regional data tied to Deal object
     * @param deal  Deal object that contains all information about the deal
     */
    private void fetchRegionalDataIfNeeded(Deal deal) {
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

    public static class UpdateCache implements Runnable {
        // TODO: Add preferences and settings to deal with the filter of the results to cache
        public UpdateCache() {}

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            Looper.prepare();
            ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(final List<ParseObject> parseObjects, ParseException e) {
                    if (e != null) {
                        Log.e("Deal", "Unable to find deals from Parse Database:\n" + e.getMessage());
                        return;
                    }
                    // Remove previously cache data
                    ParseObject.unpinAllInBackground("dealList", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // Cache new results
                            ParseObject.pinAllInBackground("dealList", parseObjects);
                        }
                    });
                }
            });
        }
    }
}