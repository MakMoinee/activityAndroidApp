package com.sample.activityandroidapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePref {

    Context context;
    SharedPreferences pref;

    public ThemePref(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences("themes", Context.MODE_PRIVATE);
    }

    public void saveThemeName(int name){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("name", name);
        editor.commit();
        editor.apply();
    }

    public int getTheme(){
        return pref.getInt("name",0);
    }
}
