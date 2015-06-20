/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Entity;

/**
 * Address Object that contains the address information for a restaurant
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 21/2/2015.
 */
public class Address {
    /** Company's house number */
    private String houseNumber;
    /** Street name where the company is located */
    private String streetName;
    /** Company's unit address */
    private String unitNumber;
    /** Company's postal code */
    private String postalCode;

    /**
     * Constructor that initialize Address Object with the following parameters:
     * @param houseNumber   Company's house number
     * @param streetName    Street name where the company is located
     * @param unitNumber    Company's unit address
     * @param postalCode    Company's postal code
     */
    public Address(String houseNumber, String streetName, String unitNumber, String postalCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.unitNumber = unitNumber;
        this.postalCode = postalCode;
    }

    /**
     * Get house number for the address
     * @return String that contains the house number of the address
     */
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     * Set house number for the address
     * @param houseNumber   String value that contains the new house number of the address
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Get street number for the address
     * @return streetName   String value that contains the street name of the address
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Set street number for the address
     * @param streetName    String value that contains the new street name where the company is located
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Get unit number for the address
     * @return unitNumber   String value that contains the unit number of the address
     */
    public String getUnitNumber() {
        return unitNumber;
    }

    /**
     * Set unit number for the address
     * @param unitNumber    String value that contains the new unit number of the address
     */
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    /**
     * Get postal code for the address
     * @return postalCode   String value that contains the postal code of the address
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * Set postal code for the address
     * @param postalCode    String value that contains the new postal code of the address
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Format Address object into string value to display a summary of the address
     * @return  String value that contains a summary of the address
     */
    public String toString() {
        return this.houseNumber + " " + this.streetName + " " + this.unitNumber + " Singapore " +
                this.postalCode;
    }
}