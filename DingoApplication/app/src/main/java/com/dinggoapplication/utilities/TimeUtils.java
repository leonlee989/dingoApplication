package com.dinggoapplication.utilities;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import java.util.Date;

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

        final Handler handler = new Handler();
        long timeLeft = timeDiffInMilliseconds(new Date(), dateTo);

        return new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                final String countdownFormat = String.format("%dH %dM\n%dS",
                        (int) ((millisUntilFinished / (1000 * 60 * 60))),
                        (int) ((millisUntilFinished / (1000 * 60)) % 60),
                        (int) ((millisUntilFinished / 1000) % 60));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.clearFocus();
                        textView.setTextKeepState(countdownFormat);
                    }
                });
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
