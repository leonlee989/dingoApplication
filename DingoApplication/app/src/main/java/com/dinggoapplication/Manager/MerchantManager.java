/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Manager;

import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.ErrorHandling.MerchantNotFoundException;

import java.util.ArrayList;

/**
 * Manager object to handle all merchant in the system
 * Created by Leon on 23/2/2015.
 */
public class MerchantManager {
    /**
     * List of merchant available in the system
     */
    ArrayList<Merchant> merchantList;

    /**
     * Default constructor to initialize MerchantManager Object
     */
    public MerchantManager() {
        this.merchantList = new ArrayList<Merchant>();
    }

    /**
     * Constructor to initialize MerchantManager Object with the following parameters
     * @param merchantList
     */
    public MerchantManager(ArrayList<Merchant> merchantList) {
        this.merchantList = merchantList;
    }

    /**
     * Get the list of merchant
     * @return
     */
    public ArrayList<Merchant> getMerchantList() {
        return this.merchantList;
    }

    /**
     * Retrieve Merchant Object by Index
     * @param index
     * @return merchant
     */
    public Merchant getMerchant(int index) {
        return this.merchantList.get(index);
    }

    /**
     * Retrieve merchant by merchantId
     * @param merchantId
     * @return
     */
    public Merchant getMerchant(String merchantId) {
        for (Merchant merchant:merchantList) {
            if (merchant.getMerchantId().equalsIgnoreCase(merchantId)) {
                return merchant;
            }
        }

        return null;
    }

    /**
     * Set the list of merchant
     * @param merchantList
     */
    public void setMerchantList(ArrayList<Merchant> merchantList){
        this.merchantList = merchantList;
    }

    /**
     * Add a new merchant into the list
     * @param merchant
     */
    public void addMerchant(Merchant merchant) {
        this.merchantList.add(merchant);
    }

    /**
     * Remove merchant with the merchant ID as the parameter
     * @param merchantId
     * @throws MerchantNotFoundException
     */
    public void removeMerchant(String merchantId) throws MerchantNotFoundException {

        boolean removedMerchant = false;

        for (int i=0; i < merchantList.size(); i++) {
            Merchant merchant = merchantList.get(i);
            if (merchant.getMerchantId().equalsIgnoreCase(merchantId)) {
                merchantList.remove(i);
            }
        }

        if (!removedMerchant) {
            throw new MerchantNotFoundException(merchantId + " is not found!");
        }
    }

    /**
     * Remove merchant with the merchant object as a parameter
     * @param removeMerchant
     * @throws MerchantNotFoundException
     */
    public void removeMerchant(Merchant removeMerchant) throws MerchantNotFoundException {
        boolean removedMerchant = false;

        for (int i=0; i < merchantList.size(); i++) {
            Merchant merchant = merchantList.get(i);
            if (merchant.getMerchantId().equalsIgnoreCase(removeMerchant.getMerchantId())) {
                merchantList.remove(i);
            }
        }

        if (!removedMerchant) {
            throw new MerchantNotFoundException(removeMerchant);
        }
    }
}