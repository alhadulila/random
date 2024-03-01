package local.htss.apgo.net.protocol.handshake;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.net.protocol.auth.AuthInitPacket05;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;
import local.htss.apgo.lib.crypto.AES;

import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@FrameMeta.Identifiers(prefix = 1)
@FrameMeta.TransportPolicy
public class Frame01Aes extends ProtocolFrame {
    private SecretKey secretKey;
    public Frame01Aes(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        if(secretKey == null) {
            try {
                secretKey = AES.createKey();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        dataOutputStream.writeInt(secretKey.getEncoded().length);
        dataOutputStream.write(secretKey.getEncoded());
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        byte[] bytes = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bytes);
        secretKey = AES.load(bytes);
    }

    @Override
    public void processServer() throws IOException {
        getNetworkThread().setNetworkAes(secretKey);
        AuthInitPacket05 authInitPacket05 = new AuthInitPacket05(getNetworkThread());
        authInitPacket05.init();
        getNetworkThread().dispatchPacketOut(authInitPacket05);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
