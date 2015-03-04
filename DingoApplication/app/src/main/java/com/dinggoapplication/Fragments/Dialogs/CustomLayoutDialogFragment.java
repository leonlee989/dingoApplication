package com.dinggoapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dinggoapplication.R;

/**
 * Dialog fragment that accept custom layout for dialogs
 * Created by Leon on 2/3/2015.
 */
public class CustomLayoutDialogFragment extends DialogFragment {

    public static CustomLayoutDialogFragment newInstance(int resId, String dialog1, String dialog2,
                                                         int max, int min) {
        CustomLayoutDialogFragment customLayoutDialogFragment = new CustomLayoutDialogFragment();
        Bundle args = new Bundle();

        args.putInt("resId", resId);
        args.putString("dialog1", dialog1);
        args.putString("dialog2", dialog2);
        args.putInt("max", max);
        args.putInt("min", min);
        customLayoutDialogFragment.setArguments(args);
        return customLayoutDialogFragment;
    }

    public static CustomLayoutDialogFragment newInstance(int resId, String dialog_text) {
        CustomLayoutDialogFragment customLayoutDialogFragment = new CustomLayoutDialogFragment();
        Bundle args = new Bundle();

        args.putInt("resId", resId);
        args.putString("time_dialog", dialog_text);
        customLayoutDialogFragment.setArguments(args);
        return customLayoutDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final int resId = getArguments().getInt("resId");

        if (resId == R.layout.dialog_number_picker) {

            // Inflate layout for the dialog and pass null for parent view since it is a dialog
            builder.setView(inflater.inflate(resId, null))
                    .setTitle("Select a value")
                            // Add action buttons
                    .setPositiveButton(R.string.response_yes, response_yes)
                    .setNegativeButton(R.string.response_no, response_no);
        } else if (resId == R.layout.dialog_time_picker) {

            // Inflate layout for the time dialog and pass null for parent view since it is a dialog
            builder.setView(inflater.inflate(resId, null))
                    .setTitle("Select a time")
                    .setPositiveButton(R.string.response_yes, response_yes)
                    .setNegativeButton(R.string.response_no, response_no);
        }

        return builder.create();
    }

    DialogInterface.OnClickListener response_yes = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();

        }
    };

    DialogInterface.OnClickListener response_no = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        final int resId = getArguments().getInt("resId");

        if (resId == R.layout.dialog_number_picker) {

            // Set the value for the number picker
            NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberpicker);
            if (np != null) {
                np.setMaxValue(getArguments().getInt("max"));
                np.setMinValue(getArguments().getInt("min"));
            }

            // Set the dialog text
            TextView dialogText1 = (TextView) dialog.findViewById(R.id.dialog_text_1);
            if (dialogText1 != null) {
                dialogText1.setText(getArguments().getString("dialog1"));
            }

            TextView dialogText2 = (TextView) dialog.findViewById(R.id.dialog_text_2);
            if (dialogText2 != null) {
                dialogText2.setText(getArguments().getString("dialog2"));
            }
        } else if (resId == R.layout.dialog_time_picker) {
            TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
            timePicker.setIs24HourView(true);

            TextView time_dialog = (TextView) dialog.findViewById(R.id.time_dialog);
            time_dialog.setText(getArguments().getString("time_dialog"));
        }

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
    }
}
