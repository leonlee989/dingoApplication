package com.example.leon.dingoapplication.Entity;

import android.graphics.Bitmap;

import com.example.leon.dingoapplication.ErrorHandling.DealNotFoundException;

import java.util.ArrayList;

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
     * Constructor to initialize Merchant Object
     * @param merchantId
     * @param image
     * @param companyName
     * @param merchantName
     * @param merchantType
     * @param address
     */
    public Merchant(String merchantId, Bitmap image, String companyName, String merchantName,
                    String merchantType, Address address) {
        this.merchantId = merchantId;
        this.image = image;
        this.companyName = companyName;
        this.merchantName = merchantName;
        this.merchantType = merchantType;
        this.address = address;
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
    public void setAddress(String houseNumber, String streetName, String unitNumber, int postalCode) {
        this.address = new Address(houseNumber, streetName, unitNumber, postalCode);
    }
}