package com.interswitchgroup.pinonmobile.api.tasks;

import android.os.AsyncTask;

import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;
import com.interswitchgroup.pinonmobile.api.services.GetSessionKey;

import java.io.IOException;

import retrofit2.Retrofit;

public class GetSessionKeyTask extends AsyncTask<String,Void,String> {
    Retrofit retrofit;

    public GetSessionKeyTask(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            GenerateSessionKeyResponse generateSessionKeyResponse = retrofit.create(GetSessionKey.class)
                    .getSessionKey()
                    .execute()
                    .body();
            return generateSessionKeyResponse.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}