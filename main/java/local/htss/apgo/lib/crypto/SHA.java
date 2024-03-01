package local.htss.apgo.lib.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    public static byte[] doSHA512(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            return messageDigest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] doSHA256(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
