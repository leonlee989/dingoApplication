package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.CuisineType;
import com.dinggoapplication.entities.Deal;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Deal Manager class handles all deals in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 2/8/2015.
 */
public class DealManager {
    /** Singleton instance of Deal Manager Object */
    private static DealManager instance;
    /** Number of checked out for the instance */
    private static int checkOut = 0;
    /** The name of the object the manager class is handling */
    private final static String PARSE_NAME = Deal.TABLE_NAME;

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
        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) { checkOut++; }
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
     * Retrieve all deals according to user's preferences and settings from Parse database and pin
     * all the deals into user local database
     * Retrieve all deals from cache upon completion
     * @return List of deals according to the user's preferences and settings
     */
    public ArrayList<Deal> updateCacheList(){
        ParseQuery<Deal> query = ParseQuery.getQuery(PARSE_NAME);

        try {
            Deal.unpinAll("dealList");

            ArrayList<Deal> dealList = new ArrayList<>(query.find());
            fetchRegionalDataIfNeeded(dealList);
            Deal.pinAll("dealList", dealList);

        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to find any deals from Parse Database:\n" + e.getMessage());
        }

        return getDealsFromCache();
    }

    /**
     * Method that retrieve deals from local data store and pass the retrieved deals back to the
     * invoker
     * @return  A list of Deal objects that is according to user settings and preferences
     */
    public ArrayList<Deal> getDealsFromCache() {

        ParseQuery<Deal> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();

        try {
            return new ArrayList<>(query.find());
        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to find any deals from Local Database:\n" + e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Retrieve the deal according to the provided reference ID
     * @param referenceId   String value that contains the reference ID to identify a Deal in the
     *                      local data store
     * @return              Deal object that contains the provided reference ID
     * @throws ParseException
     */
    public Deal getDeal(String referenceId) throws ParseException {
        ParseQuery<Deal> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();
        return query.get(referenceId);
    }

    /**
     * Fetch regional data tied to Deal object
     * @param dealList  A list of deal object to fetch for regional data tied to each object
     */
    private void fetchRegionalDataIfNeeded(ArrayList<Deal> dealList) {
        try {
            List<Branch> branchList = new ArrayList<>();
            List<Company> companyList = new ArrayList<>();
            List<CuisineType> cuisineTypeList = new ArrayList<>();

            for (Deal deal : dealList) {
                Branch branch;
                if (!deal.getBranch().isDataAvailable()) {
                    branch = deal.getBranch().fetchIfNeeded();
                    branchList.add(branch);
                } else {
                    branch = deal.getBranch();
                }

                if (branch != null) {
                    Company company;
                    if (!branch.getCompany().isDataAvailable()) {
                        company = branch.getCompany().fetchIfNeeded();
                        companyList.add(company);
                    } else {
                        company = branch.getCompany();
                    }

                    if (company != null) {
                        if (!company.getCuisineType().isDataAvailable()) {
                            cuisineTypeList.add((CuisineType) company.getCuisineType().fetchIfNeeded());
                        }
                    }
                }
            }

            if (!branchList.isEmpty()) Branch.pinAll("branchList", branchList);
            if (!companyList.isEmpty()) Company.pinAll("companyList", companyList);
            if (!cuisineTypeList.isEmpty()) Company.pinAll("cuisineTypeList", cuisineTypeList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}