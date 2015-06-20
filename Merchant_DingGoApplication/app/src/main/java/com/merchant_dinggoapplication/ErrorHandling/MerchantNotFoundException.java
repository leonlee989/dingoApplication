/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.ErrorHandling;

import com.merchant_dinggoapplication.Entity.Merchant;

/**
 * An exception class to handle merchant object
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class MerchantNotFoundException extends Exception {
    /** Merchant that is related to the exception */
    Merchant merchant;

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     */
    public MerchantNotFoundException(String detailMessage) {
        super(detailMessage);
        this.merchant = null;
    }

    /**
     * Constructor to initialize an Exception
     * @param merchant  A particular merchant object to handle exception
     */
    public MerchantNotFoundException(Merchant merchant) {
        super();
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     * @param merchant          A particular merchant object to handle exception
     */
    public MerchantNotFoundException(String detailMessage, Merchant merchant) {
        super(detailMessage);
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     * @param throwable         Throwable object for stack flow
     * @param merchant          A particular merchant object to handle exception
     */
    public MerchantNotFoundException(String detailMessage, Throwable throwable, Merchant merchant) {
        super(detailMessage, throwable);
        this.merchant = merchant;
    }

    /**
     * Constructor to initialize an Exception
     * @param throwable     Throwable object for stack flow
     * @param merchant      A particular merchant object to handle exception
     */
    public MerchantNotFoundException(Throwable throwable, Merchant merchant) {
        super(throwable);
        this.merchant = merchant;
    }

    /**
     * Returns the detail message which was provided when this
     * {@code Throwable} was created. Returns {@code null} if no message was
     * provided at creation time. Subclasses may override this method to return
     * localized text for the message. Android returns the regular detail message.
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage() + " : " + merchant.getMerchantId() + " cannot be found!";
    }

    /**
     * Returns the detail message which was provided when this
     * {@code Throwable} was created. Returns {@code null} if no message was
     * provided at creation time.
     */
    @Override
    public String getMessage() {
        return super.getMessage() + " : " + merchant.getMerchantId() + " cannot be found!";
    }
}