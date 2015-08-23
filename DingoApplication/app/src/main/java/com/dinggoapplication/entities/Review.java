package com.dinggoapplication.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Class that contains all the information with regards to the review
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 18/8/15.
 */
@ParseClassName("review")
public class Review extends ParseObject {

    /** Name of the table */
    public static final String TABLE_NAME = "review";

    /** Column names for Review fields */
    public static final String COLUMN_USER = "user",
            COLUMN_DEAL_ID = "referenceId",
            COLUMN_COMPANY_ID = "companyId",
            COLUMN_FOOD_RATING = "food_drink",
            COLUMN_VALUE_RATING = "value",
            COLUMN_AMBIENCE_RATING = "ambience",
            COLUMN_SERVICE_RATING = "service",
            COLUMN_COMMENT = "comments";

    /** Default constructor that instantiate the Review Object */
    public Review() {}

    /**
     * Constructor that instantiates the Review Object with the following parameters
     * @param user              The user who rate the deal
     * @param deal              The deal that is being rated
     * @param foodRating        The rating given for the food and drinks in the restaurant
     * @param valueRating       The rating given for the value of the restaurant
     * @param ambienceRating    The rating given for the ambience in the restaurant
     * @param serviceRating     The rating given for the customer service in the restaurant
     * @param comment           Comments about the deal or company
     */
    public Review(ParseUser user, Deal deal, int foodRating, int valueRating, int ambienceRating, int serviceRating, String comment) {
        saveReview(user, deal, foodRating, valueRating, ambienceRating, serviceRating, comment);
    }

    /**
     * Save a review object into Parse database
     * @param user              The user who rate the deal
     * @param deal              The deal that is being rated
     * @param foodRating        The rating given for the food and drinks in the restaurant
     * @param valueRating       The rating given for the value of the restaurant
     * @param ambienceRating    The rating given for the ambience in the restaurant
     * @param serviceRating     The rating given for the customer service in the restaurant
     * @param comment           Comments about the deal or company
     */
    private void saveReview(ParseUser user, Deal deal, int foodRating, int valueRating, int ambienceRating, int serviceRating, String comment) {
        setRater(user);
        setDeal(deal);
        setFoodRating(foodRating);
        setValueRating(valueRating);
        setAmbienceRating(ambienceRating);
        setServiceRating(serviceRating);
        setComment(comment);

        saveEventually();
    }

    /**
     * Retrieve the ID for the review
     * @return  String value that contains the object ID for the review object
     */
    public String getReviewId() {
        return getObjectId();
    }

    /**
     * The name of the user who give the rating for the deal
     * @return  String value that contains the name of the user
     */
    public String getRaterName() {
        return (String) ((ParseUser) get(COLUMN_USER)).get("name");
    }

    /**
     * Set the parse user who have give the ratings
     * @param user  The parse user who give the ratings
     */
    public void setRater(ParseUser user) {
        put(COLUMN_USER, user);
    }

    /**
     * Retrieve Deal object that is being rated
     * @return  Deal object that contains all the information with regards to the deal
     */
    public Deal getDeal() {
        return (Deal) get(COLUMN_DEAL_ID);
    }
    /**
     * Retrieve the name of the deal that is being rated
     * @return  String value that contains the name of the deal
     */
    public String getDealName() {
        return getDeal().getDealName();
    }

    /**
     * Set the deal object into the review object
     * @param deal  Deal object that contains all the information with regards to the deal
     */
    public void setDeal(Deal deal) {
        put(COLUMN_DEAL_ID, deal);
        put(COLUMN_COMPANY_ID, deal.getBranch().getCompany());
    }

    /**
     * Retrieve the company object
     * @return  Company object that contains all the information with regards to the company
     */
    public Company getCompany() {
        return (Company) get(COLUMN_COMPANY_ID);
    }

    /**
     * Retrieve the rating given for the food and drinks
     * @return  Integer value that contains the ratings given for food and drinks
     */
    public int getFoodRating() {
        return getInt(COLUMN_FOOD_RATING);
    }

    /**
     * Give a rating value to the merchant for its food and drinks
     * @param foodRating    Integer value that contains the rating given for food and drinks
     */
    public void setFoodRating(int foodRating) {
        put(COLUMN_FOOD_RATING, foodRating);
    }

    /**
     * Retrieve the rating value for the value worth for the deal
     * @return  Integer value that contains the rating given for the value of the deal
     */
    public int getValueRating() {
        return getInt(COLUMN_VALUE_RATING);
    }

    /**
     * Give a rating value for the value of the deal and its worth
     * @param valueRating   Integer value that contains the rating value for the value of the deal
     */
    public void setValueRating(int valueRating) {
        put(COLUMN_VALUE_RATING, valueRating);
    }

    /**
     * Retrieve the rating value for the ambience of the restaurant
     * @return  Integer value that contains the rating value for the ambience of the restaurant
     */
    public int getAmbienceRating() {
        return getInt(COLUMN_AMBIENCE_RATING);
    }

    /**
     * Give a rating value for the ambience of the restaurant
     * @param ambienceRating    Integer value that contains the rating value for the ambience of the restaurant
     */
    public void setAmbienceRating(int ambienceRating) {
        put(COLUMN_AMBIENCE_RATING, ambienceRating);
    }

    /**
     * Retrieve the rating value for merchant's customer service
     * @return  Integer value that contains the rating value for merchant's customer service
     */
    public int getServiceRating() {
        return getInt(COLUMN_SERVICE_RATING);
    }

    /**
     * Give a rating value for merchant's customer service
     * @param serviceRating Integer value that contains the rating value for merchant's customer service
     */
    public void setServiceRating(int serviceRating) {
        put(COLUMN_SERVICE_RATING, serviceRating);
    }

    /**
     * Retrieve the comments given by the user to the deal
     * @return  String value that contains the comments given by the user to the deal
     */
    public String getComments() {
        return getString(COLUMN_COMMENT);
    }

    /**
     * Given a comment
     * @param comment   String value that contains the comments given by the user to the deal
     */
    public void setComment(String comment) {
        put(COLUMN_COMMENT, comment);
    }
}
