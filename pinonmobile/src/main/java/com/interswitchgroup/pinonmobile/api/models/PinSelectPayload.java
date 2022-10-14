package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;

public class PinSelectPayload {
    private String pinBlock;
    private String serno;
    private String otp;
    private String pan;

    public PinSelectPayload(String pinBlock, String serno, String otp, String pan) {
        this.pinBlock = pinBlock;
        this.serno = serno;
        this.otp = otp;
        this.pan = pan;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
