package com.interswitchgroup.pinonmobile.models;

import com.google.gson.annotations.SerializedName;

public class SuccessModel {

    @SerializedName("responseCode")
    public String code;
    @SerializedName("responseMessage")
    public String message;
}
