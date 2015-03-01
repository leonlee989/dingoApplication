package com.dinggoapplication.ErrorHandling;

import com.dinggoapplication.Entity.Deal;

/**
 * Created by Leon on 23/2/2015.
 */
public class DealNotFoundException extends Exception {
    Deal deal;

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     */
    public DealNotFoundException(String detailMessage) {
        super(detailMessage);
        this.deal = null;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     * @param deal
     */
    public DealNotFoundException(String detailMessage, Deal deal) {
        super(detailMessage);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param detailMessage
     * @param throwable
     * @param deal
     */
    public DealNotFoundException(String detailMessage, Throwable throwable, Deal deal) {
        super(detailMessage, throwable);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param throwable
     * @param deal
     */
    public DealNotFoundException(Throwable throwable, Deal deal) {
        super(throwable);
        this.deal = deal;
    }

    /**
     * Constructor to initialize an Exception
     * @param deal
     */
    public DealNotFoundException(Deal deal) {
        super();
        this.deal = deal;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage() + ": " + deal.getReferenceCode() + " cannot be found!";
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": " + deal.getReferenceCode() + " cannot be found!";
    }
}
