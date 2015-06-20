/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Manager;

import com.merchant_dinggoapplication.Entity.Merchant;
import com.merchant_dinggoapplication.ErrorHandling.MerchantNotFoundException;

import java.util.ArrayList;

/**
 * Manager object to handle all merchant in the system
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class MerchantManager {
    /** List of merchant available in the system */
    ArrayList<Merchant> merchantList;

    /**
     * Default constructor to initialize MerchantManager Object
     */
    public MerchantManager() {
        this.merchantList = new ArrayList<>();
    }

    /**
     * Constructor to initialize MerchantManager Object with the following parameters
     * @param merchantList  A list of merchant available in the system
     */
    public MerchantManager(ArrayList<Merchant> merchantList) {
        this.merchantList = merchantList;
    }

    /**
     * Get the list of merchant
     * @return  The list of merchant available in the class
     */
    public ArrayList<Merchant> getMerchantList() {
        return this.merchantList;
    }

    /**
     * Retrieve Merchant Object by Index
     * @param index         Position of the merchant in the list
     * @return merchant     Merchant object that contains all details about the merchant
     */
    public Merchant getMerchant(int index) {
        return this.merchantList.get(index);
    }

    /**
     * Retrieve merchant by merchantId
     * @param merchantId    ID of a particular merchant
     * @return              Merchant object with the ID
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
     * @param merchantList  A new list of merchant to be replaced
     */
    public void setMerchantList(ArrayList<Merchant> merchantList){
        this.merchantList = merchantList;
    }

    /**
     * Add a new merchant into the list
     * @param merchant  New merchant object to be added into the list
     */
    public void addMerchant(Merchant merchant) {
        this.merchantList.add(merchant);
    }

    /**
     * Remove merchant with the merchant ID as the parameter
     * @param merchantId    ID of the merchant to be removed
     * @throws MerchantNotFoundException
     */
    public void removeMerchant(String merchantId) throws MerchantNotFoundException {

        boolean removedMerchant = false;

        for (int i=0; i < merchantList.size(); i++) {
            Merchant merchant = merchantList.get(i);
            if (merchant.getMerchantId().equalsIgnoreCase(merchantId)) {
                merchantList.remove(i);

                removedMerchant = true;
            }
        }

        if (!removedMerchant) {
            throw new MerchantNotFoundException(merchantId + " is not found!");
        }
    }

    /**
     * Remove merchant with the merchant object as a parameter
     * @param removeMerchant    Merchant object to be removed
     * @throws MerchantNotFoundException
     */
    public void removeMerchant(Merchant removeMerchant) throws MerchantNotFoundException {
        boolean removedMerchant = false;

        for (int i=0; i < merchantList.size(); i++) {
            Merchant merchant = merchantList.get(i);
            if (merchant.getMerchantId().equalsIgnoreCase(removeMerchant.getMerchantId())) {
                merchantList.remove(i);

                removedMerchant = true;
            }
        }

        if (!removedMerchant) {
            throw new MerchantNotFoundException(removeMerchant);
        }
    }
}