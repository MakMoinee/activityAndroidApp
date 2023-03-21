package com.sample.activityandroidapp.impl;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.sample.activityandroidapp.AboutAppActivity;
import com.sample.activityandroidapp.DashboardActivity;
import com.sample.activityandroidapp.ProfileActivity;
import com.sample.activityandroidapp.R;
import com.sample.activityandroidapp.databinding.DialogSettingsBinding;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.preference.SoundPref;
import com.sample.activityandroidapp.preference.ThemePref;

public class DefaultMenuOptImpl {
    Context mContext;
    DialogSettingsBinding settingsBinding;
    AlertDialog settingsDialog;
    Boolean isMusicPlay = false;

    int selectedTheme = 0;

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

    public void showAboutApp() {
        Intent intent = new Intent(mContext, AboutAppActivity.class);
        mContext.startActivity(intent);
    }

    public void showSettings(MediaPlayer mp, LocalActivityListener listener) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        settingsBinding = DialogSettingsBinding.inflate(LayoutInflater.from(mContext), null, false);
        mBuilder.setView(settingsBinding.getRoot());
        setSettingsValues();
        setSettingsListener(mp, listener);
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

        selectedTheme = new ThemePref(mContext).getTheme();

        switch (selectedTheme) {
            case R.style.DefaultTheme:
                settingsBinding.defaultTheme.setChecked(true);
                settingsBinding.yellowTheme.setChecked(false);
                settingsBinding.violetTheme.setChecked(false);
                break;
            case R.style.YellowTheme:
                settingsBinding.defaultTheme.setChecked(false);
                settingsBinding.yellowTheme.setChecked(true);
                settingsBinding.violetTheme.setChecked(false);
                break;
            case R.style.VioletTheme:
                settingsBinding.violetTheme.setChecked(true);
                settingsBinding.yellowTheme.setChecked(false);
                settingsBinding.defaultTheme.setChecked(false);
                break;
        }
    }

    private void setSettingsListener(MediaPlayer mp, LocalActivityListener listener) {

        settingsBinding.radioTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.defaultTheme:
                        selectedTheme = R.style.DefaultTheme;
                        break;
                    case R.id.yellowTheme:
                        selectedTheme = R.style.YellowTheme;
                        break;
                    case R.id.violetTheme:
                        selectedTheme = R.style.VioletTheme;
                        break;
                }
            }
        });

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
            if (isMusicPlay) {
                if (mp.isPlaying()) {
                    settingsDialog.dismiss();
                } else {
                    listener.playMedia();
                    settingsDialog.dismiss();
                }

            } else {
                if (mp.isPlaying()) {
                    mp.pause();
                }
                mp.stop();
                settingsDialog.dismiss();
                listener.stopMedia();
            }

            new ThemePref(mContext).saveThemeName(selectedTheme);

        });
        settingsBinding.btnClose.setOnClickListener(v -> settingsDialog.dismiss());
    }
}
