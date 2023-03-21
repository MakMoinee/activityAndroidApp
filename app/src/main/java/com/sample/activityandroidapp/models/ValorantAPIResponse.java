package com.sample.activityandroidapp.models;

import java.util.List;

public class ValorantAPIResponse {
    int status;
    List<ValorantWeapons> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ValorantWeapons> getData() {
        return data;
    }

    public void setData(List<ValorantWeapons> data) {
        this.data = data;
    }
}
