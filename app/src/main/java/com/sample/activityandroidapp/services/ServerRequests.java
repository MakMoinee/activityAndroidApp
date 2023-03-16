package com.sample.activityandroidapp.services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sample.activityandroidapp.common.Constants;
import com.sample.activityandroidapp.intefaces.ServerListener;
import com.sample.activityandroidapp.models.Users;

public class ServerRequests {

    Context mContext;

    public ServerRequests(Context mContext) {
        this.mContext = mContext;
    }

    public void login(Users users, ServerListener listener) {
        String request = new Gson().toJson(users);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.loginURL, response -> listener.onSuccess(response), error -> listener.onError()) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return request.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

}
