package com.sample.activityandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.common.Constants;
import com.sample.activityandroidapp.databinding.ActivityMainBinding;
import com.sample.activityandroidapp.impl.LoginListenerImpl;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.intefaces.ServerListener;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.preference.MyPref;
import com.sample.activityandroidapp.services.ServerRequests;

public class MainActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityMainBinding binding;
    ServerListener loginListener = new LoginListenerImpl(MainActivity.this, MainActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Valorant InfoSys");
        setListeners();
    }

    private void setListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String userName = binding.editUsername.getText().toString();
            String password = binding.editPassword.getText().toString();

            if (userName.equals("") || password.equals("")) {
                Toast.makeText(MainActivity.this, Constants.emptyFields, Toast.LENGTH_SHORT).show();
            } else {
                Users users = new Users.UserBuilder()
                        .setUserName(userName)
                        .setPassword(password)
                        .build();
                new ServerRequests(MainActivity.this).login(users, loginListener);
            }
        });
    }

    @Override
    public void callOnFinish(Users users) {
        new MyPref(MainActivity.this).saveUsers(users);
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
