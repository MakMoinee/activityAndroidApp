package com.sample.activityandroidapp.models;

public class Weapons {
    int weaponID;
    String displayName;
    Double fireRate;
    int magazinSize;
    String category;
    String displayIcon;
    String uuid;

    int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Weapons() {
    }

    public int getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(int weaponID) {
        this.weaponID = weaponID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



    public int getMagazinSize() {
        return magazinSize;
    }

    public void setMagazinSize(int magazinSize) {
        this.magazinSize = magazinSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getFireRate() {
        return fireRate;
    }

    public void setFireRate(Double fireRate) {
        this.fireRate = fireRate;
    }
}
