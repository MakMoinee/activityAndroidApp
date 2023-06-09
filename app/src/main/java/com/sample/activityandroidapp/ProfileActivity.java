package com.sample.activityandroidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.databinding.ActivityProfileBinding;
import com.sample.activityandroidapp.impl.DefaultMenuOptImpl;
import com.sample.activityandroidapp.impl.LogoutImpl;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.preference.SoundPref;
import com.sample.activityandroidapp.preference.ThemePref;

public class ProfileActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityProfileBinding binding;
    DefaultMenuOptImpl defaultMenuOpt = new DefaultMenuOptImpl(ProfileActivity.this);
    LogoutImpl logout = new LogoutImpl(ProfileActivity.this, ProfileActivity.this);

    MediaPlayer mp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = new ThemePref(ProfileActivity.this).getTheme();
        if (theme == 0) {
            new ThemePref(ProfileActivity.this).saveThemeName(R.style.DefaultTheme);
        } else {
            setTheme(theme);

        }
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        mp = MediaPlayer.create(ProfileActivity.this, R.raw.valorant);
        Boolean isPlay = new SoundPref(ProfileActivity.this).isPlay();
        if (isPlay) {
            mp.setLooping(true);
            mp.start();
        }

        setContentView(binding.getRoot());

        setTitle("Profile");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                if (mp.isPlaying()) mp.stop();
                defaultMenuOpt.showHome();
                finish();
                return true;
            case R.id.action_settings:
                defaultMenuOpt.showSettings(mp, ProfileActivity.this);
                return true;
            case R.id.action_about:
                defaultMenuOpt.showAboutApp();
                finish();
                return true;
            case R.id.action_logout:
                logout.showLogout();
                return true;
            case R.id.action_ext:
                finishAffinity();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onLogOut() {
        Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void stopMedia() {
        if (mp.isPlaying()) mp.pause();
        mp.stop();
    }

    @Override
    public void playMedia() {
        mp.setLooping(true);
        mp.start();
    }
}
