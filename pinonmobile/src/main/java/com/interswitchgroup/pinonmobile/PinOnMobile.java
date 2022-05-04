package com.interswitchgroup.pinonmobile;

import static com.interswitchgroup.pinonmobile.encryption.Encryption.getKeyFromString;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;
import com.interswitchgroup.pinonmobile.api.models.GenerateMLEKeyResponse;
import com.interswitchgroup.pinonmobile.api.models.GeneratePinSelectOTPPayload;
import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;
import com.interswitchgroup.pinonmobile.api.services.GeneratePinSelectOTP;
import com.interswitchgroup.pinonmobile.api.services.GetMLEKey;
import com.interswitchgroup.pinonmobile.api.services.GetSessionKey;
import com.interswitchgroup.pinonmobile.di.DaggerWrapper;
import com.interswitchgroup.pinonmobile.encryption.Encryption;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.ui.PinOnMobileActivity;

import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
                if (generateMLEKeyResponse == null) return null;
                String key = generateMLEKeyResponse.getItem().getKey();
                return key;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
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
                return null;
            }
        }
    }

    public void generatePinSelectOtp(GeneratePinSelectOTPPayload generatePinSelectOTPPayload,String mle, String sesKey) throws Exception {

        // create an encrypted payload
        EncryptedPayload encryptedPayload = new EncryptedPayload();
        RSAPublicKey rsaPubKey = (RSAPublicKey) getKeyFromString(this.mleKey);
        encryptedPayload.setEncData(Encryption.encryptString(rsaPubKey
                ,generatePinSelectOTPPayload.toString(),mle));

        try {
            Disposable subscribe = retrofit.create(GeneratePinSelectOTP.class)
                    .generatePinSelectOTP(encryptedPayload,institution.getKeyId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(encryptedPayload1 -> {
                        //1. Decrypt payload
                        String dec =  Encryption.getDecryptedPayload(encryptedPayload1.getEncData(),institution.getRsaPrivateKey());
                    }, throwable -> {

                    });

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void generateOtp() throws Exception {
        Log.d("PinOnMobile","sending sms");
        GeneratePinSelectOTPPayload generatePinSelectOTPPayload = new GeneratePinSelectOTPPayload();
        generatePinSelectOTPPayload.setSerno(account.getCardSerialNumber());

        generatePinSelectOtp(generatePinSelectOTPPayload, institution.getRsaPublicKey(),sessionKey);
    }
    public void launchUI() throws Exception {
        generateOtp();
        //pass props here
        Intent intent = new Intent(activity, PinOnMobileActivity.class);
        intent.putExtra("Institution", singletonPinOnMobileInstance.institution);
        intent.putExtra("account",singletonPinOnMobileInstance.account);
        activity.startActivity(intent);
    }

}
