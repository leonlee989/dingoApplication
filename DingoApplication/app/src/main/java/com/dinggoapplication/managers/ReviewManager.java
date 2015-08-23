package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.Review;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Review Manager class handles all the reviews in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 17/8/2015.
 */
public class ReviewManager {
    /** Singleton instance of Review Manager object */
    private static ReviewManager instance;
    /** Number of checkout for the instance */
    private static int checkout;
    /** The name of the object the manager class is handling */
    private final static String PARSE_NAME = "review";

    /**
     * Default constructor for Review Manager class
     */
    private ReviewManager() {}

    /**
     * Singleton handler that retrieves the single instance of Review Manager object
     * @return  Single instance of ReviewManager object
     */
    public static ReviewManager getInstance() {
        synchronized (ReviewManager.class) {
            if (instance == null) {
                instance = new ReviewManager();
            }
        }
        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) { checkout++;}
        return instance;
    }

    /**
     * Retrieve the number of checkouts for the ReviewManager object
     * @return  Integer value that contains the number of checkouts for the ReviewManger Object
     */
    public int getCheckout() {
        return checkout;
    }

    /**
     * Retrieve all reviews from Parse database and pin all the reviews into user's local database
     * tag with a label according to company ID
     * Retrieve all reviews from cache upon completion
     * @param company   Company object that contains all the information with regards to the merchant
     * @return          A array of Review objects
     */
    public ArrayList<Review> retrieveReviews(Company company) {

        ParseQuery<Review> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("companyId", company);
        try {
            ArrayList<Review> reviewList = new ArrayList<>(query.find());
            fetchRegionalDataIfNeeded(reviewList);
            Review.pinAll(company.getCompanyID(), reviewList);

        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to retrieve retrieve by the merchant ID: :" + company.getCompanyID() + "\n" + e.getMessage());
        }

        return getReviewsFromCache(company);
    }

    /**
     * Method that retrieve reviews from local data store and pass the retrieved reviews back to the invoker
     * @param company   Company object that contains all the information with regards to the merchant
     * @return          A list of review objects
     */
    public ArrayList<Review> getReviewsFromCache(Company company) {

        ParseQuery<Review> queryCache = ParseQuery.getQuery(PARSE_NAME);
        queryCache.fromLocalDatastore()
                .whereEqualTo("companyId", company);

        try {
            List<Review> list = queryCache.find();
            Log.d("Review", "Size of the list: " + list.size());


            for (Review objects : list)
                Log.d("Review", objects.getString("comments"));

            return new ArrayList<>(list);

        } catch (ParseException e) {
            Log.e(PARSE_NAME, e.getMessage());
        }

        return new ArrayList<>();
    }

    public Review getReview (String reviewId) throws ParseException {
        ParseQuery<Review> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();
        return query.get(reviewId);
    }

    /**
     * Fetch regional data tied to the review object
     * @param reviewList    A list of Review object
     */
    private void fetchRegionalDataIfNeeded(ArrayList<Review> reviewList) {
        try {
            for (Review review : reviewList) {
                if (!review.getDeal().isDataAvailable()) review.getDeal().fetchIfNeeded();
                if (!review.getCompany().isDataAvailable()) review.getCompany().fetchIfNeeded();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
