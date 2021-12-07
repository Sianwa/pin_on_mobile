package com.interswitchgroup.pinonmobile.api.services;

import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GeneratePinSelectOTP {
    @POST("cards/pin/select/otp")
    Single<EncryptedPayload> generatePinSelectOTP(@Body EncryptedPayload encryptedPayload
            , @Header("keyId") String keyId);
}
