package com.movilizer.util.encoding;

import org.apache.commons.codec.binary.Base64;

/**
 * @author peter.grigoriev@movilizer.com
 */
public class EncodingUtils {
    public static String byteArrayToBase64(byte[] data) {
        if (data == null) {
            return "";
        }

        Base64 base64Encoder = new Base64();
        return new String(base64Encoder.encode(data));
    }

    public static byte[] base64ToByteArray(String encodedString) {
        if (encodedString == null) {
            return null;
        }

        Base64 base64 = new Base64();
        return base64.decode(encodedString.getBytes());
    }
}
