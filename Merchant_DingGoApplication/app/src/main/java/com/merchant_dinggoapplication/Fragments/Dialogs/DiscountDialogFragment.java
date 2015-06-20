/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.merchant_dinggoapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.merchant_dinggoapplication.Entity.Deal.DealType;
import com.merchant_dinggoapplication.R;

import java.util.HashMap;

/**
 *
 * Created by Leon on 4/3/2015.
 */
public class DiscountDialogFragment extends DialogFragment {

    public static DiscountDialogFragment newInstance(String title, HashMap<CharSequence, DealType> valueList, int position) {
        DiscountDialogFragment discountDialogFragment = new DiscountDialogFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putSerializable("value", valueList);
        args.putInt("position", position);
        discountDialogFragment.setArguments(args);

        return discountDialogFragment;
    }

    @Override
    public void onStart()
    {
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Retrieve all information from saved bundle
        final String title = getArguments().getString("title");
        final HashMap<String, DealType> valueMap = (HashMap<String, DealType>)
                getArguments().getSerializable("value");

        final CharSequence[] valueList = new CharSequence[valueMap.size()];
        valueMap.keySet().toArray(valueList);

        // Setting alert dialog object
        builder.setTitle(title)
                .setItems(valueList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DealType dealType = valueMap.get(valueList[which]);

                        switch (dealType) {
                            case PERCENTAGE_DISCOUNT: // Percentage discount dialog box to show

                                CustomLayoutDialogFragment percentageDialog = CustomLayoutDialogFragment
                                        .newInstance(R.layout.dialog_number_picker,"Discount amount",
                                                getResources().getString(R.string.percentageGive1),
                                                getResources().getString(R.string.percentageGive2), 100, 1,
                                                getArguments().getInt("position"));

                                percentageDialog.show(getFragmentManager(), dealType.name());

                                break;
                            case TIER_DISCOUNT: // Tiered discount dialog box to show
                                CustomLayoutDialogFragment TieredDialog = CustomLayoutDialogFragment
                                        .newInstance(R.layout.dialog_number_picker_2, "Discount amount",
                                                getResources().getString(R.string.tieredGive1),
                                                getResources().getString(R.string.tieredGive2),
                                                getResources().getString(R.string.tieredGive3),
                                                100, 1, 100, 1,
                                                getArguments().getInt("position"));

                                TieredDialog.show(getFragmentManager(), dealType.name());

                                break;
                        }
                    }
                });

        return builder.create();
    }
}