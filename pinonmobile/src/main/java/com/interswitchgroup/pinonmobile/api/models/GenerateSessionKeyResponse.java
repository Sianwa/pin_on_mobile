package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class GenerateSessionKeyResponse {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("item")
    @Expose
    private Item item;

    @Generated("jsonschema2pojo")
    public class Item {

        @SerializedName("entity")
        @Expose
        private String key;
        @SerializedName("checkSum")
        @Expose
        private String checkSum;
        @SerializedName("keyID")
        @Expose
        private String keyID;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCheckSum() {
            return checkSum;
        }

        public void setCheckSum(String checkSum) {
            this.checkSum = checkSum;
        }

        public String getKeyID() {
            return keyID;
        }

        public void setKeyID(String keyID) {
            this.keyID = keyID;
        }

    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }


}
