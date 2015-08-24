package com.dinggoapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version x.x
 * Created by Leon on 27/7/2015.
 */
public class TimeUtils {

    public static long timeDiffInMilliseconds(Date dateFrom, Date dateTo) {
        return dateTo.getTime() - dateFrom.getTime();
    }

    public static CountDownTimer setTimer(Date dateTo, final TextView textView) {
        
        long timeLeft = timeDiffInMilliseconds(new Date(), dateTo);
        return new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String countdownFormat = String.format("%dH %dM\n%dS",
                        (int) ((millisUntilFinished / (1000 * 60 * 60))),
                        (int) ((millisUntilFinished / (1000 * 60)) % 60),
                        (int) ((millisUntilFinished / 1000) % 60));

                textView.setTextKeepStates(countdownFormat);
            }

            @Override
            public void onFinish() {
                textView.setText("Expired!");
            }
        };
        /*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                context.runOnUiThread(new Runnable() {
                    long timeLeft = timeDiffInMilliseconds(new Date(), dateTo);

                    @Override
                    public void run() {

                        String countdownFormat = String.format("%dH %dM\n%dS",
                                (int) ((timeLeft / (1000*60*60))),
                                (int) ((timeLeft / (1000*60)) % 60),
                                (int) ((timeLeft / 1000) % 60));

                        textView.setDuplicateParentStateEnabled(true);
                        textView.setText(String.valueOf(countdownFormat));
                        timeLeft -= 1;
                    }

                });
            }
        }, 0, 1000);
        */
    }
}
