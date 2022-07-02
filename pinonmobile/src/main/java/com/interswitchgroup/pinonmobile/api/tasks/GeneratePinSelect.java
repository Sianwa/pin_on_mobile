package com.interswitchgroup.pinonmobile.api.tasks;

import static com.interswitchgroup.pinonmobile.encryption.Encryption.getKeyFromString;

import android.os.AsyncTask;
import android.util.Log;

import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;
import com.interswitchgroup.pinonmobile.api.models.PinSelectPayload;
import com.interswitchgroup.pinonmobile.api.services.GeneratePinSelectOTP;
import com.interswitchgroup.pinonmobile.encryption.Encryption;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.models.Keys;

import java.security.interfaces.RSAPublicKey;

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
            EncryptedPayload encryptedResponse = retrofit
                    .create(GeneratePinSelectOTP.class)
                    .generatePinSelect(encryptedPayload,institution.getKeyId(),sessionKeyId)
                    .execute()
                    .body();

            String dec =  Encryption.getDecryptedPayload(encryptedResponse.toString(),institution.getRsaPrivateKey());
            Log.d("",dec);
            return  dec;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
