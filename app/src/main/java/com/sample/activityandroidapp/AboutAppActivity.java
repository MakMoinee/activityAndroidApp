package com.sample.activityandroidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.databinding.ActivityAboutBinding;
import com.sample.activityandroidapp.impl.DefaultMenuOptImpl;
import com.sample.activityandroidapp.impl.LogoutImpl;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.preference.SoundPref;
import com.sample.activityandroidapp.preference.ThemePref;

public class AboutAppActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityAboutBinding binding;
    MediaPlayer mp;

    LogoutImpl logout = new LogoutImpl(AboutAppActivity.this, AboutAppActivity.this);
    DefaultMenuOptImpl defaultMenuOpt = new DefaultMenuOptImpl(AboutAppActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = new ThemePref(AboutAppActivity.this).getTheme();
        if (theme == 0) {
            new ThemePref(AboutAppActivity.this).saveThemeName(R.style.DefaultTheme);
        } else {
            setTheme(theme);
        }
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("About App");
        mp = MediaPlayer.create(AboutAppActivity.this, R.raw.valorant);
        Boolean isPlay = new SoundPref(AboutAppActivity.this).isPlay();
        if (isPlay) {
            mp.setLooping(true);
            mp.start();
        }
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
            case R.id.action_profile:
                defaultMenuOpt.showProfile();
                finish();
                return true;
            case R.id.action_settings:
                defaultMenuOpt.showSettings(mp,AboutAppActivity.this);
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
        Intent intent = new Intent(AboutAppActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isPlay = new SoundPref(AboutAppActivity.this).isPlay();
        if (mp.isPlaying()) mp.pause();
        if (isPlay) {
            mp.setLooping(true);
            mp.start();
        }
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
