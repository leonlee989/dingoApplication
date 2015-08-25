package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Company Manager class handles all merchants in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 21/8/2015.
 */
public class CompanyManager {
    /** Singleton instance of Company Manager Object */
    private static CompanyManager instance;
    /** Number of checked out for the instance */
    private static int checkout = 0;
    /** The name of the object the manager class is handling */
    private final static String PARSE_NAME = Company.TABLE_NAME;

    /**
     * Default constructor for Company Manager class
     */
    private CompanyManager() {}

    /**
     * Singleton handler that retrieves the single instance of CompanyManager object
     * @return  Single instance of CompanyManager Object
     */
    public static CompanyManager getInstance() {
        synchronized (CompanyManager.class) {
            if (instance == null) {
                instance = new CompanyManager();
            }
        }
        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) { checkout++; }
        return instance;
    }

    /**
     * Retrieve the number of checkouts for the CompanyManager Object
     * @return  Integer value that contains the number of checkouts for the CompanyManager object
     */
    public int getCheckout() {
        return checkout;
    }

    /**
     * Retrieve all company object from Parse database and pin all the company objects into user
     * local database.
     * Retrieve all companies from cache upon completion
     * @return A list of companies objects available in Parse database
     */
    public ArrayList<Company> updateCacheList() {
        ParseQuery<Company> query = ParseQuery.getQuery(PARSE_NAME);

        try {
            Company.pinAll("companyList", query.find());
        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to find any merchant from Parse Database:\n" + e.getMessage());
        }

        return getCompaniesFromCache();
    }

    /**
     * Method that retrieve companies from local data store and pass the retrieved companies back to
     * the invoker
     * @return A list of company object
     */
    public ArrayList<Company> getCompaniesFromCache() {

        ParseQuery<Company> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();

        try {
            return new ArrayList<>(query.find());
        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to find any merchants from local data store:\n" + e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Retrieve the company object according to the provided company ID
     * @param companyId     String value that contains the company ID to identify a company in the
     *                      local data store
     * @return              Company Object that holds similar ID
     * @throws ParseException
     */
    public Company getCompany(String companyId) throws ParseException {
        ParseQuery<Company> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();
        return query.get(companyId);
    }
}
