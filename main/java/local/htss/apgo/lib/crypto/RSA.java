package local.htss.apgo.lib.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class RSA {
    public static Key loadKey(byte[] bytes, boolean pub) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        if(pub) {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(bytes);
            return keyFactory.generatePublic(encodedKeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
    }
    public static KeyPair gen(int s) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(s);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static KeyPair joinKeys(PrivateKey privateKey, PublicKey publicKey) {
        return new KeyPair(publicKey, privateKey);
    }
    public static byte[] encrypt(PublicKey publicKey, byte[] bytes) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return encryptCipher.doFinal(bytes);
    }
    public static byte[] decrypt(PrivateKey privateKey, byte[] bytes) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        encryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return encryptCipher.doFinal(bytes);
    }
    public static byte[] sign(PrivateKey privateKey, byte[] bytes) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return encryptCipher.doFinal(SHA.doSHA512(bytes));
    }

    public static boolean validateSignature(PublicKey publicKey, byte[] plain, byte[] cipher) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] plain2 = encryptCipher.doFinal(cipher);
        return Arrays.equals(SHA.doSHA512(plain), plain2);
    }
}
