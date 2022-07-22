package com.interswitchgroup.pinonmobile.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TripleDES {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String ALGORITHM = "DESede";
    private static final String MODE = "ECB";
    private static final String PADDING = "NoPadding";

    /** algorithm/mode/padding */
    private static final String TRANSFORMATION = ALGORITHM+"/"+MODE+"/"+PADDING;

    private final String key;
    private final int pinLength;

    public TripleDES(String key, int pinLength) {
        this.key = key;
        this.pinLength = pinLength;
    }

    public String encrypt(String pan, String pinClear) throws Exception {

        if(pinClear.length() != pinLength) {
            System.out.println("Incorrect PIN length given. Please fix! pinClear.size() " + "!= " + " pinLength : " + pinClear.length() + " !=" + pinLength);
        }

        String pinEncoded = encodePinBlockAsHex(pan, pinClear);
        byte[] tmp = h2b(this.key);
        byte[] key = new byte[24];
        System.arraycopy(tmp, 0, key, 0, 16);
        System.arraycopy(tmp, 0, key, 16, 8);
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(TRANSFORMATION,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM));
        byte[] plaintext = cipher.doFinal(h2b(pinEncoded));
        return b2h(plaintext);
    }

    public String decrypt(String pan, String encryptedPin) throws Exception {
        byte[] tmp = h2b(this.key);
        byte[] key = new byte[24];
        System.arraycopy(tmp, 0, key, 0, 16);
        System.arraycopy(tmp, 0, key, 16, 8);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM));
        byte[] plaintext = cipher.doFinal(h2b(encryptedPin));
        String pinEncoded = b2h(plaintext);
        return decodePinBlock(pan, pinEncoded);
    }

    public String decodePinBlock(String pan, String pinEncoded) throws Exception {
        pan = pan.substring(pan.length() - 12 - 1, pan.length() - 1);
        String paddingPAN = "0000".concat(pan);
        byte[] pinBlock = xorBytes(h2b(paddingPAN), h2b(pinEncoded));
        return b2h(pinBlock).substring(2, pinLength+2);
    }

    public String encodePinBlockAsHex(String pan, String pin) throws Exception {
        pan = pan.substring(pan.length() - 12 - 1, pan.length() - 1);
        String paddingPAN = "0000".concat(pan);

        String Fs = "FFFFFFFFFFFFFFFF";
        String paddingPIN = "0" + pin.length() + pin + Fs.substring(2 + pin.length());

        byte[] pinBlock = xorBytes(h2b(paddingPAN), h2b(paddingPIN));

        return b2h(pinBlock);
    }

    private static byte[] xorBytes(byte[] a, byte[] b) throws Exception {
        if (a.length != b.length) {
            throw new Exception();
        }
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            int r = 0;
            r = a[i] ^ b[i];
            r &= 0xFF;
            result[i] = (byte) r;
        }
        return result;
    }

    private static byte[] h2b(String hex) {
        if ((hex.length() & 0x01) == 0x01)
            throw new IllegalArgumentException();
        byte[] bytes = new byte[hex.length() / 2];
        for (int idx = 0; idx < bytes.length; ++idx) {
            int hi = Character.digit((int) hex.charAt(idx * 2), 16);
            int lo = Character.digit((int) hex.charAt(idx * 2 + 1), 16);
            if ((hi < 0) || (lo < 0))
                throw new IllegalArgumentException();
            bytes[idx] = (byte) ((hi << 4) | lo);
        }
        return bytes;
    }

    private static String b2h(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        for (int idx = 0; idx < bytes.length; ++idx) {
            int hi = (bytes[idx] & 0xF0) >>> 4;
            int lo = (bytes[idx] & 0x0F);
            hex[idx * 2] = (char) (hi < 10 ? '0' + hi : 'A' - 10 + hi);
            hex[idx * 2 + 1] = (char) (lo < 10 ? '0' + lo : 'A' - 10 + lo);
        }
        return new String(hex);
    }
}