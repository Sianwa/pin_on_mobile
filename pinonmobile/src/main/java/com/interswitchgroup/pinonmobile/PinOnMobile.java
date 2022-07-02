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
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.models.Keys;
import com.interswitchgroup.pinonmobile.models.PinBlock;
import com.interswitchgroup.pinonmobile.ui.PinOnMobileActivity;

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

    public static PinOnMobile getInstance(Activity activity, Institution institution, Account account) throws Exception {
        if (singletonPinOnMobileInstance == null) {
            singletonPinOnMobileInstance = new PinOnMobile();
            singletonPinOnMobileInstance.institution = institution;
            singletonPinOnMobileInstance.activity = activity;
            singletonPinOnMobileInstance.account = account;
            DaggerWrapper.getComponent(activity,institution).inject(singletonPinOnMobileInstance);
        }
        // create a class for keys
        if (singletonPinOnMobileInstance.keys == null){
            singletonPinOnMobileInstance.initializeIdentityServiceConfig();
        }
        return singletonPinOnMobileInstance;
    }

    private void initializeIdentityServiceConfig() throws Exception {
        this.keys  = new Keys();
        String mleKeyResponse = new GetMLETask(singletonPinOnMobileInstance.retrofit).execute().get();
        if(mleKeyResponse != null) {
            keys.setMleKey(mleKeyResponse);
        } else {
            throw new Exception("failed to get mle key");
        }
        String sessionKeyResponse = new GetSessionKeyTask(singletonPinOnMobileInstance.retrofit).execute().get();
        // convert json string to pojo
        Gson gson = new Gson();
        GenerateSessionKeyResponse generateSessionKeyResponse = gson.fromJson(sessionKeyResponse, GenerateSessionKeyResponse.class);
        if(sessionKeyResponse != null){
            keys.setSessionKey(generateSessionKeyResponse.getItem().getKey());
            keys.setSessionKeyId(generateSessionKeyResponse.getItem().getKeyID());
        }else{
            throw new Exception("failed to get session key");
        }
    }





    public void sendPin() throws Exception {
        Log.d("PinOnMobile", "sending otp");
        String desKey = this.keys.getSessionKey();
        PinBlock pinBlock = new PinBlock("2092", desKey, account.getAccountNumber());
        String pinBlockString = pinBlock.genPinBlock();
        PinSelectPayload pinSelectPayload = new PinSelectPayload(pinBlockString,account.getCardSerialNumber(),"2092");
        new GeneratePinSelect(singletonPinOnMobileInstance.retrofit, this.keys,institution,pinSelectPayload).execute().get();
    }
    public void generateOtp() throws Exception {
        Log.d("PinOnMobile","generating otp");
        GeneratePinSelectOTPPayload generatePinSelectOTPPayload = new GeneratePinSelectOTPPayload();
        generatePinSelectOTPPayload.setSerno(account.getCardSerialNumber());

        new GeneratePinSelectOtp(singletonPinOnMobileInstance.retrofit, generatePinSelectOTPPayload,institution, keys.getMleKey()).execute().get();
    }

    /**
     *
     * @param successCallback
     * @param failureCallback
     */
    public void changePin(final SuccessCallback successCallback, final FailureCallback failureCallback){
        this.failureCallback = failureCallback;
        this.successCallback = successCallback;
        try {
            generateOtp();
            sendPin();
            Intent intent = new Intent(activity, PinOnMobileActivity.class);
            intent.putExtra("Institution", singletonPinOnMobileInstance.institution);
            intent.putExtra("Account",singletonPinOnMobileInstance.account);
            // session key
            activity.startActivity(intent);
            activity.finish();
            this.successCallback.onSuccess("e");

        }catch(Exception e){
            this.failureCallback.onError(e);
        }
    }

    public void newPin(final SuccessCallback successCallback, final FailureCallback failureCallback) {
        this.failureCallback = failureCallback;
        this.successCallback = successCallback;
        try {
            generateOtp();
            sendPin();
            Intent intent = new Intent(activity, PinOnMobileActivity.class);
            intent.putExtra("Institution", singletonPinOnMobileInstance.institution);
            intent.putExtra("Account",singletonPinOnMobileInstance.account);
            this.successCallback.onSuccess("e");

            // session key
//            activity.startActivity(intent);
        }catch(Exception e){
            this.failureCallback.onError(e);
        }
    }

}
