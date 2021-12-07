package com.interswitchgroup.pinonmobile.api.services;

import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetSessionKey {
    @GET("keys/session")
    Call<GenerateSessionKeyResponse> getSessionKey();
}
