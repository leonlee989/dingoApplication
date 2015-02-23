package com.example.leon.dingoapplication.ErrorHandling;

import com.example.leon.dingoapplication.Entity.Merchant;

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
