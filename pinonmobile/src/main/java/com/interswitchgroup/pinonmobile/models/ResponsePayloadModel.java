package com.interswitchgroup.pinonmobile.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ResponsePayloadModel {

    @SerializedName("responseCode")
    public String code;
    @SerializedName("responseMessage")
    public String message;
    @SerializedName("id")
    public int respId;
    @SerializedName("instid")
    public int instId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
