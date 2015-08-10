/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Entity;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Class that contains all details about the merchants
 * @deprecated
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class Merchant {
    /** Merchant Id to identity a specific merchant */
    private String merchantId;
    /** Password for merchant to login */
    private String password;
    /** Logo for company */
    private Bitmap image;
    /** Name of the company */
    private String companyName;
    /** Merchant's name */
    private String merchantName;
    /** Type of merchant */
    private String merchantType;
    /** Address of the shop */
    private Address address;
    /** Short description of the merchant */
    private String merchantDescription;
    /** Contact number for the merchant */
    private int contactNumber;
    /** Website of the merchant */
    private String website;
    /** Get Longitude and latitude of the address */
    private LatLng latLng;

    /**
     * Constructor to initialize Merchant Object with the following parameters
     * @param merchantId            ID assigned to the merchant
     * @param password              Login authentication for merchant to access their account
     * @param image                 Logo of the company
     * @param companyName           The name of the company
     * @param merchantName          The name of the merchant
     * @param merchantDescription   Description about the company
     * @param merchantType          Type of merchant
     * @param address               The address of the company
     * @param contactNumber         The contact number of the company
     * @param website               The url link for company's website
     */
    public Merchant(String merchantId, String password, Bitmap image, String companyName,
                    String merchantName, String merchantDescription, String merchantType,
                    Address address, int contactNumber, String website) {
        this(merchantId, password, image, companyName, merchantName, merchantDescription,
                merchantType, address, contactNumber, website, null);
    }

    /**
     * Construction to initialize Merchant Object with the following parameters
     * @param merchantId            ID assigned to the merchant
     * @param password              Login authentication for merchant to access their account
     * @param image                 Logo of the company
     * @param companyName           The name of the company
     * @param merchantName          The name of the merchant
     * @param merchantDescription   Description about the company
     * @param merchantType          Type of merchant
     * @param address               The address of the company
     * @param contactNumber         The contact number of the company
     * @param website               The url link for company's website
     * @param latLng                The latitude and longitude where the company is located in the map
     */
    public Merchant(String merchantId, String password, Bitmap image, String companyName,
                    String merchantName, String merchantDescription, String merchantType,
                    Address address, int contactNumber, String website, LatLng latLng) {
        this.merchantId = merchantId;
        this.password = password;
        this.image = image;
        this.companyName = companyName;
        this.merchantName = merchantName;
        this.merchantDescription = merchantDescription;
        this.merchantType = merchantType;
        this.address = address;
        this.contactNumber = contactNumber;
        this.website = website;

        this.latLng = latLng;
    }

    /**
     * Get the ID of the merchant
     * @return  String value that contains the ID of the merchant
     */
    public String getMerchantId() {
        return this.merchantId;
    }

    /**
     * Set the ID of the merchant
     * @param merchantId    String value that contains the new ID for the merchant
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * Get the password of the merchant
     * @return password     String value that contains the password of the merchant
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the merchant
     * @param password  String value that contains the new password for the merchant
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get image object for the merchant
     * @return image    Bitmap value that contains the image of the logo
     */
    public Bitmap getImage() {
        return this.image;
    }

    /**
     * Set the image for the merchant
     * @param image     Bitmap value that contains the new logo for the merchant
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Get the company name of the merchant
     * @return companyName String value that contains the name of the company
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Set the company name for the merchant
     * @param companyName   String value that contains the name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get the name of the merchant
     * @return merchantName     String value that contains the name of the merchant
     */
    public String getMerchantName() {
        return this.merchantName;
    }

    /**
     * Set the name of the merchant
     * @param merchantName      String value that contains the new name for the merchant
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Retrieve the short description of the merchant
     * @return merchantDescription  String value that contains the description of the merchant
     */
    public String getMerchantDescription() {
        return merchantDescription;
    }

    /**
     * Set the short description of the merchant
     * @param merchantDescription   String value that contains the new description for the merchant
     */
    public void setMerchantDescription(String merchantDescription) {
        this.merchantDescription = merchantDescription;
    }

    /**
     * Get the type of the merchant
     * @return merchantType     String value that contains the type of the merchant
     */
    public String getMerchantType() {
        return this.merchantType;
    }

    /**
     * Set the type of the merchant
     * @param merchantType      String value that contains the new type for the merchant
     */
    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    /**
     * Get the address of the company
     * @return address     Address object that contains the details of the merchant's address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * Set the address of the company
     * @param address   New address object that contains the new address for the company
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Set the address of the company with the following parameters
     * @param houseNumber   Company's house number
     * @param streetName    Street name where the company is located
     * @param unitNumber    Company's unit address
     * @param postalCode    Company's postal code
     */
    public void setAddress(String houseNumber, String streetName, String unitNumber, String postalCode) {
        this.address = new Address(houseNumber, streetName, unitNumber, postalCode);
    }

    /**
     * Get the contact number of the merchant
     * @return contactNumber    Integer value that contains the contact number of the merchant
     */
    public int getContactNumber() {
        return contactNumber;
    }

    /**
     * Set the contact number of the merchant
     * @param contactNumber     Integer value that contains the new contact number of the merchant
     */
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Retrieve the website of the merchant
     * @return  String value that contains the url link for the company's website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set the website of the merchant
     * @param website   String value that contains the url link for the company's website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Get longitude and latitude values where the company is located in the map
     * @return  LatLng object that contains latitude and longitude values where the company is located in the map
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Set the longitude and latitude of the address
     * @param latLng    LatLng object that contains latitude and longitude value where the company is located in the map
     */
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}