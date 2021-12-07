package com.interswitchgroup.pinonmobile.api.services;

import com.interswitchgroup.pinonmobile.api.models.GenerateMLEKeyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMLEKey {
    @GET("keys/mle")
    Call<GenerateMLEKeyResponse> getMLEKey();

}
