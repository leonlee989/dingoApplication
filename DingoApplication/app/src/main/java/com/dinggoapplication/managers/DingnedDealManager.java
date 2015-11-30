package com.dinggoapplication.managers;

import android.util.Log;

import com.dinggoapplication.entities.DingedDeal;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

/**
 * Created by mohmohhtun on 11/26/15.
 */
public class DingnedDealManager {
    /** Singleton instance of Deal Manager Object */
    private static DingnedDealManager instance;
    /** Number of checked out for the instance */
    private static int checkOut = 0;
    /** The name of the object the manager class is handling */
    private final static String PARSE_NAME = "dinged_deal";

    private boolean isDinged;

    List<DingedDeal> dingedDealList;
    /**
     * Default constructor for Deal Manager class
     */
    private DingnedDealManager() {}

    /**
     * Singleton handler that retrieves the single instance of DealManager object
     * @return  Single instance of DealManager Object
     */
    public static DingnedDealManager getInstance() {
        synchronized (DingnedDealManager.class) {
            if(instance == null) {
                instance = new DingnedDealManager();
            }
        }
        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) { checkOut++; }
        return instance;
    }

    /**
     * Retrieve the number of checkouts for the DealManager Object
     * @return  Integer value that contains the number of checkouts for the DealManager Object
     */
    public int getCheckOut() {
        return checkOut;
    }


    /**
     * Retrieve the deal according to the provided reference ID
     * @param referenceId   String value that contains the reference ID to identify a Deal in the
     *                      local data store
     * @return              Deal object that contains the provided reference ID
     * @throws ParseException
     */
    public DingedDeal getDingedDeal(String referenceId,String userId) throws ParseException {
        ParseQuery<DingedDeal> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("referenceId", referenceId);
        query.whereEqualTo("userId", userId);
        List<DingedDeal> dingedDeal = query.find();
        Log.d("LIST", String.valueOf(dingedDeal.size()));
        return dingedDeal.get(0);
    }
    public List<DingedDeal> getLastDeal1() throws ParseException{
        ParseQuery<DingedDeal> query = ParseQuery.getQuery(PARSE_NAME);
        query.orderByDescending("confirmationId");
        List<DingedDeal> dingedDeal = query.find();
        return  dingedDeal;
    }

    public DingedDeal getLastDeal() throws ParseException{
        ParseQuery<DingedDeal> query = ParseQuery.getQuery(PARSE_NAME);
        query.orderByDescending("confirmationId");
        List<DingedDeal> dingedDeal = query.find();
        return  dingedDeal.get(0);
    }


    public boolean isDing(ParseUser user,Date today) throws ParseException{
        ParseQuery<DingedDeal> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("userId", user);

        query.whereGreaterThan("createdAt", today);
        try {
            dingedDealList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dingedDealList.size()!=0){
            Log.d("Deal size",dingedDealList.get(0).toString());
            isDinged = false;
            return isDinged;
        }else
        {
            Log.d("Deal size","NULL");
            isDinged = true;
            return isDinged;
        }


    }

    public List<DingedDeal> getOngoingDeals(ParseUser user,String status){

        ParseQuery<DingedDeal> query = ParseQuery.getQuery(PARSE_NAME);
        query.whereEqualTo("userId", user);
        query.whereEqualTo("status",status);

//        query.findInBackground(new FindCallback<DingedDeal>() {
//            public void done(List<DingedDeal> scoreList, ParseException e) {
//                if (scoreList.size() != 0) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                   dingedDealList.addAll(scoreList);
//                } else {
//                    Log.d("NUll","Ongoing");
//                }
//            }
//        });
        try {
            dingedDealList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dingedDealList;
    }

}
