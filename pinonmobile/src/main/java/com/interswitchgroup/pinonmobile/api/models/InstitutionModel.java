package com.interswitchgroup.pinonmobile.api.models;

public class InstitutionModel{
    public String callbackUrl;
    public int id;

    public InstitutionModel(String callbackUrl, int id) {
        this.callbackUrl = callbackUrl;
        this.id = id;
    }
}
