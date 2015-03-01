package com.dinggoapplication.Manager;

import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.ErrorHandling.DealNotFoundException;

import java.util.ArrayList;

/**
 * Deal Manager
 * Created by Leon on 25/2/2015.
 */
public class DealManager {
    /**
     * A list of deal available
     */
    ArrayList<Deal> dealList;

    /**
     * Default constructor to initialize DealManager
     */
    public DealManager() {
        dealList = new ArrayList<Deal>();
    }

    /**
     * Retrieve the whole list of deals
     * @return
     */
    public ArrayList<Deal> getDealList() {
        return this.dealList;
    }

    /**
     * Get a single deal by the position of the array
     * @param index
     * @return
     */
    public Deal getDeal(int index) {
        return dealList.get(index);
    }

    /**
     * Get a single deal by reference code
     * @param id
     * @return
     */
    public Deal getDeal(String id) {
        for(int i=0; i < this.dealList.size(); i++) {
            Deal deal = dealList.get(i);
            if (deal.getReferenceCode().equalsIgnoreCase(id)) {
                return deal;
            }
        }

        return null;
    }

    /**
     * Set the deals for the manager
     * @param dealList
     */
    public void setDealList(ArrayList<Deal> dealList) {
        this.dealList = dealList;
    }

    /**
     * Add deals to the manager
     * @param deal
     */
    public void addDeal(Deal deal) {
        dealList.add(deal);
    }

    /**
     * Remove a deal from deal manager
     * @param removeDeal
     * @throws DealNotFoundException
     */
    public void removeDeal(Deal removeDeal) throws DealNotFoundException {
        boolean dealRemoved = false;

        for (int i=0; i < dealList.size(); i++) {
            Deal deal = dealList.get(i);
            if (deal.getReferenceCode().equalsIgnoreCase(removeDeal.getReferenceCode())) {
                dealList.remove(i);
                dealRemoved = true;
            }
        }

        if (!dealRemoved) {
            throw new DealNotFoundException(removeDeal);
        }
    }
}
