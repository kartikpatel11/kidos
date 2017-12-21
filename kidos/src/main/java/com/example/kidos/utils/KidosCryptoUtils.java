package com.example.kidos.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Kartik on 20/11/17.
 */

public class KidosCryptoUtils {

    /**
     * This class defines common routines for generating
     * authentication signatures for AWS requests.
     */
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static final String KEY=",R(a#Wyc0>x]]Sbv/j1)6/Gfs|xxIw";

    private static final Mac mac=getMacInstance();

    private static final SecretKeySpec signingKey=new SecretKeySpec(KEY.getBytes(), HMAC_SHA1_ALGORITHM);;

    static
    {
        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Computes RFC 2104-compliant HMAC signature.
     * * @param data
     * The data to be signed.
     * @param data
     * The signing key.
     * @return
     * The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws
     * java.security.SignatureException when signature generation fails
     */
    public static String encodeData(String data)
    {
        String result=null;
        try {
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            //byte[] rawHmac = data.getBytes("UTF-8");
            result = Base64.encodeToString(rawHmac, Base64.NO_WRAP);
            System.out.println(data+"-"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Mac getMacInstance() {
        Mac mac_=null;
        // get an hmac_sha1 Mac instance and initialize with the signing key
        try {
            mac_= Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mac_;
    }

}
