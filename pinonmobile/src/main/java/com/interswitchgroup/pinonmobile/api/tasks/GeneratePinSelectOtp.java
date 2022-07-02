package com.interswitchgroup.pinonmobile.api.tasks;

import static com.interswitchgroup.pinonmobile.encryption.Encryption.getKeyFromString;

import android.os.AsyncTask;
import android.util.Log;

import com.interswitchgroup.pinonmobile.api.models.EncryptedPayload;
import com.interswitchgroup.pinonmobile.api.models.GeneratePinSelectOTPPayload;
import com.interswitchgroup.pinonmobile.api.services.GeneratePinSelectOTP;
import com.interswitchgroup.pinonmobile.encryption.Encryption;
import com.interswitchgroup.pinonmobile.models.Institution;

import java.security.interfaces.RSAPublicKey;

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
            EncryptedPayload encryptedResponse = retrofit.create(GeneratePinSelectOTP.class)
                    .generatePinSelectOTP(encryptedPayload, institution.getKeyId())
                    .execute()
                    .body();

            String dec = Encryption.getDecryptedPayload(encryptedResponse.getEncData(), institution.getRsaPrivateKey());
            Log.d("", dec);
            return  dec;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}