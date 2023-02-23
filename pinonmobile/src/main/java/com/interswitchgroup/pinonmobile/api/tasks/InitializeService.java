package com.interswitchgroup.pinonmobile.api.tasks;

import android.os.AsyncTask;

import com.interswitchgroup.pinonmobile.api.models.InitializationRequestPayload;
import com.interswitchgroup.pinonmobile.api.models.InitializationResponseModel;
import com.interswitchgroup.pinonmobile.api.services.Initialize;

import java.io.IOException;

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
           InitializationResponseModel responseModel = retrofit.create(Initialize.class)
                   .initializeService(requestPayload)
                   .execute()
                   .body();

           if(responseModel == null){
               return null;
           }
           String callbackUrl = responseModel.callbackUrl;
           return responseModel;
       }catch (IOException e){
           e.printStackTrace();
           return null;
        }
    }
}
