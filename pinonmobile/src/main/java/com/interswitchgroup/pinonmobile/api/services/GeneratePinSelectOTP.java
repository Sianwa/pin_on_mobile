package com.interswitchgroup.pinonmobile.api.services;

import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GeneratePinSelectOTP {
    @POST("cards/pin/select/otp")
    Call<EncryptedPayload> generatePinSelectOTP(@Body EncryptedPayload encryptedPayload
            , @Header("keyId") String keyId);

    @POST("cards/pin/select")
    Call<EncryptedPayload> generatePinSelect(@Body EncryptedPayload encryptedPayload
    , @Header("keyId") String keyId , @Header("sessionKeyId") String sessionKeyId);
}
