package com.taoxue.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Creator: Christy
 * Date： 2015/12/4
 * Desc: Please input your description about it.
 */
public class EncryptUtil {

    /**
     * 3DES密钥
     */
    private static final String DES3_KEY = "aHpxbG1GZkFHb3VyeExvSENSdG4zQnRv";

    /**
     * 3DES加密
     *
     * @param str
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String DES3Encode(String str) {
        String result = "";
        try {
            byte[] key = Base64.decode(DES3_KEY, Base64.DEFAULT);
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] bOut = cipher.doFinal(str.getBytes("UTF-8"));
            for (int i = 0; i < bOut.length; i++) {
                String hex = Integer.toHexString(bOut[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                result += hex.toUpperCase();
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static String DES3Decode(String str) {
        String result = "";
        try {
            byte[] bIn = new byte[str.length() / 2];
            for (int i = 0; i < bIn.length; i++) {
                bIn[i] = (byte) (0xFF & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
            }
            byte[] key = Base64.decode(DES3_KEY, Base64.DEFAULT);
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] bOut = cipher.doFinal(bIn);
            result = new String(bOut, "UTF-8");
        } catch (Exception e) {
            result = "decode error";
        }
        return result;
    }


    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String MD5Encode(String str) {
        StringBuffer hexString = new StringBuffer();
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(str.getBytes("GBK"));
            byte[] messageDigest = algorithm.digest();
            // Create HEX String
            for (int i = 0; i < messageDigest.length; i++) {
                int val = (0xFF & messageDigest[i]);
                if (val < 0x10)
                    hexString.append('0');
                hexString.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
        }
        return hexString.toString().toUpperCase();
    }
}
