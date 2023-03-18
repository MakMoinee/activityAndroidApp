package com.sample.activityandroidapp.parsers;

import com.sample.activityandroidapp.models.Users;

import org.json.JSONException;
import org.json.JSONObject;

public class UserJSONParser {
    public static Users parseUser(String response) {

        try {
            JSONObject userObject = new JSONObject(response);
            Users users = new Users.UserBuilder()
                    .setUserID(userObject.getInt("userID"))
                    .setUserName(userObject.getString("username"))
                    .setPassword(userObject.getString("password"))
                    .build();
            return users;
        } catch (JSONException e) {
            return null;
        }
    }
}
