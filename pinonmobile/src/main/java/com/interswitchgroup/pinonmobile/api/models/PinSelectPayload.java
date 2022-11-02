package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;

public class PinSelectPayload {
    private String pinBlock;
    private String serno;
    private String otp;
    private String pan;
    private int institutionID;
    private String expiryDate;
    private String zpk;

    public PinSelectPayload(String pinBlock, String serno, String otp, String pan) {
        this.pinBlock = pinBlock;
        this.serno = serno;
        this.otp = otp;
        this.pan = pan;
    }

    public PinSelectPayload(String pinBlock, String cardSerialNumber, String otp, String accountNumber, Integer institutionId, String zpk, String expiryDate) {
        this.pinBlock = pinBlock;
        this.serno = cardSerialNumber;
        this.otp = otp;
        this.pan = accountNumber;
        this.institutionID = institutionId;
        this.zpk = zpk;
        this.expiryDate = expiryDate;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
