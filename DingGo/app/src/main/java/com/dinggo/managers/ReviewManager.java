package com.dinggo.managers;

import android.util.Log;

import com.dinggo.entities.Company;
import com.dinggo.entities.Review;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final static String PARSE_NAME = Review.TABLE_NAME;

    /** Default constructor for Review Manager class */
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
    public ArrayList<Review> updateCacheList(Company company) {

        ParseQuery<Review> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("companyId", company);
        try {
            ArrayList<Review> reviewList = new ArrayList<>(query.find());
            fetchRegionalDataIfNeeded(reviewList);
            Review.pinAll(company.getCompanyId(), reviewList);

        } catch (ParseException e) {
            Log.e(PARSE_NAME, "Unable to retrieve retrieve by the merchant ID: :" + company.getCompanyId() + "\n" + e.getMessage());
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

    /**
     * Retrieve Review object according to the ID of the review
     * @param reviewId  ID of the review
     * @return          Review object that contains the corresponding review information
     * @throws ParseException
     */
    public Review getReview (String reviewId) throws ParseException {
        ParseQuery<Review> query = ParseQuery.getQuery(PARSE_NAME);
        query.fromLocalDatastore();
        return query.get(reviewId);
    }

    /**
     * Retrieve the average rating received by the merchant
     * Format of the hash map is a follows:
     * numReviews | x.x
     * service | x.x
     * value | x.x
     * ambience | x.x
     * food_drink | x.x
     * total | x.x
     *
     * Parse exception will be thrown for the following reasons:
     * No results found
     * Unable to retrieve any reviews from Parse due to server error
     * Unable to retrieve any company due to server error or invalid company Id
     * Logic error in the cloud clouds
     *
     * @param companyId   ID of the company
     * @return              Hash of average ratings according to the merchant
     * @throws ParseException
     */
    public HashMap<String, Float> getAverageRatings(String companyId) throws ParseException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("company", companyId);

        HashMap<String, Float> valueList = new HashMap<>();
        HashMap<String, Object> output = ParseCloud.callFunction("averageStar", params);
        for (Map.Entry<String, Object> entry : output.entrySet()) {
            float value;

            if (entry.getValue() instanceof Integer)  value = (Integer) entry.getValue();
            else if (entry.getValue() instanceof Double) value = ((Double) entry.getValue()).floatValue();
            else value = Float.valueOf((String) entry.getValue());

            valueList.put(entry.getKey(), value);
        }

        return valueList;
    }

    /**
     * Fetch regional data tied to the review object
     * @param reviewList    A list of Review object
     */
    private void fetchRegionalDataIfNeeded(ArrayList<Review> reviewList) {
        try {

            for (Review review : reviewList) {
                if (!review.getRater().isDataAvailable()) review.getRater().fetchIfNeeded();
                if (!review.getDeal().isDataAvailable()) review.getDeal().fetchIfNeeded();
                if (!review.getCompany().isDataAvailable()) review.getCompany().fetchIfNeeded();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
