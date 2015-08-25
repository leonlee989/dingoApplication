package com.dinggoapplication.entities;

import android.util.Log;

import com.dinggoapplication.utilities.LogUtils;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Class that contains all the information with regards to the deal
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 28/7/2015.
 */
@ParseClassName("deal")
public class Deal extends ParseObject {
    /** Mame of the table */
    public final static String TABLE_NAME = "deal";

    /** Column name for Deal field */
    public final static String COLUMN_BRANCH_ID = "branch_id",
            COLUMN_DEAL_NAME = "dealName",
            COLUMN_DESC = "description",
            COLUMN_DEAL_TYPE = "dealType",
            COLUMN_TERM_CONDITIONS = "term_conditions",
            COLUMN_SEAT_OFFER = "seatToOffer",
            COLUMN_REDEEM_BY = "redeemBy",
            COLUMN_STATUS = "dealStatus";

    /** Default constructor that instantiate the Deal Object */
    public Deal() {}

    /**
     * Constructor that instantiate the Deal object with the following argument
     * @param branch            Branch who offered the deal
     * @param dealName          Name of the deal
     * @param description       Description about the deal
     * @param dealType          Type of deal
     * @param termConditions    Terms and conditions to use the deal
     * @param seatToOffer       Number of seats offered for the deal
     * @param redeemBy          Date and time to be redeemed by
     */
    public Deal(Branch branch, String dealName, String description, DealType dealType, String termConditions, int seatToOffer, Date redeemBy, DealStatus status) {
        setBranch(branch);
        setDealName(dealName);
        setDescription(description);
        setDealType(dealType);
        setTermConditions(termConditions);
        setSeatToOffer(seatToOffer);
        setRedeemBy(redeemBy);
        setDealStatus(status);

        saveInBackground(LogUtils.saveCallback(Deal.class.getName()));
    }

    /**
     * Retrieve the ID of the deal
     * @return  String value that contains the ID of the deal
     */
    public String getReferenceId() {
        return getObjectId();
    }

    /**
     * Retrieve the branch who offered this deal
     * @return  Branch object that contains the information about the branch who offered the deal
     */
    public Branch getBranch() {
        Branch branch = (Branch) get(COLUMN_BRANCH_ID);

        if (!branch.isDataAvailable()) {
            try {
                branch.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return branch;
    }

    /**
     * Set and change the branch who will offer the deal
     * @param branch    New branch who will offer this deal
     */
    public void setBranch(Branch branch) {
        put(COLUMN_BRANCH_ID, branch);
    }

    /**
     * Retrieve the name of the deal
     * @return String value that contains the name of the deal
     */
    public String getDealName() {
        return getString(COLUMN_DEAL_NAME);
    }

    /**
     * Set and change the name of the deal
     * @param dealName  String value that contains the name of the deal
     */
    public void setDealName(String dealName) {
        put(COLUMN_DEAL_NAME, dealName);
    }

    /**
     * Retrieve the description about the deal
     * @return  String value that contains the description about the deal
     */
    public String getDescription() {
        return getString(COLUMN_DESC);
    }

    /**
     * Set and change the description about the deal
     * @param description   String value that contains the new description about the deal
     */
    public void setDescription(String description) {
        put(COLUMN_DESC, description);
    }

    /**
     * Retrieve the type of deal offered
     * @return  DealType object that contains the information about the type of deal
     */
    public DealType getDealType() {
        DealType dealType = (DealType) get(COLUMN_DEAL_TYPE);

        if (!dealType.isDataAvailable()) {
            try {
                dealType.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return dealType;
    }

    /**
     * Set and change the type of deal offered
     * @param dealType  New DealType object to replace the existing one
     */
    public void setDealType(DealType dealType) {
        put(COLUMN_DEAL_TYPE, dealType);
    }

    /**
     * Retrieve the terms and conditions to use the deal
     * @return  String value that contains the terms and condition to use the deal
     */
    public String getTermConditions() {
        return getString(COLUMN_TERM_CONDITIONS);
    }

    /**
     * Set anc change the terms and conditions to use the deal
     * @param termConditions    String value that contains the new terms and condition to use the deal
     */
    public void setTermConditions(String termConditions) {
        put(COLUMN_TERM_CONDITIONS, termConditions);
    }

    /**
     * Retrieve the number of seats offered for the deal
     * @return  Integer value that contains the number of seats offered for the deal
     */
    public int getSeatToOffer() {
        return getInt(COLUMN_SEAT_OFFER);
    }

    /**
     * Set and change the number of seats offered for the deal
     * @param seatToOffer   Integer value that contains the new number of seats offered for the deal
     */
    public void setSeatToOffer(int seatToOffer) {
        put(COLUMN_SEAT_OFFER, seatToOffer);
    }

    /**
     * Retrieve the date and time to redeem the deal
     * @return  Date object that contains the date to redeem the deal
     */
    public Date getRedeemBy() {
        return getDate(COLUMN_REDEEM_BY);
    }

    /**
     * Set and change the date and time to redeem the deal
     * @param redeemBy  New Date object that contains the date to redeem the deal
     */
    public void setRedeemBy(Date redeemBy) {
        put(COLUMN_REDEEM_BY, redeemBy);
    }

    public DealStatus getDealStatus() {
        String status = getString(COLUMN_STATUS);
        return DealStatus.valueOf(status);
    }

    public void setDealStatus(DealStatus status) {
        put(COLUMN_STATUS, status.name());
    }

    public enum DealStatus {
        ACTIVE, WITHDRAWN, EXPIRED
    }
}