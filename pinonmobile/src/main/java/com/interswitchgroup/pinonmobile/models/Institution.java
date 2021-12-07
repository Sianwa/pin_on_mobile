package com.interswitchgroup.pinonmobile.models;

public class Institution {
    private String clientId;
    private String clientSecret;
    private Integer institutionId;
    private String rsaPublicKey;
    private String rsaPrivateKey;
    private String keyId;
    public Institution(String clientId, String clientSecret, Integer institutionId, String rsaPublicKey, String rsaPrivateKey,String keyId) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.institutionId = institutionId;
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
        this.keyId = keyId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
