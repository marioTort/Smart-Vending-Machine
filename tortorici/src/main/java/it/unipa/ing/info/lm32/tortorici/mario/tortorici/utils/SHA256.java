package it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String getHash(String password) {

        String newPassword = null;

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] bytes = messageDigest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            newPassword = stringBuilder.toString();

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return newPassword;

    }

}
