package com.dinggoapplication.entities;

import com.dinggoapplication.utilities.LogUtils;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Class that contains the type of cuisine in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 26/7/2015.
 */
@ParseClassName("cuisine_type")
public class CuisineType extends ParseObject {
    /** Column name for cuisine name field */
    private final String CUISINE_NAME = "cuisineName";

    /** Default constructor to instantiate Cuisine Type */
    public CuisineType() {}

    /**
     * Constructor to instantiate Cuisine Type with the following arguments
     * @param cuisineName   Name of the cuisine type
     */
    public CuisineType(String cuisineName) {
        setCuisineName(cuisineName);
        saveInBackground(LogUtils.saveCallback(CuisineType.class.getName()));
    }

    /**
     * Retrieve the ID for the cuisine type from Parse
     * @return  String value that contains the ID for the cuisine type
     */
    public String getCuisineId() {
        return getObjectId();
    }

    /**
     * Retrieve the name of the cuisine type from Parse
     * @return  String value that contains the name of the cuisine type
     */
    public String getCuisineName() {
        return getString(CUISINE_NAME);
    }

    /**
     * Set the name of the cuisine type into Parse
     * @param cuisineName   String value to set a new name for the cuisine type
     */
    public void setCuisineName(String cuisineName) {
        put(CUISINE_NAME, cuisineName);
    }
}
