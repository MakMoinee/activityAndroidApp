package com.sample.activityandroidapp.intefaces;

import com.sample.activityandroidapp.models.Users;

public interface LocalActivityListener {
    default void callOnFinish(Users users){

    }

    default void onLogOut() {

    }
}
