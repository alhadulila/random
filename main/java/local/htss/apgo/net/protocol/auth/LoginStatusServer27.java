package local.htss.apgo.net.protocol.auth;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Sent by server to indicate if login worked
 */
public class LoginStatusServer27 extends ProtocolFrame {
    private transient DataInputStream passStream;
    private int challenge;
    private int status;
    public LoginStatusServer27(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(status);
        if(challenge != 0) {
            dataOutputStream.writeInt(challenge);
        }
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        passStream = dataInputStream;
    }

    //Warning: data should be read during the process phase from 'passStream'
    @Override
    public void processClient() throws IOException {
        super.processClient();
    }

    public int getChallenge() {
        return challenge;
    }

    public void setChallenge(int challenge) {
        this.challenge = challenge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataInputStream getPassStream() {
        return passStream;
    }

    public void setPassStream(DataInputStream passStream) {
        this.passStream = passStream;
    }

    public static class Ids {
        public static final int CHALLENGE_TOTP = 1;
        public static final int CHALLENGE_CAPTCHA_TXT = 2;

        public static final int STATUS_OK = 0;
        public static final int STATUS_FAIL = 100;
        public static final int STATUS_FAIL_SIGN = 110;
        public static final int STATUS_CHALLENGE = 200;
    }
}
