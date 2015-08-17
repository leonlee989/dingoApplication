package com.dinggoapplication.managers;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version x.x
 * Created by Leon on 17/8/2015.
 */
public class ReviewManager {
    private static ReviewManager instance;
    private static int checkout;


    private ReviewManager() {}

    public static ReviewManager getInstance() {
        synchronized (ReviewManager.class) {
            if (instance == null) {
                instance = new ReviewManager();
            }
        }

        //noinspection SynchronizeOnNonFinalField
        synchronized (instance) {
            checkout++;
        }

        return instance;
    }

    public int getCheckout() {
        return checkout;
    }
}
