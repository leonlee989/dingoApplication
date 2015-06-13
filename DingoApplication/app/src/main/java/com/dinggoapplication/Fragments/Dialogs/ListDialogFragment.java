/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Fragments.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dinggoapplication.R;

import java.util.ArrayList;

/**
 * Created by Leon on 1/3/2015.
 */
public class ListDialogFragment extends DialogFragment {

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

    public static ListDialogFragment newInstance(String title, CharSequence[] valueList, int position) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putCharSequenceArray("values", valueList);
        args.putInt("position", position);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String title = getArguments().getString("title");
        final CharSequence[] valueList = getArguments().getCharSequenceArray("values");

        builder.setTitle(title)
                .setItems(valueList, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getArguments().putCharSequence("selection", valueList[which]);
                        mListener.onDialogPositiveClick(ListDialogFragment.this, getArguments().getInt("position"));
                    }
                });

        return builder.create();
    }

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
    }
}
