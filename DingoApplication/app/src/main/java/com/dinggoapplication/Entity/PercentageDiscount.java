package com.dinggoapplication.Entity;

import java.util.Date;

/**
 * Created by Leon on 16/2/2015.
 * PercentageDiscount Class - A certain percentage off the amount spent
 */
public class PercentageDiscount extends Deal {

    /**
     * Percentage off the amount spent
     */
    int percentage;

    /**
     * Constructor to initialize PercentageDiscount Object
     * @param referenceCode
     * @param merchant
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, int percentage) {
        super(referenceCode, merchant);
        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode
     * @param merchant
     * @param createdAt
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, Date createdAt, int percentage) {
        super(referenceCode, merchant, createdAt);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode
     * @param merchant
     * @param activated
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, boolean activated, int percentage) {
        super(referenceCode, merchant, activated);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode
     * @param merchant
     * @param createdAt
     * @param activated
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, Date createdAt, boolean activated,
                              int percentage) {
        super(referenceCode, merchant, createdAt, activated);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode
     * @param merchant
     * @param activatedDate
     * @param closureDate
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, Date activatedDate, Date closureDate,
                              int percentage) {
        super(referenceCode, merchant, activatedDate, closureDate);

        this.percentage = percentage;
    }

    /**
     * Constructor to initialize PercentageDiscount Object with the following parameters
     * @param referenceCode
     * @param merchant
     * @param createdAt
     * @param activatedDate
     * @param closureDate
     * @param percentage
     */
    public PercentageDiscount(String referenceCode, Merchant merchant, Date createdAt, Date activatedDate,
                              Date closureDate, int percentage) {
        super(referenceCode, merchant, createdAt, activatedDate, closureDate);

        this.percentage = percentage;
    }

    /**
     * Get the percentage discounted from the amount spent
     * @return percentage
     */
    public double getPercentage() {
        return this.percentage;
    }

    /**
     * Set the percentage discounted from the amount spent
     * @param percentage
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public double afterDiscount(double amountSpent) {
        return (1 - this.percentage/100) * amountSpent;
    }

    @Override
    public String toString() {
        return this.percentage + " % OFF";
    }
}