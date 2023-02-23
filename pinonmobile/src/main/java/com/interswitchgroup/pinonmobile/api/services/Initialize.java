package com.interswitchgroup.pinonmobile.api.services;

import com.interswitchgroup.pinonmobile.api.models.InitializationRequestPayload;
import com.interswitchgroup.pinonmobile.api.models.InitializationResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Initialize {
    @POST("web/initialize")
    Call<InitializationResponseModel> initializeService(@Body InitializationRequestPayload initializationRequestPayload);
}
