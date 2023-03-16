package com.sample.activityandroidapp.impl;

import android.content.Context;
import android.content.Intent;

import com.sample.activityandroidapp.DashboardActivity;
import com.sample.activityandroidapp.ProfileActivity;

public class DefaultMenuOptImpl {
    Context mContext;

    public DefaultMenuOptImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void showProfile(){
        Intent intent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(intent);
    }

    public void showHome(){
        Intent intent = new Intent(mContext, DashboardActivity.class);
        mContext.startActivity(intent);
    }
}
