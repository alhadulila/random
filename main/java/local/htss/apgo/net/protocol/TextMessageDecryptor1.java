package local.htss.apgo.net.protocol;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.data.MessageSessionKey;
import local.htss.apgo.net.protocol.data.TextChannel;
import local.htss.apgo.net.protocol.data.TextMessage1;
import local.htss.apgo.lib.crypto.AES;
import local.htss.apgo.lib.crypto.SHA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TextMessageDecryptor1 {
    private TextMessage1 message;
    private KeyPair user;
    private DecryptionStatus statusDecrypt;
    private VerifyStatus statusSign;
    private NetworkThread networkThread;
    private TextChannel ch;
    private String textBody;
    public TextMessageDecryptor1(TextMessage1 message, KeyPair user, NetworkThread networkThread) {
        this.message = message;
        this.user = user;
        this.networkThread = networkThread;
    }

    public void decrypt() {
        MessageSessionKey messageSessionKey = null;
        for (MessageSessionKey destKey : message.getDestKeys()) {
            if(Arrays.equals(destKey.getDst(), SHA.doSHA512(user.getPublic().getEncoded()))) {
                messageSessionKey = destKey;
                break;
            }
        }
        if(messageSessionKey == null) {
            setStatusDecrypt(DecryptionStatus.FAILED_NO_DST);
            return;
        }
        try {
            byte[] decrypted = AES.crypt(false,
                    messageSessionKey.getKey(),
                    null,
                    message.getContent()
                    );
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public TextMessage1 getMessage() {
        return message;
    }

    public void setMessage(TextMessage1 message) {
        this.message = message;
    }

    public KeyPair getUser() {
        return user;
    }

    public void setUser(KeyPair user) {
        this.user = user;
    }

    public DecryptionStatus getStatusDecrypt() {
        return statusDecrypt;
    }

    public void setStatusDecrypt(DecryptionStatus statusDecrypt) {
        this.statusDecrypt = statusDecrypt;
    }

    public VerifyStatus getStatusSign() {
        return statusSign;
    }

    public void setStatusSign(VerifyStatus statusSign) {
        this.statusSign = statusSign;
    }

    public NetworkThread getNetworkThread() {
        return networkThread;
    }

    public void setNetworkThread(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public TextChannel getCh() {
        return ch;
    }

    public void setCh(TextChannel ch) {
        this.ch = ch;
    }

    public static enum DecryptionStatus {
        OK,
        FAILED,
        FAILED_NO_DST
    }
    public static enum VerifyStatus {
        OK,
        WRONG,
        FAILED
    }
}
