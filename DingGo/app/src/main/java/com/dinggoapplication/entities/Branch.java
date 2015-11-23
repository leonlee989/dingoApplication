package com.dinggoapplication.entities;

import android.graphics.Bitmap;
import android.util.Log;

import com.dinggoapplication.utilities.ImageUtils;
import com.dinggoapplication.utilities.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Branch class contains information about the outlet of a company
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 27/7/2015.
 */
@ParseClassName("branch")
public class Branch extends ParseObject {
    /** Name of the table */
    public static final String TABLE_NAME = "branch";

    /** Column name in the Parse Database */
    public static final String COLUMN_COMPANY_ID = "companyId",
            COLUMN_NAME = "branchName",
            COLUMN_ADDRESS1 = "address1",
            COLUMN_ADDRESS2 = "address2",
            COLUMN_CITY = "city",
            COLUMN_STATE = "state",
            COLUMN_POST_CODE = "postCode",
            COLUMN_COUNTRY = "country",
            COLUMN_PHONE = "phoneNo",
            COLUMN_LAT = "latitude",
            COLUMN_LNG = "longitude",
            COLUMN_DESCRIPTION = "description",
            COLUMN_BRANCH_AVERAGE_SPENDING = "branchAverageSpending",
            COLUMN_LOGO_IMAGE = "logoImage",
            COLUMN_COVER_IMAGE = "coverImage";

    /** Default constructor to instantiate the Branch object and store into Parse database */
    public Branch() {}

    /**
     * Constructor to instantiate the Branch object and store into Parse database with the following arguments
     * @param company   Company that is related to the branch
     * @param address1  First line of address
     * @param address2  Second line of address
     * @param city      City the branch is located at
     * @param state     State the branch is located at
     * @param postCode  The post code of the branch
     * @param country   Country the branch is located at
     * @param phoneNo   The phone number of the branch
     */
    public Branch(String branchName, Company company, String address1, String address2, String city, String state, String postCode,
                  String country, String phoneNo) {
        this(branchName, company, address1, address2, city, state, postCode, country, phoneNo, 0, 0,
                "", null, null);
    }

    /**
     * Constructor to instantiate the Branch object and store into Parse database with the following arguments
     * @param company   Company that is related to the branch
     * @param address1  First line of address
     * @param address2  Second line of address
     * @param city      City the branch is located at
     * @param state     State the branch is located at
     * @param postCode  The post code of the branch
     * @param country   Country the branch is located at
     * @param phoneNo   The phone number of the branch
     * @param latitude  Latitude of the map where the branch is located at
     * @param longitude Longitude of the map the branch is located at
     */
    public Branch(String branchName, Company company, String address1, String address2, String city,
                  String state, String postCode, String country, String phoneNo, double latitude, double longitude,
                  String description, byte[] logoImage, byte[] coverImage) {
        setName(branchName);
        setCompany(company);
        setAddress1(address1);
        setAddress2(address2);
        setCity(city);
        setStates(state);
        setPostCode(postCode);
        setCountry(country);
        setPhoneNo(phoneNo);
        setLatLng(latitude, longitude);
        setDescription(description);
        setLogoImage(logoImage);
        setCoverImage(coverImage);
        setBranchAverageSpending(0);

        saveInBackground(LogUtils.saveCallback(Branch.class.getName()));
    }

    /**
     * Retrieve the ID of the branch from Parse database
     * @return  String value that contains the ID of the branch
     */
    public String getBranchId() {
        return getObjectId();
    }

    /**
     * Retrieve the name of the branch
     * @param branchName    String value that contains the name of the branch
     */
    public void setName(String branchName) {
        put(COLUMN_NAME, branchName);
    }

    /**
     * Retrieve the name of the branch
     * @return String value that contains the name of the branch
     */
    public String getName() {
        return getString(COLUMN_NAME);
    }

    /**
     * Retrieve the company where the branch belongs to
     * @return  Company object that contains all the information about the company
     */
    public Company getCompany() {
        Company company = (Company) get(COLUMN_COMPANY_ID);

        if (!company.isDataAvailable()) {
            try {
                company.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return company;
    }

    /**
     * Set and change a new company
     * @param company   Company object that contains all the information about the new company
     */
    public void setCompany(Company company) {
        put(COLUMN_COMPANY_ID, company);
    }

    /**
     * Retrieve the first line of address from Parse database
     * @return  String value that contains the first line of address
     */
    public String getAddress1() {
        return getString(COLUMN_ADDRESS1);
    }

    /**
     * Set and change the first line of the address
     * @param address1  String value that contains the first line of address
     */
    public void setAddress1(String address1) {
        put(COLUMN_ADDRESS1, address1);
    }

    /**
     * Retrieve the second line of address from Parse database
     * @return  String value that contains the second line of address
     */
    public String getAddress2() {
        return getString(COLUMN_ADDRESS2);
    }

    /**
     * Set and change the second line of address
     * @param address2  String value that contains the second line of address
     */
    public void setAddress2(String address2) {
        put(COLUMN_ADDRESS2, address2);
    }

    /**
     * Retrieve the city where the branch is located
     * @return  String value that contains the name of the city
     */
    public String getCity() {
        return getString(COLUMN_CITY);
    }

    /**
     * Set and change a new city for the branch
     * @param city  String value that contains the name of the new city
     */
    public void setCity(String city) {
        put(COLUMN_CITY, city);
    }

    /**
     * Retrieve the state where the branch is located at
     * @return  String value that contain the name of the state
     */
    public String getStates() {
        return getString(COLUMN_STATE);
    }

    /**
     * Set anc change a new state for the branch
     * @param states    String value that contains the name of the new state
     */
    public void setStates(String states) {
        put(COLUMN_STATE, states);
    }

    /**
     * Retrieve the post code of the branch
     * @return  Integer value that contains the post code of the branch
     */
    public String getPostCode() {
        return getString(COLUMN_POST_CODE);
    }

    /**
     * Set and change the post code of the branch
     * @param postCode  Integer value the contains the new post code for the branch
     */
    public void setPostCode(String postCode) {
        put(COLUMN_POST_CODE, postCode);
    }

    /**
     * Retrieve the name of the country where the branch is located at
     * @return  String value that contains the name of the country
     */
    public String getCountry() {
        return getString(COLUMN_COUNTRY);
    }

    /**
     * Set and change the name of the country where the branch is located at
     * @param country   String value that contains the name of the new country
     */
    public void setCountry(String country) {
        put(COLUMN_COUNTRY, country);
    }

    /**
     * Retrieve the branch's contact number
     * @return  Integer value that contains branch's contact number
     */
    public String getPhoneNo() {
        return getString(COLUMN_PHONE);
    }

    /**
     * Set and change the contact number for the branch
     * @param phoneNo   Integer value that contains the new phone number for the branch
     */
    public void setPhoneNo(String phoneNo) {
        put(COLUMN_PHONE, phoneNo);
    }

    /**
     * Set the latitude and longitude of the map where the branch is located
     * @param latitude      Latitude of the map where the branch is located
     * @param longitude     Longitude of the map where the branch is located
     */
    public void setLatLng(double latitude, double longitude) {
        put(COLUMN_LAT, latitude);
        put(COLUMN_LNG, longitude);
    }

    /**
     * Retrieve the latitude and longitude of the map where the branch is located
     * @return  Double value that contains the latitude of the map where the branch is located
     */
    public LatLng getLatLng() {
        return new LatLng(getDouble(COLUMN_LAT), getDouble(COLUMN_LNG));
    }

    /**
     * Set and change the description of the branch
     * @param description   String value that contains the desciption of the branch
     */
    public void setDescription(String description) {
        put(COLUMN_DESCRIPTION, description);
    }

    /**
     * Retrieve the description of the branch
     * @return  String value that contains the description of the branch
     */
    public String getDescription() {
        return getString(COLUMN_DESCRIPTION);
    }

    /**
     * Set the average spending of the branch
     * @param averageSpending Double value that contains the average spending of the branch
     */
    public void setBranchAverageSpending(double averageSpending) {
        put(COLUMN_BRANCH_AVERAGE_SPENDING, averageSpending);
    }

    /**
     * Retrieve the average spending of the branch
     * @return Double value that contains the average spending of the branch
     */
    public double getBranchAverageSpending() {
        return getDouble(COLUMN_BRANCH_AVERAGE_SPENDING);
    }

    /**
     * Set and change the logo of the branch
     * @param image Byte array that contains the new image of the branch
     */
    public void setLogoImage(byte[] image) {
        if (image != null) {
            ParseFile logoImage = new ParseFile(image);
            logoImage.saveInBackground(LogUtils.saveCallback(Branch.class.getName()));
            put(COLUMN_LOGO_IMAGE, logoImage);

        } else Log.e(TABLE_NAME, "Image cannot be null to set the cover image");
    }

    /**
     * Retrieve the logo image of the branch
     * @return  Bitmap that contains the logo image of the branch
     * @throws ParseException
     */
    public Bitmap getLogoImage() throws ParseException {
        ParseFile logoImage = getParseFile(COLUMN_LOGO_IMAGE);
        if (logoImage != null) return ImageUtils.convertBytesToImage(logoImage.getData());
        else return null;
    }

    /**
     * Set and change the cover image for the branch
     * @param image    Byte array that contains the cover image for the branch
     */
    public void setCoverImage(byte[] image) {
        if (image != null) {
            ParseFile coverImage = new ParseFile(image);
            coverImage.saveInBackground(LogUtils.saveCallback(Branch.class.getName()));
            put(COLUMN_COVER_IMAGE, coverImage);

        } else Log.e(TABLE_NAME, "Image cannot be null to set the cover image");
    }

    /**
     * Retrieve the cover image for the branch
     * @return  Bitmap object that contains the data on the cover image of the branch
     * @throws ParseException
     */
    public Bitmap getCoverImage() throws ParseException {
        ParseFile coverImage = getParseFile(COLUMN_COVER_IMAGE);
        if (coverImage != null) return ImageUtils.convertBytesToImage(coverImage.getData());
        else return null;
    }
}