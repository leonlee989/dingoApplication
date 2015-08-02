package com.dinggoapplication.managers;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 2/8/2015.
 */
@SuppressWarnings("SynchronizeOnNonFinalField")
public class DealManager {
    private static DealManager instance;
    private static int checkOut = 0;
    private final static String parseClassName = "deal";

    private DealManager() {}

    public static DealManager getInstance() {
        synchronized (DealManager.class) {
            if(instance == null) {
                instance = new DealManager();
            }
        }

        synchronized (instance) {
            checkOut++;
        }

        return instance;
    }

    public int getCheckOut() {
        return checkOut;
    }

    public void updateCacheList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClassName);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> dealList, ParseException e) {
                if (e != null) {
                    Log.e("Deal", "Unable to find deals from Parse Database");
                    return;
                }

                // Remove previously cache data
                ParseObject.unpinAllInBackground("dealList", new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        // Cache new results
                        ParseObject.pinAllInBackground("dealList", dealList);
                    }
                });
            }
        });
    }
}
