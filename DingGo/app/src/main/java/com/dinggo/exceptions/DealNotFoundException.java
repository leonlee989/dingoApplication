/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggo.exceptions;

import com.dinggo.entities.Deal;

/**
 * Exception class to handle deals objects
 * @deprecated
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class DealNotFoundException extends Exception {
    /** Handle exception for a particular deal */
    Deal deal;

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     */
    public DealNotFoundException(String detailMessage) {
        super(detailMessage);
        this.deal = null;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     * @param deal              A particular deal to handle for exceptions
     */
    public DealNotFoundException(String detailMessage, Deal deal) {
        super(detailMessage);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage     Message to display as an exception
     * @param throwable         Throwable object for stack flow
     * @param deal              A particular deal to handle for exceptions
     */
    public DealNotFoundException(String detailMessage, Throwable throwable, Deal deal) {
        super(detailMessage, throwable);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param throwable     Throwable object for stack flow
     * @param deal          A particular deal to handle for exceptions
     */
    public DealNotFoundException(Throwable throwable, Deal deal) {
        super(throwable);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param deal  A particular deal to handle for exceptions
     */
    public DealNotFoundException(Deal deal) {
        super();
        this.deal = deal;
    }

    /**
     * Returns the detail message which was provided when this
     * {@code Throwable} was created. Returns {@code null} if no message was
     * provided at creation time. Subclasses may override this method to return
     * localized text for the message. Android returns the regular detail message.
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage() + ": " + deal.getReferenceId() + " cannot be found!";
    }

    /**
     * Returns the detail message which was provided when this
     * {@code Throwable} was created. Returns {@code null} if no message was
     * provided at creation time.
     */
    @Override
    public String getMessage() {
        return super.getMessage() + ": " + deal.getReferenceId() + " cannot be found!";
    }
}
