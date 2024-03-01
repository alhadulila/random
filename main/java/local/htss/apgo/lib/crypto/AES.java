package local.htss.apgo.lib.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES {
    public static SecretKey createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
    public static IvParameterSpec createIv() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return new IvParameterSpec(bytes);
    }
    public static byte[] crypt(boolean enc, SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(enc ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(bytes);
    }
    public static SecretKey load(byte[] bytes) {
        return new SecretKeySpec(bytes, "AES");
    }
}
