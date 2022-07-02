package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;

public class PinSelectPayload {
    private String pinBlock;
    private String serno;
    private String otp;

    public PinSelectPayload(String pinBlock, String serno, String otp) {
        this.pinBlock = pinBlock;
        this.serno = serno;
        this.otp = otp;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
