/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication.Fragments.Dialogs;

import android.app.DialogFragment;

/**
 * Activity that creates an instance of this dialog fragment must implement this interface
 * in order to receive events callback.
 * Created by Leon on 8/3/2015.
 */
public interface DialogListener {
    public void onDialogPositiveClick(DialogFragment dialog, int position);
    public void onDialogNegativeClick(DialogFragment dialog, int position);
}
