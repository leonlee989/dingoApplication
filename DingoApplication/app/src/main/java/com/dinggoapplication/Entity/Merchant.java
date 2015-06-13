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
 * Merchant object
 * Created by Leon on 23/2/2015.
 */
public class Merchant {
    /**
     * Merchant Id to identity a specific merchant
     */
    String merchantId;

    /**
     * Password for merchant to login
     */
    String password;

    /**
     * Logo for company
     */
    Bitmap image;
    /**
     * Name of the company
     */
    String companyName;
    /**
     * Merchant's name
     */
    String merchantName;
    /**
     * Type of merchant
     */
    String merchantType;

    /**
     * Address of the shop
     */
    Address address;

    /**
     * Short description of the merchant
     */
    String merchantDescription;

    /**
     * Contact number for the merchant
     */
    int contactNumber;

    /**
     * Website of the merchant
     */
    String website;

    /**
     * Get Longitude and latitude of the address
     */
    LatLng latLng;

    /**
     * Constructor to initialize Merchant Object
     * @param merchantId
     * @param password
     * @param image
     * @param companyName
     * @param merchantName
     * @param merchantDescription
     * @param merchantType
     * @param address
     * @param contactNumber
     * @param website
     */
    public Merchant(String merchantId, String password, Bitmap image, String companyName,
                    String merchantName, String merchantDescription, String merchantType,
                    Address address, int contactNumber, String website) {
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
    }

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
     * @return merchantId
     */
    public String getMerchantId() {
        return this.merchantId;
    }

    /**
     * Set the ID of the merchant
     * @param merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * Get the password of the merchant
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the merchant
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get image object for the merchant
     * @return image
     */
    public Bitmap getImage() {
        return this.image;
    }

    /**
     * Set the image for the merchant
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Get the company name of the merchant
     * @return companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Set the company name for the merchant
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get the name of the merchant
     * @return merchantName
     */
    public String getMerchantName() {
        return this.merchantName;
    }

    /**
     * Set the name of the merchant
     * @param merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Retrieve the short description of the merchant
     * @return merchantDescription
     */
    public String getMerchantDescription() {
        return merchantDescription;
    }

    /**
     * Set the short description of the merchant
     * @param merchantDescription
     */
    public void setMerchantDescription(String merchantDescription) {
        this.merchantDescription = merchantDescription;
    }

    /**
     * Get the type of the merchant
     * @return merchantType
     */
    public String getMerchantType() {
        return this.merchantType;
    }

    /**
     * Set the type of the merchant
     * @param merchantType
     */
    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    /**
     * Get the address of the company
     * @return address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * Set the address of the company
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Set the address of the company with the following parameters
     * @param houseNumber
     * @param streetName
     * @param unitNumber
     * @param postalCode
     */
    public void setAddress(String houseNumber, String streetName, String unitNumber, String postalCode) {
        this.address = new Address(houseNumber, streetName, unitNumber, postalCode);
    }

    /**
     * Get the contact number of the merchant
     * @return contactNumber
     */
    public int getContactNumber() {
        return contactNumber;
    }

    /**
     * Set the contact number of the merchant
     * @param contactNumber
     */
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Retrieve the website of the merchant
     * @return
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set the website of the merchant
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Get the longitude and latitude of the address
     * @return
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Set the longitude and latitude of the address
     * @param latLng
     */
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}