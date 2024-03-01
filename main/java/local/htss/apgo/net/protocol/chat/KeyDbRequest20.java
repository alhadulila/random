package local.htss.apgo.net.protocol.chat;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class KeyDbRequest20 extends ProtocolFrame {
    //sha512 of key
    private byte[] fingerprint;
    public KeyDbRequest20(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(getFingerprint(), 0, 64);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        fingerprint = new byte[64];
        dataInputStream.readFully(fingerprint);
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }
}
