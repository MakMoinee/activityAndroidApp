package com.sample.activityandroidapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sample.activityandroidapp.adapters.WeaponsAdapter;
import com.sample.activityandroidapp.databinding.FragmentWeaponsBinding;
import com.sample.activityandroidapp.intefaces.AdapterListener;
import com.sample.activityandroidapp.models.Weapons;

import java.util.List;

public class WeaponsFragment extends Fragment {

    FragmentWeaponsBinding binding;

    Context mContext;
    List<Weapons> weaponsList;
    WeaponsAdapter adapter;

    public WeaponsFragment(Context mContext, List<Weapons> weaponsList) {
        this.mContext = mContext;
        this.weaponsList = weaponsList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeaponsBinding.inflate(LayoutInflater.from(mContext), container, false);
        loadData();
        return binding.getRoot();
    }

    private void loadData() {
        adapter = new WeaponsAdapter(mContext, weaponsList, new AdapterListener() {
            @Override
            public void onClick() {
                AdapterListener.super.onClick();
            }
        });

        binding.recycler.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recycler.setAdapter(adapter);
    }
}
