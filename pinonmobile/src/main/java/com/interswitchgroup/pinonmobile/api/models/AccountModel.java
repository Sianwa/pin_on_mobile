package com.interswitchgroup.pinonmobile.api.models;

public class AccountModel{
    public String cardSerialNumber;
    public Boolean isDebit;

    public AccountModel(String cardSerialNumber, Boolean isDebit) {
        this.cardSerialNumber = cardSerialNumber;
        this.isDebit = isDebit;
    }
}