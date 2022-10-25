package com.interswitchgroup.pinonmobile.api.tasks;

import static com.interswitchgroup.pinonmobile.encryption.Encryption.getKeyFromString;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;
import com.interswitchgroup.pinonmobile.api.models.GeneratePinSelectOTPPayload;
import com.interswitchgroup.pinonmobile.api.services.GeneratePinSelectOTP;
import com.interswitchgroup.pinonmobile.encryption.Encryption;
import com.interswitchgroup.pinonmobile.models.Institution;

import java.security.interfaces.RSAPublicKey;

import retrofit2.Response;
import retrofit2.Retrofit;

public class GeneratePinSelectOtp extends AsyncTask<String,Void,String> {
    GeneratePinSelectOTPPayload generatePinSelectOTPPayload;
    Institution institution;
    String mle;
    Retrofit retrofit;

    public GeneratePinSelectOtp(Retrofit retrofit, GeneratePinSelectOTPPayload generatePinSelectOTPPayload,Institution institution, String mle) {
        this.generatePinSelectOTPPayload = generatePinSelectOTPPayload;
        this.mle = mle;
        this.institution = institution;
        this.retrofit = retrofit;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            EncryptedPayload encryptedPayload = new EncryptedPayload();
            RSAPublicKey rsaPubKey = (RSAPublicKey) getKeyFromString(mle);
            encryptedPayload.setEncData(Encryption.encryptString(rsaPubKey
                    , generatePinSelectOTPPayload.toString(), mle));
            Response<EncryptedPayload> encryptedResponse = retrofit.create(GeneratePinSelectOTP.class)
                    .generatePinSelectOTP(encryptedPayload, institution.getKeyId())
                    .execute();
            if(encryptedResponse.errorBody() != null){
                Gson gson = new Gson();
                EncryptedPayload failureResp = gson.fromJson(encryptedResponse.errorBody().string(), EncryptedPayload.class);
                Log.d("ERROR RESPONSE::", encryptedResponse.errorBody().string());
                return Encryption.getDecryptedPayload(failureResp.getEncData(), institution.getRsaPrivateKey());
            }

            String dec = Encryption.getDecryptedPayload(encryptedResponse.body().getEncData(), institution.getRsaPrivateKey());
            Log.d("OTP ENC DATA:", dec);
            return  dec;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}