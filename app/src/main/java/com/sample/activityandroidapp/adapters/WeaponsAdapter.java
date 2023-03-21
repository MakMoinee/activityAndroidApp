package com.sample.activityandroidapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.activityandroidapp.R;
import com.sample.activityandroidapp.intefaces.AdapterListener;
import com.sample.activityandroidapp.models.Weapons;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeaponsAdapter extends RecyclerView.Adapter<WeaponsAdapter.ViewHolder> {

    Context mContext;
    List<Weapons> weaponsList;
    AdapterListener listener;

    public WeaponsAdapter(Context mContext, List<Weapons> weaponsList, AdapterListener listener) {
        this.mContext = mContext;
        this.weaponsList = weaponsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeaponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_weapon, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeaponsAdapter.ViewHolder holder, int position) {
        Weapons weapons = weaponsList.get(position);
        Uri uri = Uri.parse(weapons.getDisplayIcon());
        Picasso.get().invalidate(uri);
        Picasso.get().load(uri).into(holder.imgWeapon, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                holder.imgWeapon.setImageResource(R.drawable.gun);
            }
        });

        holder.txtWeaponName.setText(weapons.getDisplayName());
        holder.txtCost.setText(String.format("Cost: %s", weapons.getCost()));

        holder.itemView.setOnClickListener(v -> listener.onClick());
    }

    @Override
    public int getItemCount() {
        return weaponsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgWeapon;
        TextView txtWeaponName, txtCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWeapon = itemView.findViewById(R.id.imgWeapon);
            txtWeaponName = itemView.findViewById(R.id.txtWeaponName);
            txtCost = itemView.findViewById(R.id.txtCost);
        }
    }
}
