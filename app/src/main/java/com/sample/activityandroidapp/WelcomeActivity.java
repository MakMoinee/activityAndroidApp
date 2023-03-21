package com.sample.activityandroidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.databinding.ActivityWelcomeBinding;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.preference.MyPref;
import com.sample.activityandroidapp.preference.SoundPref;
import com.sample.activityandroidapp.preference.ThemePref;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    MediaPlayer mp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = new ThemePref(WelcomeActivity.this).getTheme();
        if (theme == 0) {
            new ThemePref(WelcomeActivity.this).saveThemeName(R.style.DefaultTheme);
        } else {
            switch (theme) {
                case R.style.DefaultTheme:
                    setTheme(R.style.DefaultThemeSplash);
                    break;
                case R.style.YellowTheme:
                    setTheme(R.style.YellowThemeSplash);
                    break;
                case R.style.VioletTheme:
                    setTheme(R.style.VioletThemeSplash);
                    break;
            }
        }
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setValues();
    }

    private void setValues() {
        Users users = new MyPref(WelcomeActivity.this).getUsers();
        mp = MediaPlayer.create(WelcomeActivity.this, R.raw.valorant);
        Boolean isPlay = new SoundPref(WelcomeActivity.this).isPlay();
        if (isPlay) mp.start();
        new Thread(() -> {
            while (progressStatus < 200) {
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

                if (progressStatus == 200) {
                    if (users.getUserID() == 0) {
                        mp.pause();
                        mp.stop();
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        mp.pause();
                        mp.stop();
                        Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }).start();
    }
}
