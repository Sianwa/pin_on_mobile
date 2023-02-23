package com.interswitchgroup.pinonmobile.api.models;

public class AccountModel{
    public String accountNumber;
    public String cardSerialNumber;
    public String expiryDate;
    public String cvv;

    public AccountModel(String accountNumber, String cardSerialNumber, String expiryDate, String cvv) {
        this.accountNumber = accountNumber;
        this.cardSerialNumber = cardSerialNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}