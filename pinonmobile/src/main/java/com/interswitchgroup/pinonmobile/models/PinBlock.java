package com.interswitchgroup.pinonmobile.models;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PinBlock {

    public String pin;
    public String des3Key;
    public String account;

    public PinBlock(String pin, String des3Key, String account) {
        this.pin = pin;
        this.des3Key = des3Key;
        this.account = account;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDes3Key() {
        return des3Key;
    }

    public void setDes3Key(String des3Key) {
        this.des3Key = des3Key;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public static String encodePin(String pin, String account) {

        final String pinLenHead = StringUtils.leftPad(Integer.toString(pin.length()), 2, '0') + pin;
        final String pinData = StringUtils.rightPad(pinLenHead, 16, 'F');
        final byte[] bPin = Hex.decode(pinData);
        final String panPart = account;
        final String panData = StringUtils.leftPad(panPart, 16, '0');
        final byte[] bPan = Hex.decode(panData);

        final byte[] pinblock = new byte[8];
        for (int i = 0; i < 8; i++) {
            pinblock[i] = (byte) (bPin[i] ^ bPan[i]);
        }

        return Hex.toHexString(pinblock).toUpperCase();

    }

    public static String encrypt3DES(String clearText, String deskey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher decryptCipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");

        byte[] desKeyBytes = Hex.decode(deskey);
        SecretKey key = new SecretKeySpec(desKeyBytes, "DESede");
        decryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] authDataBytes = decryptCipher.doFinal(Hex.decode(clearText.getBytes("UTF-8")));
        return Hex.toHexString(authDataBytes).toUpperCase();
    }
    public String genPinBlock() throws Exception {
        String pinBlock = encodePin(pin, account);
        return encrypt3DES(pinBlock, des3Key);
    }
}
