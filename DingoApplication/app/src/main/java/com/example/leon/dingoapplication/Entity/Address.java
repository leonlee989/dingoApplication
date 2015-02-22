package com.example.leon.dingoapplication.Entity;

/**
 * Address Object that contains the address information for a restaurant
 * Created by Leon on 21/2/2015.
 */
public class Address {
    String houseNumber;
    String streetName;
    String unitNumber;
    int postalCode;

    /**
     * Constructor that initialize Address Object with the following parameters:
     * @param houseNumber
     * @param streetName
     * @param unitNumber
     * @param postalCode
     */
    public Address(String houseNumber, String streetName, String unitNumber, int postalCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.unitNumber = unitNumber;
        this.postalCode = postalCode;
    }

    /**
     * Get house number for the address
     * @return houseNumber
     */
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     * Set house number for the address
     * @param houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Get street number for the address
     * @return streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Set street number for the address
     * @param streetName
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Get unit number for the address
     * @return unitNumber
     */
    public String getUnitNumber() {
        return unitNumber;
    }

    /**
     * Set unit number for the address
     * @param unitNumber
     */
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    /**
     * Get postal code for the address
     * @return postalCode
     */
    public int getPostalCode() {
        return this.postalCode;
    }

    /**
     * Set postal code for the address
     * @param postalCode
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}