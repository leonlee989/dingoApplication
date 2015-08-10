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
 * PercentageDiscount Class - A certain percentage off the amount spent
 * @deprecated
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 16/2/2015.
 */
public class PercentageDiscount extends Deal {

    /** Percentage off the amount spent */
    private int percentage;

    /**
     * Constructor to initialize PercentageDiscount Object
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param percentage    The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, int percentage) {
        super(referenceCode, coverImage, merchant);
        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode Reference code to identify deals
     * @param merchant      Cover images for display purpose
     * @param createdAt     Merchant that release the deal
     * @param percentage    The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, int percentage) {
        super(referenceCode, coverImage, merchant, createdAt);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param activated     Is the deal activated
     * @param percentage    The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, boolean activated, int percentage) {
        super(referenceCode, coverImage, merchant, activated);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode Reference code to identify deals
     * @param coverImage    Cover images for display purpose
     * @param merchant      Merchant that release the deal
     * @param createdAt     Date and time the deal is created
     * @param activated     Is the deal activated
     * @param percentage    The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, boolean activated,
                              int percentage) {
        super(referenceCode, coverImage, merchant, createdAt, activated);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param activatedDate     Date and time the deal is activated
     * @param closureDate       Date and time the deal is closed
     * @param percentage        The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date activatedDate, Date closureDate,
                              int percentage) {
        super(referenceCode, coverImage, merchant, activatedDate, closureDate);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param createdAt         Date and time the deal is created
     * @param activatedDate     Date and time the deal is activated
     * @param closureDate       Date and time the deal is closed
     * @param percentage        The percentage off the amount spent
     */
    public PercentageDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, Date activatedDate,
                              Date closureDate, int percentage) {
        super(referenceCode, coverImage, merchant, createdAt, activatedDate, closureDate);

        this.percentage = percentage;
    }

    /**
     * Get the percentage discounted from the amount spent
     * @return  Double value that contains the percentage off from the amount spent
     */
    public double getPercentage() {
        return this.percentage;
    }

    /**
     * Set the percentage discounted from the amount spent
     * @param percentage    The percentage off the amount spent
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * Calculate the discounted price
     *
     * @param amountSpent Amount spent on restaurant
     * @return Price after discount
     */
    @Override
    public double afterDiscount(double amountSpent) {
        return (1 - this.percentage/100) * amountSpent;
    }

    /**
     * Formulate a string pattern for the deal
     *
     * @return String value that contain a summary about the deal
     */
    @Override
    public String toString() {
        return this.percentage + " % OFF";
    }
}