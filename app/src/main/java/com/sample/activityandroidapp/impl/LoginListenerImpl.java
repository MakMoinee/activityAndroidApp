package com.sample.activityandroidapp.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sample.activityandroidapp.DashboardActivity;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.intefaces.ServerListener;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.parsers.UserJSONParser;
import com.sample.activityandroidapp.preference.MyPref;

public class LoginListenerImpl implements ServerListener {

    Context mContext;
    LocalActivityListener listener;

    public LoginListenerImpl(Context mContext, LocalActivityListener l) {
        this.mContext = mContext;
        this.listener = l;
    }

    @Override
    public void onSuccess(String response) {
        Users users = UserJSONParser.parseUser(response);
        new MyPref(mContext).saveUsers(users);
        listener.callOnFinish();
    }

    @Override
    public void onError() {
        Toast.makeText(mContext, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
    }
}
