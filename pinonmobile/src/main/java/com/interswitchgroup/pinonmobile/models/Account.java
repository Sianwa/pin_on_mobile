package com.interswitchgroup.pinonmobile.models;

import java.io.Serializable;

public class Account implements Serializable {

    private String accountNumber;
    private String cardSerialNumber;
    private String expiryDate;


    public Account(String accountNumber, String cardSerialNumber) {
        this.accountNumber = accountNumber;
        this.cardSerialNumber = cardSerialNumber;
    }

    public Account(String accountNumber, String cardSerialNumber, String expiryDate) {
        this.accountNumber = accountNumber;
        this.cardSerialNumber = cardSerialNumber;
        this.expiryDate = expiryDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardSerialNumber() {
        return cardSerialNumber;
    }

    public void setCardSerialNumber(String cardSerialNumber) {
        this.cardSerialNumber = cardSerialNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
