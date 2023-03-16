package com.sample.activityandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.activityandroidapp.databinding.ActivityDashboardBinding;
import com.sample.activityandroidapp.impl.DefaultMenuOptImpl;
import com.sample.activityandroidapp.impl.LogoutImpl;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;

public class DashboardActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityDashboardBinding binding;
    LogoutImpl logout = new LogoutImpl(DashboardActivity.this, DashboardActivity.this);
    DefaultMenuOptImpl defaultMenuOpt = new DefaultMenuOptImpl(DashboardActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Home");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                defaultMenuOpt.showProfile();
                finish();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                logout.showLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onLogOut() {
        Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
