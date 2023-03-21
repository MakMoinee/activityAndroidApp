package com.sample.activityandroidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.activityandroidapp.R;
import com.sample.activityandroidapp.intefaces.AdapterListener;
import com.sample.activityandroidapp.models.Users;

import java.util.List;

public class UsersAdapter extends BaseAdapter {

    Context mContext;
    List<Users> usersList;

    public UsersAdapter(Context mContext, List<Users> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_users, parent, false);
        TextView txtUser = mView.findViewById(R.id.txtUser);
        Users users = usersList.get(position);
        txtUser.setText(users.getUserName());
        return mView;
    }
}
