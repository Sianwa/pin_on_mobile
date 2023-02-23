package com.interswitchgroup.pinonmobile.api.models;

public class InitializationRequestPayload {
    public InstitutionModel institution;
    public AccountModel account;

    public InitializationRequestPayload(InstitutionModel institution, AccountModel account) {
        this.institution = institution;
        this.account = account;

    }
}

