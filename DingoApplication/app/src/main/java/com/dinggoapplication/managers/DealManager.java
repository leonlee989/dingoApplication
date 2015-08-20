package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Deal;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private final static String parseClassName = "deal";

    /** A hash of deals that belongs to the user */
    private HashMap<String, Deal> dealList;

    /**
     * Default constructor for Deal Manager class
     */
    private DealManager() {
        dealList = new HashMap<>();
    }

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
    public HashMap<String, Deal> updateCacheList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);
        List<ParseObject> parseObjects = new ArrayList<>();
        try {

            parseObjects = query.find();

            rePinInBackground(parseObjects);

            return getFromCache();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new HashMap<>();

    }
    private void rePinInBackground(final List<ParseObject> parseObjects) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    ParseObject.unpinAll("dealList");
                    ParseObject.pinAll("dealList", parseObjects);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Method to cast parse objects into deal object after query execution
     * @param parseObjects  List of Parse Object that belongs to Deal object
     * @return              A hash of deal objects that is according to user settings and preferences
     */
    private HashMap<String, Deal> afterQueryProcessing(List<ParseObject> parseObjects) {
        dealList.clear();

        for (ParseObject parseObject : parseObjects) {
            Deal deal = (Deal) parseObject;
            fetchRegionalDataIfNeeded(deal);
            dealList.put(deal.getReferenceId(), deal);
        }

        Log.d("Deal", "Caching of data is completed!");
        return dealList;
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

    /**
     * Retrieve all the deals that is according to the user's settings and preferences
     * @return  A list of deals that is according to the user's settings and preferences
     */
    public ArrayList<Deal> getDealList() {
        return new ArrayList<>(dealList.values());
    }

    /**
     * Retrieve the deal according to the provided reference ID
     * @param referenceId   String value that contains the reference ID to identify a Deal in the list
     * @return              Deal object that contains the provided reference ID
     */
    public Deal getDeal(String referenceId) {
        return dealList.get(referenceId);
    }
}