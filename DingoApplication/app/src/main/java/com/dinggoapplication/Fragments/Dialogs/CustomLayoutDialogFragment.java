package com.dinggoapplication.Fragments.Dialogs;

import android.app.Activity;
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

    DialogListener mListener;

    @Override
     public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Verify that the host activity implements the callback interface
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e){
            // The activity doesn't implement the interface, hence throw exception
            throw new ClassCastException(activity.toString() + " must implement CustomLayoutDialogListener");
        }
    }

    public static CustomLayoutDialogFragment newInstance(int resId, String title, String dialog1, String dialog2,
                                                         int max, int min, int position) {
        CustomLayoutDialogFragment customLayoutDialogFragment = new CustomLayoutDialogFragment();
        Bundle args = new Bundle();

        args.putInt("resId", resId);
        args.putString("title", title);
        args.putString("dialog1", dialog1);
        args.putString("dialog2", dialog2);
        args.putInt("max", max);
        args.putInt("min", min);
        args.putInt("listPosition", position);
        customLayoutDialogFragment.setArguments(args);
        return customLayoutDialogFragment;
    }

    public static CustomLayoutDialogFragment newInstance(int resId, String title,
                                                         String dialog1, String dialog2, String dialog3,
                                                         int max1, int min1, int max2, int min2, int position) {
        CustomLayoutDialogFragment customLayoutDialogFragment = new CustomLayoutDialogFragment();
        Bundle args = new Bundle();

        args.putInt("resId", resId);
        args.putString("title", title);
        args.putString("dialog1", dialog1);
        args.putString("dialog2", dialog2);
        args.putString("dialog3", dialog3);
        args.putInt("max1", max1);
        args.putInt("min1", min1);
        args.putInt("max2", max2);
        args.putInt("min2", min2);
        args.putInt("listPosition", position);
        customLayoutDialogFragment.setArguments(args);
        return customLayoutDialogFragment;
    }

    public static CustomLayoutDialogFragment newInstance(int resId, String title, String dialog_text, int position) {
        CustomLayoutDialogFragment customLayoutDialogFragment = new CustomLayoutDialogFragment();
        Bundle args = new Bundle();

        args.putInt("resId", resId);
        args.putString("title", title);
        args.putString("time_dialog", dialog_text);
        args.putInt("listPosition", position);
        customLayoutDialogFragment.setArguments(args);
        return customLayoutDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final int resId = getArguments().getInt("resId");
        final String title = getArguments().getString("title");

        // Inflate layout for the dialog and pass null for parent view since it is a dialog
        builder.setView(inflater.inflate(resId, null))
                .setTitle(title)
                        // Add action buttons
                .setPositiveButton(R.string.response_yes, response_yes)
                .setNegativeButton(R.string.response_no, response_no);
        return builder.create();
    }

    DialogInterface.OnClickListener response_yes = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mListener.onDialogPositiveClick(CustomLayoutDialogFragment.this, getArguments().getInt("listPosition"));
        }
    };

    DialogInterface.OnClickListener response_no = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            getDialog().cancel();
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

        } else if (resId == R.layout.dialog_number_picker_2) {

            // Set the value for the number picker
            NumberPicker np1 = (NumberPicker) dialog.findViewById(R.id.numberpicker2_1);
            if (np1 != null) {
                np1.setMaxValue(getArguments().getInt("max1"));
                np1.setMinValue(getArguments().getInt("min1"));
            }

            // Set the value for the number picker 2
            NumberPicker np2 = (NumberPicker) dialog.findViewById(R.id.numberpicker2_2);
            if (np2 != null) {
                np2.setMaxValue(getArguments().getInt("max2"));
                np2.setMinValue(getArguments().getInt("min2"));
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

            TextView dialogText3 = (TextView) dialog.findViewById(R.id.dialog_text_3);
            if (dialogText3 != null) {
                dialogText3.setText(getArguments().getString("dialog3"));
            }

        } else if (resId == R.layout.dialog_time_picker) {
            TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(0);
            timePicker.setCurrentMinute(0);

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