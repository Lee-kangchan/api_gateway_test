package com.example.apt_gateway_test.util;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TokenUtil {
    public static String decryptAES_DB(final String Str) {
        try {
            final Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(PropertyData.getTokenKey(), "UTF-8"));
            String str = new String(decryptCipher.doFinal(Hex.decodeHex(Str.toCharArray())), CharEncoding.UTF_8);
            return str;
        } catch (Exception e) {
            return "";
        }
    }
    public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) throws Exception{
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding))
                finalKey[i++%16]^=b;
            return new SecretKeySpec(finalKey, "AES");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
