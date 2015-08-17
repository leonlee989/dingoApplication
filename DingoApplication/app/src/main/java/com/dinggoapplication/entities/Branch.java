package com.dinggoapplication.entities;

import com.dinggoapplication.utilities.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Branch class contains information about the outlet of a company
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 27/7/2015.
 */
@ParseClassName("branch")
public class Branch extends ParseObject {
    /** Column name in the Parse Database */
    private final String COLUMN_COMPANY_ID = "companyId",
            COLUMN_ADDRESS1 = "address1",
            COLUMN_ADDRESS2 = "address2",
            COLUMN_CITY = "city",
            COLUMN_STATE = "state",
            COLUMN_POST_CODE = "postCode",
            COLUMN_COUNTRY = "country",
            COLUMN_PHONE = "phoneNo",
            COLUMN_LAT = "latitude",
            COLUMN_LNG = "longitude";

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
    public Branch(Company company, String address1, String address2, String city, String state, String postCode,
                  String country, long phoneNo) {
        this(company, address1, address2, city, state, postCode, country, phoneNo, 0, 0);
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
    public Branch(Company company, String address1, String address2, String city, String state, String postCode,
                  String country, long phoneNo, double latitude, double longitude) {
        setCompany(company);
        setAddress1(address1);
        setAddress2(address2);
        setCity(city);
        setStates(state);
        setPostCode(postCode);
        setCountry(country);
        setPhoneNo(phoneNo);
        setLatLng(latitude, longitude);

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
     * Retrieve the company where the branch belongs to
     * @return  Company object that contains all the information about the company
     */
    public Company getCompany() {
        return (Company) get(COLUMN_COMPANY_ID);
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
    public long getPhoneNo() {
        return getLong(COLUMN_PHONE);
    }

    /**
     * Set and change the contact number for the branch
     * @param phoneNo   Integer value that contains the new phone number for the branch
     */
    public void setPhoneNo(long phoneNo) {
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
}