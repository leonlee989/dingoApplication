package com.dinggoapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.Activities.DingADeal;
import com.dinggoapplication.Activities.EatDrinkActivity;
import com.dinggoapplication.Activities.OngoingDeal;
import com.dinggoapplication.Constants;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.Entity.PercentageDiscount;
import com.dinggoapplication.R;

/**
 * Created by Leon on 1/3/2015.
 */
public class DingConfirmationDialogFragment extends DialogFragment {
    private SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        // Set the style for the divider
        int titleDividerId = getResources().getIdentifier("android:id/titleDivider", null, null);
        View titleDivider = dialog.findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(getResources().getColor(R.color.red));
        }

        // Set the style for title
        int titleId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView titleView = (TextView) dialog.findViewById(titleId);
        titleView.setTextColor(getResources().getColor(R.color.grey));
        titleView.setTypeface(null, Typeface.BOLD);

        // Set the style for text message
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Retrieve Merchant Id From savedinstances
        sharedPreferences = getActivity().getSharedPreferences("MerchantData", Context.MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the builder contents
        builder.setMessage("Once confirmed, deal is on!")
                .setTitle("Confirmation")
                .setPositiveButton(R.string.response_yes, response_yes)
                .setNegativeButton(R.string.response_no, response_no);

        return builder.create();
    }

    DialogInterface.OnClickListener response_yes = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            ListView lv = (ListView) getActivity().findViewById(R.id.dingadeal_List);
            ListAdapter adapter = lv.getAdapter();

            for (int i=0; i < adapter.getCount(); i++) {
                DingADeal.DingADealOptions option = (DingADeal.DingADealOptions) adapter.getItem(i);
                View view = adapter.getView(i, null, null);

                switch (option.getValue()) {
                    case SELECTOR:
                        TextView value = (TextView) view.findViewById(R.id.option_label);

                        Log.w("Merchant", value.getText().toString());
                        break;
                    case TOGGLE:
                        Switch switch_control = (Switch) view.findViewById(R.id.toggle);

                        Log.w("Merchant", "" + switch_control.isChecked());
                        break;
                }
            }

            // ToDo: To be removed
            /* For static display purpose */
            String merchantId = sharedPreferences.getString("merchantId", "");
            Merchant merchant = Constants.merchantManager.getMerchant(merchantId);

            Bitmap coverImage_four_chicken = BitmapFactory.decodeResource(getActivity().getApplicationContext()
                            .getResources(), R.drawable.coverfourseasonschicken);

            PercentageDiscount additionalDeal = new PercentageDiscount("GT1", coverImage_four_chicken,
                    merchant, 20);

            Constants.dealManager.addDeal(additionalDeal);
            Constants.newItemAdded = true;

            // ToDo: Testing for notification
            NotificationManager mNotification = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyId = 1;

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle("New Ding!")
                    .setContentText("You’ve got a Ding from " + merchant.getCompanyName() + "!!");

            // Create an explicit intent for an activity in the app
            Intent intent = new Intent(getActivity(), OngoingDeal.class);

            // The stack builder object will contain an artificial back stack for the started
            // Activity. This ensures that navigating backward from the Activity leads out of
            // the application to the home screen
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
            // Adds the back stack for the intetn
            stackBuilder.addParentStack(OngoingDeal.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            // mId allows to update the notification later on
            mNotification.notify(notifyId, mBuilder.build());

            startActivity(intent);
        }
    };

    DialogInterface.OnClickListener response_no = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // Nothing Happen
        }
    };
}
