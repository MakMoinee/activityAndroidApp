package com.sample.activityandroidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.activityandroidapp.databinding.ActivityDashboardBinding;
import com.sample.activityandroidapp.fragment.UsersFragment;
import com.sample.activityandroidapp.fragment.WeaponsFragment;
import com.sample.activityandroidapp.impl.DefaultMenuOptImpl;
import com.sample.activityandroidapp.impl.LogoutImpl;
import com.sample.activityandroidapp.intefaces.LocalActivityListener;
import com.sample.activityandroidapp.intefaces.ServerListener;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.models.ValorantAPIResponse;
import com.sample.activityandroidapp.models.ValorantSkin;
import com.sample.activityandroidapp.models.ValorantWeaponStats;
import com.sample.activityandroidapp.models.ValorantWeapons;
import com.sample.activityandroidapp.models.Weapons;
import com.sample.activityandroidapp.parser.ValorantAPIParser;
import com.sample.activityandroidapp.preference.SoundPref;
import com.sample.activityandroidapp.preference.ThemePref;
import com.sample.activityandroidapp.services.ServerRequests;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements LocalActivityListener {

    ActivityDashboardBinding binding;
    LogoutImpl logout = new LogoutImpl(DashboardActivity.this, DashboardActivity.this);
    DefaultMenuOptImpl defaultMenuOpt = new DefaultMenuOptImpl(DashboardActivity.this);
    MediaPlayer mp;
    ServerRequests request;
    Fragment fragment;
    FragmentTransaction ft;
    FragmentManager fm;
    List<Weapons> weaponsList = new ArrayList<>();
    List<Users> usersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = new ThemePref(DashboardActivity.this).getTheme();
        if (theme == 0) {
            new ThemePref(DashboardActivity.this).saveThemeName(R.style.DefaultTheme);
        } else {
            setTheme(theme);
        }
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mp = MediaPlayer.create(DashboardActivity.this, R.raw.valorant);
        Boolean isPlay = new SoundPref(DashboardActivity.this).isPlay();
        if (isPlay) {
            mp.setLooping(true);
            mp.start();
        }
        request = new ServerRequests(DashboardActivity.this);
        setTitle("Home");
        loadData();
        setListener();
    }

    private void setListener() {
        binding.btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_weapons:
                        fragment = new WeaponsFragment(DashboardActivity.this, weaponsList);
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frame, fragment, null);
                        ft.commit();
                        return true;
                    case R.id.action_users:
                        fragment = new UsersFragment(DashboardActivity.this, usersList);
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frame, fragment, null);
                        ft.commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void loadData() {
        request.getAllWeapons(new ServerListener() {
            @Override
            public void onSuccess(String response) {
                try {

                    weaponsList = ValorantAPIParser.getWeapons(response);
//                    ValorantAPIResponse apiResponse = new Gson().fromJson(response, new TypeToken<ValorantAPIResponse>() {
//                    }.getType());

//                    if (apiResponse.getStatus() == 200) {
//                        List<Weapons> weaponsList = new ArrayList<>();
//                        Weapons weapons = new Weapons();
//                        for (ValorantWeapons w : apiResponse.getData()) {
//                            weapons.setDisplayName(w.getDisplayName());
//                            weapons.setUuid(w.getUuid());
//                            weapons.setCategory(w.getCategory());
//                            if(w.getWeaponStats().getFireRate()!=null)  weapons.setFireRate(w.getWeaponStats().getFireRate());
//                            weapons.setMagazinSize(w.getWeaponStats().getMagazinSize());
//                            weapons.setCost(w.getShopData().getCost());
//                            weaponsList.add(weapons);
//                            for (ValorantSkin skin : w.getSkins()) {
//                                weapons.setDisplayIcon(skin.getDisplayIcon());
//                                break;
//                            }
//
//                        }

                    if (weaponsList.size() > 0) {
                        fragment = new WeaponsFragment(DashboardActivity.this, weaponsList);
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frame, fragment, null);
                        ft.commit();
                    }

                } catch (Exception e) {
                    Log.e("ERROR_PARSING_API_RESPONSE", e.getMessage());
                }
            }

            @Override
            public void onError() {

            }
        });
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
                if (mp.isPlaying()) mp.stop();
                finish();
                return true;
            case R.id.action_settings:
                defaultMenuOpt.showSettings(mp, DashboardActivity.this);
                return true;
            case R.id.action_logout:
                logout.showLogout();
                return true;
            case R.id.action_about:
                defaultMenuOpt.showAboutApp();
                if (mp.isPlaying()) mp.stop();
                finish();
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
        if (mp.isPlaying()) mp.stop();
        Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isPlay = new SoundPref(DashboardActivity.this).isPlay();
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
