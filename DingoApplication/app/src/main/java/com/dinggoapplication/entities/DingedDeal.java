package com.dinggoapplication.entities;

import android.util.Log;

import com.dinggoapplication.utilities.LogUtils;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by mohmohhtun on 11/26/15.
 */
@ParseClassName("dinged_deal")
public class DingedDeal extends ParseObject {

    /**
     * Name of the table
     */
    public static final String TABLE_NAME = "dinged_deal";

    /**
     * Column name for cuisine name field
     */
    public static final String COLUMN_CONFIRMATIONID = "confirmationId",
            COLUMN_ISREVIEWED = "isReviewed",
            COLUMN_NUMDINGS = "numDings",
            COLUMN_DEALREFERENCEID = "referenceId",
            COLUMN_STATUS = "status",
            COLUMN_USERID = "userId",
            COLUMN_CREATEDAT = "createdAt",
            COLUMN_UPDATEDAT = "updatedAt";

    /**
     * Default constructor that instantiate the deal type object
     */
    public DingedDeal() {
    }

    public DingedDeal( int confirmationId, Boolean isRewviewed, int numDings,Deal referenceId, String status, ParseUser userId) {
        setConfirmationId(confirmationId);
        setIsReviewed(isRewviewed);
        setNumDings(numDings);
        setStatus(status);
        setUserId(userId);
        setDealReferenceId(referenceId);
        saveInBackground(LogUtils.saveCallback(DingedDeal.class.getName()));
    }

    public String getReferenceId() {
        return getObjectId();
    }


    public void setConfirmationId(int confirmationId) {
     put(COLUMN_CONFIRMATIONID, confirmationId);
    }

    public int getConfirmationId() {
        return getInt(COLUMN_CONFIRMATIONID);
    }

    public void setIsReviewed(Boolean isReviewed) {
       put(COLUMN_ISREVIEWED,isReviewed);
    }

    public  Boolean getIsReviewed() {
        return getBoolean(COLUMN_ISREVIEWED);
    }

    public void setNumDings(int numDings) {
        put(COLUMN_NUMDINGS, numDings);
    }

    public int getNumDings(){
        return  getInt(COLUMN_NUMDINGS);
    }

    public void setStatus(String status) {
       put(COLUMN_STATUS,status);
    }

    public String getStatus(){
        return getString(COLUMN_STATUS);
    }

    public void setDealReferenceId(Deal referenceId) {
       put(COLUMN_DEALREFERENCEID,referenceId);
    }

    public Deal getDealReferenceId() {

        Deal deal = (Deal) get(COLUMN_DEALREFERENCEID);

        if (!deal.isDataAvailable()) {
            try {
                deal.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return deal;
    }

    public void setUserId(ParseUser userId) {
      put(COLUMN_USERID,userId);
    }

    public ParseUser getUserId(){
        ParseUser parseuser = (ParseUser) get(COLUMN_USERID);
        if (!parseuser.isDataAvailable()) {
            try {
                parseuser.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return parseuser;
    }
}
