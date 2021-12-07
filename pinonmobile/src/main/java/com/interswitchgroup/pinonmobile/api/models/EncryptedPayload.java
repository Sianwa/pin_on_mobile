package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EncryptedPayload {
    @SerializedName("encData")
    @Expose
    private String encData;

    public String getEncData() {
        return encData;
    }

    public void setEncData(String encData) {
        this.encData = encData;
    }
}
