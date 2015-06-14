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
    /** Reference code to a particular deal */
    String referenceCode;
    /** Cover image for the merchant products */
    Bitmap coverImage;
    /** Boolean to determine whether the deal is activated or not */
    boolean activated;
    /** Date of activation */
    Date activatedDate;
    /** Date of deactivation */
    Date closureDate;
    /** Deal the deal is created by restaurant*/
    Date createdAt;
    /** Merchant that is involve in the deal */
    Merchant merchant;
    
    /**
     * Constructor to initialize a Deal Object with the following parameters:
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant) {
        this(referenceCode, coverImage, merchant, new Date(), null, null);
        activated = false;
    }

    /**
     * Constructor to initialize the object with the following parameters
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param createdAt     Date and time the deal is created
     */
    public Deal (String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt) {
        this(referenceCode, coverImage, merchant, createdAt, null, null);
        this.activated = false;

    }

    /**
     * Constructor to initialize Deal object with the following parameters:
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param activated     Is the deal activated
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, boolean activated) {
        this(referenceCode, coverImage, merchant, new Date(), null, null);
        setActivated(activated);
    }

    /**
     * Constructor to initialize Deal object with the following parameters
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param createdAt     Date and time the deal is created
     * @param activated     Is the deal activated
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, boolean activated) {
        this(referenceCode, coverImage, merchant, createdAt, null, null);
        setActivated(activated);
    }

    /**
     * Constructor to initialized Deal object with the following parameters:
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param activatedDate Date and time the deal is activated
     * @param closureDate   Date and time the deal is closed
     */
    public Deal(String referenceCode, Bitmap coverImage, Merchant merchant, Date activatedDate, Date closureDate) {
        this(referenceCode, coverImage, merchant, new Date(), activatedDate, closureDate);
    }

    /**
     * Constructor to initialized Deal object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param createdAt         Date and time the deal is created
     * @param activatedDate     Date and time the deal is activated
     * @param closureDate       Date and time the deal is closed
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
     * @return  A string value that contains the reference code of the deal
     */
    public String getReferenceCode(){
        return this.referenceCode;
    }

    /**
     * Set reference code for the deal
     * @param referenceCode A string value that contains the new reference code for the deal
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /**
     * Get the cover image for the merchant
     * @return A bitmap object that contains the image uploaded
     */
    public Bitmap getCoverImage() {
        return coverImage;
    }

    /**
     * Set the cover image for the merchant
     * @param coverImage    Bitmap object that contains the newly uploaded image
     */
    public void setCoverImage(Bitmap coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * Get the merchant who offers this deal
     * @return merchant that offers the deal
     */
    public Merchant getMerchant() {
        return  this.merchant;
    }

    /**
     * Set the merchant who offered this deal
     * @param merchant  New merchant that offers this deal
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
     * @param activated Whether deal is activated or not
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
     * @param activatedDate Date and time the deal is activated
     */
    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;

        if (activatedDate != null) {
            Date todayTime = new Date();
            // Different in today time by milliseconds
            long diff = this.activatedDate.getTime() - todayTime.getTime();

            this.activated = diff <= 0;
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
     * @param closureDate Date and time the deal is closed
     */
    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;

        if (closureDate != null) {
            if (activated) {
                Date todayTime = new Date();
                // Different in today time by milliseconds
                long diff = this.closureDate.getTime() - todayTime.getTime();

                this.activated = diff > 0;
            }
        }
    }

    /**
     * Get the date of creation for the deal
     * @return Date and time the deal is created
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Set the date of creation for the deal
     * @param createdAt Set a new date and time the deal is created
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Calculate the discounted price
     * @param amountSpent   Amount spent on restaurant
     * @return              Price after discount
     */
    public abstract double afterDiscount(double amountSpent);

    /**
     * Formulate a string pattern for the deal
     * @return String value that contain a summary about the deal
     */
    public abstract String toString();

    /**
     * Enumerator class for deal type
     */
    public enum DealType {
        /** Discount by percentage */
        PERCENTAGE_DISCOUNT,
        /** Discount by the amount user spent for a amount of discount */
        TIER_DISCOUNT
    }
}