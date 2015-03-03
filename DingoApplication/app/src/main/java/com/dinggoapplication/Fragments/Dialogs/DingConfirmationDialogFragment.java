package com.dinggoapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.Activities.DingADeal;
import com.dinggoapplication.R;

/**
 * Created by Leon on 1/3/2015.
 */
public class DingConfirmationDialogFragment extends DialogFragment {
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
        }
    };

    DialogInterface.OnClickListener response_no = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
        }
    };
}
