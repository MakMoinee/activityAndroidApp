package com.sample.activityandroidapp.models;

import java.util.List;

public class ValorantWeapons {
    String displayName;
    String uuid;
    String category;
    ValorantWeaponStats weaponStats;

    List<ValorantSkin> skins;

    ValorantShopData shopData;

    public ValorantWeapons() {
    }

    public List<ValorantSkin> getSkins() {
        return skins;
    }

    public void setSkins(List<ValorantSkin> skins) {
        this.skins = skins;
    }

    public ValorantShopData getShopData() {
        return shopData;
    }

    public void setShopData(ValorantShopData shopData) {
        this.shopData = shopData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ValorantWeaponStats getWeaponStats() {
        return weaponStats;
    }

    public void setWeaponStats(ValorantWeaponStats weaponStats) {
        this.weaponStats = weaponStats;
    }
}
