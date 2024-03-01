package local.htss.apgo.net.protocol.auth;

import local.htss.apgo.lib.client.AuthForm;
import local.htss.apgo.lib.client.Client;
import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

@FrameMeta.Identifiers(prefix = 5)
@FrameMeta.TransportPolicy(encrypt = true)
public class AuthInitPacket05 extends ProtocolFrame {
    public static final String LOGIN_NONCE_ID = "auth_init_nonce";
    public static final int SIG_SIZE = 512;
    public static final int NONCE_SIZE = 512;
    private byte[] nonce;

    public AuthInitPacket05(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(nonce, 0, NONCE_SIZE);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        nonce = new byte[NONCE_SIZE];
        dataInputStream.readFully(nonce);
    }

    public void init() {
        nonce = new byte[NONCE_SIZE];
        new SecureRandom().nextBytes(nonce);
        getNetworkThread().getCustomData().put(LOGIN_NONCE_ID, nonce);
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    @Override
    public void processClient() throws IOException {
        Client.getClient().log("Received authentication request");
        AuthForm authForm = new AuthForm();
        authForm.init();
        authForm.addSelf();
        getNetworkThread().getCustomData().put(LOGIN_NONCE_ID, nonce);

    }
}
