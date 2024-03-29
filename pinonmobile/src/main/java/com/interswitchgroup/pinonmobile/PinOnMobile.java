package com.interswitchgroup.pinonmobile;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchgroup.pinonmobile.api.models.GeneratePinSelectOTPPayload;
import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;
import com.interswitchgroup.pinonmobile.api.models.PinSelectPayload;
import com.interswitchgroup.pinonmobile.api.tasks.GeneratePinSelect;
import com.interswitchgroup.pinonmobile.api.tasks.GeneratePinSelectOtp;
import com.interswitchgroup.pinonmobile.api.tasks.GetMLETask;
import com.interswitchgroup.pinonmobile.api.tasks.GetSessionKeyTask;
import com.interswitchgroup.pinonmobile.di.DaggerWrapper;
import com.interswitchgroup.pinonmobile.interfaces.FailureCallback;
import com.interswitchgroup.pinonmobile.interfaces.SuccessCallback;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.GenericResponse;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.models.Keys;
import com.interswitchgroup.pinonmobile.models.ResponsePayloadModel;
import com.interswitchgroup.pinonmobile.ui.PinOnMobileActivity;
import com.interswitchgroup.pinonmobile.utils.TripleDES;

import java.io.Serializable;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class PinOnMobile implements Serializable {
    private static PinOnMobile singletonPinOnMobileInstance;
    private Retrofit retrofit;
    private Institution institution;
    private Keys keys;
    private Account account;
    private Activity activity;
    private FailureCallback failureCallback;
    private SuccessCallback successCallback;

    @Inject
    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public SuccessCallback getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(SuccessCallback successCallback) {
        this.successCallback = successCallback;
    }

    public FailureCallback getFailureCallback() {
        return failureCallback;
    }

    public void setFailureCallback(FailureCallback failureCallback) {
        this.failureCallback = failureCallback;
    }

    public static PinOnMobile getInstance(Activity activity, Institution institution, Account account) throws Exception {
        if (singletonPinOnMobileInstance == null) {
            singletonPinOnMobileInstance = new PinOnMobile();
            singletonPinOnMobileInstance.institution = institution;
            singletonPinOnMobileInstance.activity = activity;
            singletonPinOnMobileInstance.account = account;
            DaggerWrapper.getComponent(activity,institution).inject(singletonPinOnMobileInstance);
        }
        // i have to always get a different session and mle each time we initialize
        singletonPinOnMobileInstance.initializeIdentityServiceConfig();
        return singletonPinOnMobileInstance;
    }

    private void initializeIdentityServiceConfig() throws Exception {
        this.keys  = new Keys();
        String mleKeyResponse = new GetMLETask(singletonPinOnMobileInstance.retrofit)
                .execute()
                .get();
        if(mleKeyResponse != null) {
            keys.setMleKey(mleKeyResponse);
        } else {
            this.keys = null;
            throw new Exception("Failed to get mle key");
        }
        String sessionKeyResponse = new GetSessionKeyTask(singletonPinOnMobileInstance.retrofit).execute().get();
        // convert json string to pojo
        Gson gson = new Gson();
        GenerateSessionKeyResponse generateSessionKeyResponse = gson.fromJson(sessionKeyResponse, GenerateSessionKeyResponse.class);
        if(sessionKeyResponse != null){
            System.out.println("SESSION DATA::" + sessionKeyResponse);
            keys.setSessionKey(generateSessionKeyResponse.getItem().getKey());
            keys.setSessionKeyId(generateSessionKeyResponse.getItem().getKeyID());
        }else{
            this.keys = null;
            throw new Exception("Failed to get session key");
        }
    }

    public ResponsePayloadModel sendPin(String pin, String otp, SuccessCallback successCallback, FailureCallback failureCallback) throws Exception {
        Log.d("PinOnMobile", "SENDING OTP AND PIN BLOCK....");
        String desKey = this.keys.getSessionKey();
        TripleDES tripleDES = new TripleDES(desKey,4);
        String pinBlock = tripleDES.encrypt(account.getAccountNumber(),pin);
        PinSelectPayload pinSelectPayload = new PinSelectPayload(pinBlock, account.getCardSerialNumber(), otp, account.getAccountNumber(), institution.getInstitutionId(), keys.getMleKey(), account.getExpiryDate());
        String response =  new GeneratePinSelect(singletonPinOnMobileInstance.retrofit, this.keys,institution,pinSelectPayload).execute().get();
        Gson gson = new Gson();
        ResponsePayloadModel successModel = gson.fromJson(response, ResponsePayloadModel.class);
        System.out.println("RESPONSE DATA::"+successModel.toString());
        if(successModel.code != "0"){
            failureCallback.onError(new GenericResponse(successModel.code,successModel.message));
        }else{
            successCallback.onSuccess(successModel);
        }
        return successModel;

    }

    public void generateOtp() throws Exception {
        Log.d("PinOnMobile","GENERATING OTP....");
        GeneratePinSelectOTPPayload generatePinSelectOTPPayload = new GeneratePinSelectOTPPayload();

        if(account.getExpiryDate().equals("")){
            //if expiry is absent treat it like a debit
            generatePinSelectOTPPayload.setSerno(account.getCardSerialNumber());
            generatePinSelectOTPPayload.setPan("");
            generatePinSelectOTPPayload.setExpiryDate("");
        }else{
            //if expiry is absent treat it like a credit
            generatePinSelectOTPPayload.setSerno(account.getCardSerialNumber());
            generatePinSelectOTPPayload.setPan(account.getAccountNumber());
            generatePinSelectOTPPayload.setExpiryDate(account.getExpiryDate());

        }

        System.out.println("PAYLOAD::"+ generatePinSelectOTPPayload);


        String response = new GeneratePinSelectOtp(singletonPinOnMobileInstance.retrofit, generatePinSelectOTPPayload,institution, keys.getMleKey()).execute().get();
        Gson gson = new Gson();
        ResponsePayloadModel successModel = gson.fromJson(response, ResponsePayloadModel.class);
        System.out.println("RESPONSE DATA::"+successModel.toString());
        if(!successModel.code.equals("0")){
            //failureCallback.onError(new GenericResponse(successModel.code,successModel.message));
            throw new Exception(successModel.getMessage());
        }

    }

    /**
     *
     * @param successCallback
     * @param failureCallback
     */
    public void setPin(final SuccessCallback successCallback, final FailureCallback failureCallback){
        this.failureCallback = failureCallback;
        this.successCallback = successCallback;
        try {
            Intent intent = new Intent(activity, PinOnMobileActivity.class);
            intent.putExtra("Institution", singletonPinOnMobileInstance.institution);
            intent.putExtra("Account",singletonPinOnMobileInstance.account);
            activity.startActivity(intent);

        }catch(Exception e){
            this.failureCallback.onError(new GenericResponse("", e.getMessage()));
        }
    }

}
