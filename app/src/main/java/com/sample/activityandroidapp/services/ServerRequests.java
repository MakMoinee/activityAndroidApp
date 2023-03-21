package com.sample.activityandroidapp.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import java.util.HashMap;
import java.util.Map;

public class ServerRequests {

    Context mContext;

    public ServerRequests(Context mContext) {
        this.mContext = mContext;
    }

    public void login(Users users, ServerListener listener) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.loginURL, response -> listener.onSuccess(response), error -> listener.onError()) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("username",users.getUserName());
//                params.put("password",users.getPassword());
//                return params;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "multipart/form-data";
//            }
//        };


        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.loginURL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                listener.onSuccess(resultResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", users.getUserName());
                params.put("password", users.getPassword());
                return params;
            }


        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(multipartRequest);
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        queue.add(stringRequest);
    }


    public void getAllWeapons(ServerListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://valorant-api.com/v1/weapons", response -> listener.onSuccess(response), error -> {
            Log.e("ERROR_GET_ALL_WEAPONS", error.getMessage());
            listener.onError();
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }


    public void getAllUsers(ServerListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.usersURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, error -> listener.onError());
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    public void deleteUser(String id, ServerListener listener) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.usersURL, response -> {
            String resultResponse = new String(response.data);
            listener.onSuccess(resultResponse);
        }, error -> listener.onError()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "delete");
                params.put("id", id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(volleyMultipartRequest);
    }

    public void editUser(Users users,ServerListener listener){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.usersURL, response -> {
            String resultResponse = new String(response.data);
            listener.onSuccess(resultResponse);
        }, error -> listener.onError()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update");
                params.put("userID", Integer.toString(users.getUserID()));
                params.put("username",users.getUserName());
                params.put("password",users.getPassword());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(volleyMultipartRequest);
    }

}
