/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Entity;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Leon on 16/2/2015.
 * Abstract class to hold all the different type of deals
 */
public abstract class Deal {
    /**
     * Reference code to a particular deal
     */
    String referenceCode;

    /**
     * Cover image for the merchant products
     */
    Bitmap coverImage;
    /**
     *  Boolean to determine whether the deal is activated or not
     */
    boolean activated;

    /**
     * Date of activation
     */
    Date activatedDate;

    /**
     * Date of deactivation
     */
    Date closureDate;

    /**
     * Deal the deal is created by restaurant
     */
    Date createdAt;

    /**
     * Merchant that is involve in the deal
     */
    Merchant merchant;

    
    /**
     * Constructor to initialize a Deal Object with the following parameters:
     * @param referenceCode
     * @param coverImage
     * @param merchant
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;

        this.merchant = merchant;
        activated = false;
        createdAt = new Date();

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize the object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     */
    public Deal (String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;
        this.merchant = merchant;
        this.activated = false;
        this.createdAt = createdAt;

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize Deal object with the following parameters:
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param activated
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, boolean activated) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;
        this.merchant = merchant;
        setActivated(activated);
        this.createdAt = new Date();

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize Deal object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     * @param activated
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, boolean activated) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;
        this.merchant = merchant;
        this.createdAt = createdAt;
        setActivated(activated);

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialized Deal object with the following parameters:
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param activatedDate
     * @param closureDate
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, Date activatedDate, Date closureDate) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;
        this.merchant = merchant;
        this.createdAt = new Date();

        setActivatedDate(activatedDate);
        setClosureDate(closureDate);
    }

    /**
     * Constructor to initialized Deal object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     * @param activatedDate
     * @param closureDate
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, Date activatedDate, Date closureDate) {
        this.referenceCode = referenceCode;
        this.coverImage = coverImage;
        this.merchant = merchant;
        this.createdAt = createdAt;

        setActivatedDate(activatedDate);
        setClosureDate(closureDate);
    }

    /**
     * Get reference code of the deal
     * @return  referenceCode
     */
    public String getReferenceCode(){
        return this.referenceCode;
    }

    /**
     * Set reference code for the deal
     * @param referenceCode
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /**
     * Get the cover image for the merchant
     * @return coverImage
     */
    public Bitmap getCoverImage() {
        return coverImage;
    }

    /**
     * Set the cover image for the merchant
     * @param coverImage
     */
    public void setCoverImage(Bitmap coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * Get the merchant who offers this deal
     * @return merchant
     */
    public Merchant getMerchant() {
        return  this.merchant;
    }

    /**
     * Set the merchant who offered this deal
     * @param merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * Whether deal is being activated or not
     * @return boolean for activation
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Activate or deactivate deals
     * @param activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
        if (activated) {
            this.activatedDate = new Date();
        }
    }

    /**
     * Method to toggle activation
     */
    public void toggleActivation() {
        if (activated) {
            activated = false;
            this.closureDate = new Date();
        } else {
            activated = true;
            this.activatedDate = new Date();
        }
    }

    /**
     * Get the activation date for the deal
     * @return activatedDate
     */
    public Date getActivatedDate() {
        return this.activatedDate;
    }

    /**
     * Set the activation date for the deal
     * @param activatedDate
     */
    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;

        Date todayTime = new Date();
        // Different in today time by milliseconds
        long diff = this.activatedDate.getTime() - todayTime.getTime();

        if  (diff <= 0) {
            this.activated = true;
        } else {
            this.activated = false;
        }
    }

    /**
     * Get the closure date for the deal
     * @return closureDate
     */
    public Date getClosureDate() {
        return this.closureDate;
    }

    /**
     * Set the closure date for the deal
     * @param closureDate
     */
    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;

        if (activated) {
            Date todayTime = new Date();
            // Different in today time by milliseconds
            long diff = this.closureDate.getTime() - todayTime.getTime();

            if (diff > 0) {
                this.activated = true;
            } else {
                this.activated = false;
            }
        }
    }

    /**
     * Get the date of creation for the deal
     * @return createdAt
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Set the date of creation for the deal
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * Calculate the discounted price
     * @param amountSpent
     * @return After Discount Price
     */
    public abstract double afterDiscount(double amountSpent);

    /**
     * Formulate a string pattern for the deal
     * @return String
     */
    public abstract String toString();

    /**
     * Enumerator class for deal type
     */
    public enum DealType {
        PERCENTAGE_DISCOUNT, TIER_DISCOUNT;
    }
}