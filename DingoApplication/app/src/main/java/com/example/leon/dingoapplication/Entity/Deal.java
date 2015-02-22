
package com.example.leon.dingoapplication.Entity;

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
     * Constructor to initialize a Deal Object with the following parameters:
     * @param referenceCode
     */
    public Deal(String referenceCode) {
        this.referenceCode = referenceCode;
        activated = false;
        createdAt = new Date();

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize the object with the following parameters
     * @param referenceCode
     * @param createdAt
     */
    public Deal (String referenceCode, Date createdAt) {
        this.referenceCode = referenceCode;
        this.activated = false;
        this.createdAt = createdAt;

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize Deal object with the following parameters:
     * @param referenceCode
     * @param activated
     */
    public Deal(String referenceCode, boolean activated) {
        this.referenceCode = referenceCode;
        setActivated(activated);
        this.createdAt = new Date();

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialize Deal object with the following parameters
     * @param referenceCode
     * @param createdAt
     * @param activated
     */
    public Deal(String referenceCode, Date createdAt, boolean activated) {
        this.referenceCode = referenceCode;
        this.createdAt = createdAt;
        setActivated(activated);

        this.activatedDate = null;
        this.closureDate = null;
    }

    /**
     * Constructor to initialized Deal object with the following parameters:
     * @param referenceCode
     * @param activatedDate
     * @param closureDate
     */
    public Deal(String referenceCode, Date activatedDate, Date closureDate) {
        this.referenceCode = referenceCode;
        this.createdAt = new Date();

        setActivatedDate(activatedDate);
        setClosureDate(closureDate);
    }

    /**
     * Constructor to initialized Deal object with the following parameters
     * @param referenceCode
     * @param createdAt
     * @param activatedDate
     * @param closureDate
     */
    public Deal(String referenceCode, Date createdAt, Date activatedDate, Date closureDate) {
        this.referenceCode = referenceCode;
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