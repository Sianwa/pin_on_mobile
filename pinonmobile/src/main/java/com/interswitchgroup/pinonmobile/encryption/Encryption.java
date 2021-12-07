package com.interswitchgroup.pinonmobile.encryption;

import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.models.PinBlock;
import com.interswitchgroup.pinonmobile.models.PinBlockPayload;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    public void test (Institution institution, Account account) throws Exception {
        String desKey = "1D149AB6D530E7F6D7D8148CE7636812";

        PinBlock pinBlock = new PinBlock("1234", desKey, account.getAccountNumber());
        String pinBlockString = pinBlock.genPinblock();
        PinBlockPayload pinBlockPayload = new PinBlockPayload(institution.getInstitutionId()
                ,account.getCardSerialNumber(),pinBlock.genPinblock());
//        String encPayload = encryptString(institution.getRsaPublicKey(),pinBlockPayload.getPinBlockPayloadString(), keyId);

        System.out.println("Encrypted pinblock =" + pinBlockString);
//        System.out.println("Encrypted payload =" + encPayload);
    }



    public static String decrypt3DES(String clearText, String deskey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher decryptCipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");

        byte[] desKeyBytes = Hex.decode(deskey);
        SecretKey key = new SecretKeySpec(desKeyBytes, "DESede");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);

        byte[] authDataBytes = decryptCipher.doFinal(clearText.getBytes("UTF-8"));
        return new String(Hex.encode(authDataBytes));
    }

    public static String encryptString(RSAPublicKey pubKey, String payload, String keyId) throws Exception {
        JWEHeader.Builder headerBuilder = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

        headerBuilder.keyID(keyId);
        headerBuilder.customParam("iat", System.currentTimeMillis());

        JWEObject jweObject = new JWEObject(headerBuilder.build(), new Payload(payload));
        jweObject.encrypt(new RSAEncrypter(pubKey));
        return jweObject.serialize();
    }

    public static PublicKey getKeyFromString(String key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] byteKey = Base64.decode(key.getBytes("UTF-8"));
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        return keyfactory.generatePublic(X509publicKey);
    }

    public static String getDecryptedPayload(String encData, String clientPrivateKey) {
        String response = "";
        try {
            JWEObject jweObject = JWEObject.parse(encData);
            //If you have used passphrase while generating the csr make sure you the same while getting the private key. Otherwise decryption will fail.
            jweObject.decrypt(new RSADecrypter(privateKeyFromString(clientPrivateKey)));
            response = jweObject.getPayload().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static PrivateKey privateKeyFromString(String key) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        byte[] byteKey = Base64.decode(key.getBytes());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");

        return keyfactory.generatePrivate(pkcs8EncodedKeySpec);

    }
    public static PublicKey getPubKeyFromString(String key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] byteKey = Base64.decode(key.getBytes("UTF-8"));
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        return keyfactory.generatePublic(X509publicKey);
    }
}
