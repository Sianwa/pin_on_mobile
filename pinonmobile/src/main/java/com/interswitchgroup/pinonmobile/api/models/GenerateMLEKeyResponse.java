package com.interswitchgroup.pinonmobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
@Generated("jsonschema2pojo")
public class GenerateMLEKeyResponse {

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

        @SerializedName("key")
        @Expose
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
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
}
