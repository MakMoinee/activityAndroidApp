package com.sample.activityandroidapp.impl;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.preference.MyPref;


public class LogoutImpl {

    Context mContext;
    LocalActivityListener listener;

    public LogoutImpl(Context mContext, LocalActivityListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void showLogout() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        DialogInterface.OnClickListener dListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    new MyPref(mContext).saveUsers(new Users());
                    dialog.dismiss();
                    listener.onLogOut();
                    break;
                default:
                    dialog.dismiss();
                    break;
            }
        };

        mBuilder.setMessage("Are You Sure You Want To Logout?")
                .setNegativeButton("Yes", dListener)
                .setPositiveButton("No", dListener)
                .setCancelable(false)
                .show();
    }
}
