package com.interswitchgroup.pinonmobile.api.tasks;

import android.os.AsyncTask;

import com.interswitchgroup.pinonmobile.api.models.GenerateMLEKeyResponse;
import com.interswitchgroup.pinonmobile.api.services.GetMLEKey;

import java.io.IOException;
import retrofit2.Retrofit;

public class GetMLETask extends AsyncTask<String, Void, String> {
    Retrofit retrofit;

    public GetMLETask(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            GenerateMLEKeyResponse generateMLEKeyResponse = retrofit.create(GetMLEKey.class)
                    .getMLEKey()
                    .execute()
                    .body();
            if (generateMLEKeyResponse == null) return null;
            String key = generateMLEKeyResponse.getItem().getKey();
            return key;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}