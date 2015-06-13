/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.ErrorHandling;

import com.dinggoapplication.Entity.Merchant;

/**
 * An exception where merchant cannot be found
 * Created by Leon on 23/2/2015.
 */
public class MerchantNotFoundException extends Exception {
    /**
     * Merchant that is related to the exception
     */
    Merchant merchant;

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     */
    public MerchantNotFoundException(String detailMessage) {
        super(detailMessage);
        this.merchant = null;
    }

    /**
     * Constructor to initialize an Exception
     * @param merchant
     */
    public MerchantNotFoundException(Merchant merchant) {
        super();
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     * @param merchant
     */
    public MerchantNotFoundException(String detailMessage, Merchant merchant) {
        super(detailMessage);
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     * @param throwable
     * @param merchant
     */
    public MerchantNotFoundException(String detailMessage, Throwable throwable, Merchant merchant) {
        super(detailMessage, throwable);
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param throwable
     * @param merchant
     */
    public MerchantNotFoundException(Throwable throwable, Merchant merchant) {
        super(throwable);
        this.merchant = merchant;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " : " + merchant.getMerchantId() + " cannot be found!";
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage() + " : " + merchant.getMerchantId() + " cannot be found!";
    }
}
