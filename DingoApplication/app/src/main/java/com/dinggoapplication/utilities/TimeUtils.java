package com.dinggoapplication.utilities;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import static com.dinggoapplication.utilities.LogUtils.makeLogTag;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version x.x
 * Created by Leon on 27/7/2015.
 */
public class TimeUtils {
    /** String value that contains the tag name for this activity */
    private static final String TAG = makeLogTag(TimeUtils.class);

    public static long timeDiffInMilliseconds(Date dateFrom, Date dateTo) {
        return dateTo.getTime() - dateFrom.getTime();
    }

    public static CountDownTimer setTimer(Date dateTo, final TextView textView, final NestedScrollView nSV) {

        long timeLeft = timeDiffInMilliseconds(new Date(), dateTo);

        return new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String countdownFormat = String.format("%dH %dM\n%dS",
                        (int) ((millisUntilFinished / (1000 * 60 * 60))),
                        (int) ((millisUntilFinished / (1000 * 60)) % 60),
                        (int) ((millisUntilFinished / 1000) % 60));
                if (isViewVisible(textView, nSV)) {
                    textView.setText(countdownFormat);
                }
                //Log.d(TAG, String.valueOf(isViewVisible(textView, nSV)));
            }

            @Override
            public void onFinish() {
                textView.setText("Expired!");
            }
        };
    }

    private static boolean isViewVisible(View view, NestedScrollView mScrollView) {
        //TODO getChildVisibleRect of ScrollView class
        Rect scrollBounds = new Rect();
        mScrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        return scrollBounds.top <= top && scrollBounds.bottom >= bottom;
    }

}
