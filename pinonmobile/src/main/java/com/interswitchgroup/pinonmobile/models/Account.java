package com.interswitchgroup.pinonmobile.models;

import java.io.Serializable;

public class Account implements Serializable {

    private String accountNumber;
    private String cardSerialNumber;
    private String expiryDate;
    private Boolean isDebit;

    public Account(Boolean isDebit, String cardSerialNumber) {
        this.isDebit = isDebit;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(Boolean debit) {
        isDebit = debit;
    }
}
