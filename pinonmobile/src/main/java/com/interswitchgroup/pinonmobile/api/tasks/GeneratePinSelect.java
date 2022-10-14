package com.interswitchgroup.pinonmobile.api.tasks;

import static com.interswitchgroup.pinonmobile.encryption.Encryption.getKeyFromString;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;
import com.interswitchgroup.pinonmobile.api.models.GenerateSessionKeyResponse;
import com.interswitchgroup.pinonmobile.api.models.PinSelectPayload;
import com.interswitchgroup.pinonmobile.api.services.GeneratePinSelectOTP;
import com.interswitchgroup.pinonmobile.encryption.Encryption;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.models.Keys;

import java.security.interfaces.RSAPublicKey;

import retrofit2.Response;
import retrofit2.Retrofit;

public class GeneratePinSelect extends AsyncTask<String,Void,String> {
    String mle;
    String sessionKeyId;
    Institution institution;
    PinSelectPayload pinSelectPayload;
    Retrofit retrofit;

    public GeneratePinSelect(Retrofit retrofit, Keys keys, Institution institution, PinSelectPayload pinSelectPayload) {
        this.mle = keys.getMleKey();
        this.sessionKeyId = keys.getSessionKeyId();
        this.institution = institution;
        this.pinSelectPayload = pinSelectPayload;
        this.retrofit = retrofit;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            EncryptedPayload encryptedPayload = new EncryptedPayload();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getKeyFromString(mle);
            encryptedPayload.setEncData(Encryption.encryptString(rsaPublicKey,pinSelectPayload.toString(),mle));
            Response<EncryptedPayload> payloadResponse = retrofit
                    .create(GeneratePinSelectOTP.class)
                    .generatePinSelect(encryptedPayload,institution.getKeyId(),sessionKeyId)
                    .execute();
            if(payloadResponse.errorBody() != null){
                Gson gson = new Gson();
                EncryptedPayload failureResp = gson.fromJson(payloadResponse.errorBody().string(), EncryptedPayload.class);
                Log.d("ERROR RESPONSE::", payloadResponse.errorBody().string());
                return Encryption.getDecryptedPayload(failureResp.getEncData(), institution.getRsaPrivateKey());
            }
            String dec = Encryption.getDecryptedPayload(payloadResponse.body().getEncData(), institution.getRsaPrivateKey());
            Log.d("RESPONSE:: ", payloadResponse.body().getEncData());
            return dec;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
