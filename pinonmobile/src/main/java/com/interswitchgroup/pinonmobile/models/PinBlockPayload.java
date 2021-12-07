package com.interswitchgroup.pinonmobile.models;

import com.google.gson.Gson;

public class PinBlockPayload {
    private Integer instId;
    private String serno;
    private String pinBlock;

    public PinBlockPayload(Integer instId, String serno, String pinBlock) {
        this.instId = instId;
        this.serno = serno;
        this.pinBlock = pinBlock;
    }

    //return as json string

    public String getPinBlockPayloadString(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return  json;
    }
}
