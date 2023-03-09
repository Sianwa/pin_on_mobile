package com.interswitchgroup.pinonmobile.api.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.interswitchgroup.pinonmobile.api.models.InitializationRequestPayload;
import com.interswitchgroup.pinonmobile.api.models.InitializationResponseModel;
import com.interswitchgroup.pinonmobile.api.services.Initialize;
import com.interswitchgroup.pinonmobile.models.ResponsePayloadModel;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class InitializeService extends AsyncTask<String, Void, InitializationResponseModel> {
    InitializationRequestPayload requestPayload;
    Retrofit retrofit;

    public InitializeService(InitializationRequestPayload requestPayload, Retrofit retrofit) {
        this.requestPayload = requestPayload;
        this.retrofit = retrofit;
    }

    @Override
    protected InitializationResponseModel doInBackground(String... strings) {
       try{
           Response<InitializationResponseModel> response = retrofit.create(Initialize.class)
                   .initializeService(requestPayload)
                   .execute();

           if(response.body() != null){
               InitializationResponseModel responseModel = response.body();
               return responseModel;
           }
           else{
               String errorBody = response.errorBody().string();
               Gson gson = new Gson();
               InitializationResponseModel errorRespModel = gson.fromJson(errorBody, InitializationResponseModel.class);
               return errorRespModel;
           }
       }
       catch (Exception e){
           System.out.println("EXCEPTION OCCURRED::"+e.getMessage());
           e.printStackTrace();
           return null;
        }
    }
}
