package com.sample.activityandroidapp.parser;

import android.util.Log;

import com.sample.activityandroidapp.models.Weapons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ValorantAPIParser {
    public static List<Weapons> getWeapons(String response) {
        List<Weapons> weaponsList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray dataArr = object.getJSONArray("data");
            for (int i = 0; i < dataArr.length(); i++) {
                Weapons weapons = new Weapons();
                JSONObject w = dataArr.getJSONObject(i);
                weapons.setDisplayName(w.getString("displayName"));
                weapons.setUuid(w.getString("uuid"));
//                JSONArray weaponStats = w.getJSONArray("weaponStats");
                try {
                    JSONObject shopData = w.getJSONObject("shopData");
                    if (shopData != null) weapons.setCost(shopData.getInt("cost"));
                } catch (Exception t) {

                }
                JSONArray skins = w.getJSONArray("skins");
                for (int s = 0; s < skins.length(); s++) {
                    JSONObject skinObj = skins.getJSONObject(s);
                    weapons.setDisplayIcon(skinObj.getString("displayIcon"));
                    break;
                }

                weaponsList.add(weapons);
            }

            return weaponsList;
        } catch (JSONException e) {
            Log.e("ERROR_GETTING_WEAPONS", e.getMessage());
            return null;
        }
    }
}
