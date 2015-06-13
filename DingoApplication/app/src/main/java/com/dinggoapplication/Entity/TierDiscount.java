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
 * Created by Leon on 17/2/2015.
 * Tiered Discount Object - Certain amount off the spent amount for a certain tiered amount
 */
public class TierDiscount extends Deal {

    /**
     *`Amount required to spent to be entitled to the discount
     */
    double tierAmount;
    /**
     * Discount amount if entitlement is valid
     */
    double discountAmount;

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant);
        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param activated
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, boolean activated,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, activated);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     * @param activated
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, boolean activated,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt, activated);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param activatedDate
     * @param closureDate
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date activatedDate, Date closureDate,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, activatedDate, closureDate);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Constructor to initialize a TierDiscount Object with the following parameters
     * @param referenceCode
     * @param coverImage
     * @param merchant
     * @param createdAt
     * @param activatedDate
     * @param closureDate
     * @param tierAmount
     * @param discountAmount
     */
    public TierDiscount(String referenceCode, Bitmap coverImage, Merchant merchant, Date createdAt, Date activatedDate, Date closureDate,
                        double tierAmount, double discountAmount) {
        super(referenceCode, coverImage, merchant, createdAt, activatedDate, closureDate);

        this.tierAmount = tierAmount;
        this.discountAmount = discountAmount;
    }

    /**
     * Get tiered amount for the deal
     * @return tierAmount
     */
    public double getTierAmount() {
        return this.tierAmount;
    }

    /**
     * Set tiered amount for the deal
     * @param tierAmount
     */
    public void setTierAmount(double tierAmount) {
        this.tierAmount = tierAmount;
    }

    /**
     *  Get discount price for the deal
     * @return discountAmount
     */
    public double getDiscountAmount() {
        return this.discountAmount;
    }

    /**
     * Set discount price for the deal
     * @param discountAmount
     */
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double afterDiscount(double amountSpent) {
        if (amountSpent >= tierAmount) {
            return amountSpent - discountAmount;
        } else {
            return amountSpent;
        }
    }

    @Override
    public String toString() {
        return "$" + (long) this.discountAmount + " off $" + (long) this.tierAmount + " spent";
    }
}
