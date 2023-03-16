package com.sample.activityandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.databinding.ActivityWelcomeBinding;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.preference.MyPref;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setValues();
    }

    private void setValues() {
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 20;
                // Update the progress bar and display the
                //current value in the text view
                handler.post(new Runnable() {
                    public void run() {
                        binding.progress.setProgress(progressStatus);
                    }
                });
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("WELCOME_ACTIVITY_ERR", e.getMessage());
                }

                if (progressStatus == 100) {
                    Users users = new MyPref(WelcomeActivity.this).getUsers();
                    if (users.getUserID() == 0) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(WelcomeActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }).start();
    }
}
