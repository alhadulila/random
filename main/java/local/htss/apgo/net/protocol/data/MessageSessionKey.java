package local.htss.apgo.net.protocol.data;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.NetworkData;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageSessionKey extends NetworkData {
    private byte[] dst;
    private SecretKey key;
    private IvParameterSpec iv;
    public MessageSessionKey(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {

    }

    public byte[] getDst() {
        return dst;
    }

    public void setDst(byte[] dst) {
        this.dst = dst;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }
}
