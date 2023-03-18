package com.sample.activityandroidapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.sample.activityandroidapp.models.Users;

public class MyPref {
    Context context;
    SharedPreferences pref;

    public MyPref(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences("users", Context.MODE_PRIVATE);
    }

    public void saveUsers(Users users) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userID", users.getUserID());
        editor.putString("userName", users.getUserName());
        editor.putString("password", users.getPassword());
        editor.commit();
        editor.apply();
    }

    public Users getUsers() {
        Users users = new Users.UserBuilder()
                .setUserID(pref.getInt("userID", 0))
                .setUserName(pref.getString("userName", ""))
                .setPassword(pref.getString("password", ""))
                .build();

        return users;
    }
}
