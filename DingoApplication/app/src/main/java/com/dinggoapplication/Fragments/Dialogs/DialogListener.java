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
