package com.sample.activityandroidapp.impl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.sample.activityandroidapp.DashboardActivity;
import com.sample.activityandroidapp.ProfileActivity;
import com.sample.activityandroidapp.R;
import com.sample.activityandroidapp.databinding.DialogSettingsBinding;
import com.sample.activityandroidapp.preference.SoundPref;

public class DefaultMenuOptImpl {
    Context mContext;
    DialogSettingsBinding settingsBinding;
    AlertDialog settingsDialog;
    Boolean isMusicPlay = false;

    public DefaultMenuOptImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void showProfile() {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(intent);
    }

    public void showHome() {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        mContext.startActivity(intent);
    }

    public void showSettings() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        settingsBinding = DialogSettingsBinding.inflate(LayoutInflater.from(mContext), null, false);
        mBuilder.setView(settingsBinding.getRoot());
        setSettingsValues();
        setSettingsListener();
        settingsDialog = mBuilder.create();
        settingsDialog.show();
    }

    private void setSettingsValues() {
        isMusicPlay = new SoundPref(mContext).isPlay();
        if (isMusicPlay) {
            settingsBinding.musicOff.setChecked(false);
            settingsBinding.musicOn.setChecked(true);
        } else {
            settingsBinding.musicOff.setChecked(true);
            settingsBinding.musicOn.setChecked(false);
        }
    }

    private void setSettingsListener() {

        settingsBinding.radioMusic.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.musicOn:
                    isMusicPlay = true;
                    break;
                case R.id.musicOff:
                    isMusicPlay = false;
                    break;
            }
        });
        settingsBinding.btnSave.setOnClickListener(v -> {
            new SoundPref(mContext).saveIsPlay(isMusicPlay);
            settingsDialog.dismiss();
        });
        settingsBinding.btnClose.setOnClickListener(v -> settingsDialog.dismiss());
    }
}
