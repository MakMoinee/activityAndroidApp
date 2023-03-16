package com.sample.activityandroidapp.intefaces;

public interface ServerListener {

    default void onSuccess(String response) {

    }

    void onError();
}
