package com.interswitchgroup.pinonmobile.models;

import java.io.Serializable;

public class Account implements Serializable {

    private String accountNumber;
    private String cardSerialNumber;

    public Account(String accountNumber, String cardSerialNumber) {
        this.accountNumber = accountNumber;
        this.cardSerialNumber = cardSerialNumber;
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
}
