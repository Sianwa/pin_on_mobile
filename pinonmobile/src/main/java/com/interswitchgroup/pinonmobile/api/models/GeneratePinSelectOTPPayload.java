package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;

public class GeneratePinSelectOTPPayload {
    private String serno;
    private String pan;
    private String expiryDate;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
