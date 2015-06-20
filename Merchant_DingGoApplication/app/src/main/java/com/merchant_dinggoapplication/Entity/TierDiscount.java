/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Entity;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Tiered Discount Object - Certain amount off the spent amount for a certain tiered amount
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 17/2/2015.
 */
public class TierDiscount extends Deal {

    /** Amount required to spent to be entitled to the discount */
    private double tierAmount;
    /** Discount amount if entitlement is valid */
    private double discountAmount;

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant);
        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param createdAt         Date and time the deal is created
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param activated         Is the deal activated
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, boolean activated,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, activated);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param createdAt         Date and time the deal is created
     * @param activated         Is the deal activated
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, boolean activated,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt, activated);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param activatedDate     Date and time the deal is activated
     * @param closureDate       Date and time the deal is closed
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date activatedDate, Date closureDate,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, activatedDate, closureDate);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode     Reference code to identify deals
     * @param coverImage        Cover images for display purpose
     * @param merchant          Merchant that release the deal
     * @param createdAt         Date and time the deal is created
     * @param activatedDate     Date and time the deal is activated
     * @param closureDate       Date and time the deal is closed
     * @param tierAmount        The amount customer have to spend
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, Date activatedDate, Date closureDate,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt, activatedDate, closureDate);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Get tiered amount for the deal
     * @return Double value that contains the tier amount
     */
    public double getTierAmount() {
        return this.tierAmount;
    }

    /**
     * Set tiered amount for the deal
     * @param tierAmount    The amount customer have to spend
     */
    public void setTierAmount(double tierAmount) {
        this.tierAmount = tierAmount;
    }

    /**
     *  Get discount price for the deal
     * @return Double value that contains the discount amount
     */
    public double getDiscountAmount() {
        return this.discountAmount;
    }

    /**
     * Set discount price for the deal
     * @param discountAmount    The amount discounted according to the tier amount
     */
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Calculate the discounted price
     *
     * @param amountSpent Amount spent on restaurant
     * @return Price after discount
     */
    @Override
    public double afterDiscount(double amountSpent) {
        if (amountSpent >= tierAmount) {
            return amountSpent - discountAmount;
        } else {
            return amountSpent;
        }
    }

    /**
     * Formulate a string pattern for the deal
     *
     * @return String value that contain a summary about the deal
     */
    @Override
    public String toString() {
        return "$" + (long) this.discountAmount + " off $" + (long) this.tierAmount + " spent";
    }
}
