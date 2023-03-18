package com.sample.activityandroidapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SoundPref {

    Context mContext;
    SharedPreferences pref;

    public SoundPref(Context mContext) {
        this.mContext = mContext;
        this.pref = mContext.getSharedPreferences("sounds", Context.MODE_PRIVATE);
    }

    public void saveIsPlay(Boolean isPlay) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isPlay", isPlay);
        editor.commit();
        editor.apply();
    }

    public Boolean isPlay() {
        return pref.getBoolean("isPlay", true);
    }
}
