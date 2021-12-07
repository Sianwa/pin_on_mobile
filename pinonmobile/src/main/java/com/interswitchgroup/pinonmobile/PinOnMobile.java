package com.interswitchgroup.pinonmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.interswitchgroup.pinonmobile.api.models.GenerateMLEKeyResponse;
import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;
import com.interswitchgroup.pinonmobile.api.services.GetMLEKey;
import com.interswitchgroup.pinonmobile.api.services.GetSessionKey;
import com.interswitchgroup.pinonmobile.di.DaggerWrapper;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.ui.PinOnMobileActivity;

import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class PinOnMobile {
    private static PinOnMobile singletonPinOnMobileInstance;
    private Retrofit retrofit;
    private Institution institution;
    private String mleKey;
    private String sessionKey;
    private Account account;
    private PublicKey publicKey;
    private RSAPrivateKey rsaPrivateKey;
    private Activity activity;

    @Inject
    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public static PinOnMobile getInstance(Activity activity, Institution institution, Account account) throws Exception {
        if (singletonPinOnMobileInstance == null) {
            singletonPinOnMobileInstance = new PinOnMobile();
            singletonPinOnMobileInstance.institution = institution;
            singletonPinOnMobileInstance.activity = activity;
            singletonPinOnMobileInstance.account = account;
            DaggerWrapper.getComponent(activity,institution).inject(singletonPinOnMobileInstance);
        }
        // create a class for keys
        if (singletonPinOnMobileInstance.mleKey == null || singletonPinOnMobileInstance.sessionKey == null){
            singletonPinOnMobileInstance.initializeIdentityServiceConfig();
        }
        return singletonPinOnMobileInstance;
    }

    public void changePin(Activity activity){
        System.out.println("activity");
    }

    private void initializeIdentityServiceConfig() throws Exception {
        String mleKeyResponse = new GetMLETask().execute().get();
        if(mleKeyResponse != null) {
            mleKey = mleKeyResponse;
        } else {
            throw new Exception("failed to get mle key");
        }
        String sessionKeyResponse = new GetSessionKeyTask().execute().get();
        if(sessionKeyResponse != null){
            sessionKey = sessionKeyResponse;
        }else{
            throw new Exception("failed to get session key");
        }
    }

    public class GetMLETask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                GenerateMLEKeyResponse generateMLEKeyResponse = singletonPinOnMobileInstance.retrofit.create(GetMLEKey.class)
                        .getMLEKey()
                        .execute()
                        .body();
                return generateMLEKeyResponse.getItem().getKey();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private static class GetSessionKeyTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                GenerateSessionKeyResponse generateSessionKeyResponse = singletonPinOnMobileInstance.retrofit.create(GetSessionKey.class)
                        .getSessionKey()
                        .execute()
                        .body();
                return generateSessionKeyResponse.getItem().getKey();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void launchUI(){
        //pass props here
        Intent intent = new Intent(activity, PinOnMobileActivity.class);
//        intent.putExtra("Institution", singletonIdentityInstance.institution);
//        intent.putExtra("account",singletonIdentityInstance.account);
        activity.startActivity(intent);
    }

}
