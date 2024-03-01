package local.htss.apgo.net.protocol.handshake;

import local.htss.apgo.lib.client.Client;
import local.htss.apgo.lib.crypto.RSA;
import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@FrameMeta.Identifiers(prefix = 0)
@FrameMeta.TransportPolicy()
public class Frame00PublicKey extends ProtocolFrame {
    private PublicKey publicKey;
    public Frame00PublicKey(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(publicKey.getEncoded().length);
        dataOutputStream.write(publicKey.getEncoded());
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        byte[] bytes = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bytes);
        try {
            publicKey = (PublicKey) RSA.loadKey(bytes, true);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processClient() throws IOException {
        Client.getClient().log("Received public key");
        Frame01Aes frame01Aes = new Frame01Aes(getNetworkThread());
        getNetworkThread().dispatchPacketOut(frame01Aes);
        getNetworkThread().setNetworkAes(frame01Aes.getSecretKey());
        Client.getClient().log("Sending session key");
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
