package org.grakovne.mds.server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    public static String hashSha512(String string) {
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            return convertByteToHex(sha512.digest(string.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't hash password");
        }
    }

    private static String convertByteToHex(byte data[]) {
        StringBuilder hexData = new StringBuilder();
        for (byte symbol : data) {
            hexData.append(Integer.toString((symbol & 0xff) + 0x100, 16).substring(1));
        }

        return hexData.toString();
    }
}
