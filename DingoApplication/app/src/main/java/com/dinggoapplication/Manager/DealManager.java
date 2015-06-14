/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Manager;

import com.dinggoapplication.Entity.Deal;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.ErrorHandling.DealNotFoundException;

import java.util.ArrayList;

/**
 * Deal Manager class that handles all deals in the application
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 25/2/2015.
 */
public class DealManager {
    /** A list of deal available */
    ArrayList<Deal> dealList;

    /**
     * Default constructor to initialize DealManager
     */
    public DealManager() {
        dealList = new ArrayList<>();
    }

    /**
     * Retrieve the whole list of deals
     * @return  A list deals available in the system
     */
    public ArrayList<Deal> getDealList() {
        return this.dealList;
    }

    /**
     * Get a single deal by the position of the array
     * @param index     The position of the deal in the list
     * @return          Deal object that contains all the details of the deal
     */
    public Deal getDeal(int index) {
        return dealList.get(index);
    }

    /**
     * Get a single deal by reference code
     * @param id    Reference code of the deal
     * @return      Deal object that contains all the details of the deal
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
     * @param dealList  A new of deals
     */
    public void setDealList(ArrayList<Deal> dealList) {
        this.dealList = dealList;
    }

    /**
     * Add deals to the manager
     * @param deal  Add a new deal into the list
     */
    public void addDeal(Deal deal) {
        dealList.add(deal);
    }

    /**
     * Remove a deal from deal manager
     * @param removeDeal    Remove a particular deal from the list
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

    /**
     * Retrieve list of deals offered by the respective merchant
     * @param merchantId    ID of the merchant who offers the deal
     * @return              The list of deals offered by the merchants
     */
    public ArrayList<Deal> retrieveDealByMerchant(String merchantId) {
        ArrayList<Deal> merchantList = new ArrayList<>();

        for (Deal deal : this.dealList) {
            Merchant merchant = deal.getMerchant();

            if (merchant.getMerchantId().equalsIgnoreCase(merchantId)) {
                merchantList.add(deal);
            }
        }

        return merchantList;
    }

    /**
     * Retrieve list of deals by providing a specific category with a value
     * @param category  Category of the deal
     * @param value     Value to retrieve deal by the category given
     * @return          A list of deals filtered by category
     */
    public ArrayList<Deal> retrieveDealByCategory(String category, String value) {
        ArrayList<Deal> merchantList = new ArrayList<>();

        if (category.equalsIgnoreCase("Merchant Type")) {
            for (Deal deal : this.dealList) {
                Merchant merchant = deal.getMerchant();

                if (merchant.getMerchantType().equalsIgnoreCase(value)) {
                    merchantList.add(deal);
                }
            }
        }
        return merchantList;
    }
}