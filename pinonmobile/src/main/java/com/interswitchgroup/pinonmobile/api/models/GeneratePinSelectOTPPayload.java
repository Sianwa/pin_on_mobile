package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;

public class GeneratePinSelectOTPPayload {
    private String serno;

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
