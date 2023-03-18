package com.sample.activityandroidapp;

import android.content.Intent;
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

public class ProfileActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityProfileBinding binding;
    DefaultMenuOptImpl defaultMenuOpt = new DefaultMenuOptImpl(ProfileActivity.this);
    LogoutImpl logout = new LogoutImpl(ProfileActivity.this, ProfileActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
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
                defaultMenuOpt.showHome();
                finish();
                return true;
            case R.id.action_settings:
                defaultMenuOpt.showSettings();
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
}
