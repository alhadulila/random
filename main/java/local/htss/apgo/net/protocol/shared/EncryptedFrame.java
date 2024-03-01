package local.htss.apgo.net.protocol.shared;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.lib.crypto.AES;
import local.htss.apgo.net.protocol.ProtocolUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@FrameMeta.TransportPolicy()
@FrameMeta.Identifiers(prefix = 1000)

public class EncryptedFrame extends ProtocolFrame {
    private ProtocolFrame innerFrame;
    private byte[] iv;
    public EncryptedFrame(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream encDos = new DataOutputStream(byteArrayOutputStream);
        encDos.writeLong(
                ProtocolUtils.getIds(innerFrame.getClass()).prefix()
        );
        innerFrame.encode(encDos);
        encDos.close();
        byte[] encrypted;
        try {
            encrypted = AES.crypt(true, getNetworkThread().getNetworkAes(), new IvParameterSpec(iv), byteArrayOutputStream.toByteArray());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        byteArrayOutputStream.close();
        dataOutputStream.write(iv, 0, 16);
        dataOutputStream.writeInt(encrypted.length);
        dataOutputStream.write(encrypted);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        iv = new byte[16];
        dataInputStream.readFully(iv);
        byte[] enc = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(enc);
        byte[] dec;
        try {
            dec = AES.crypt(false, getNetworkThread().getNetworkAes(), new IvParameterSpec(iv), enc);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dec);
        DataInputStream decDis = new DataInputStream(byteArrayInputStream);
        innerFrame = ProtocolUtils.decodeIntermediate(decDis, getNetworkThread());
        decDis.close();
        byteArrayInputStream.close();
    }

    @Override
    public void processClient() {
        try {
            getNetworkThread().handleIntermediate(innerFrame);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processServer() throws IOException {
        getNetworkThread().handleIntermediate(innerFrame);
    }

    public ProtocolFrame getInnerFrame() {
        return innerFrame;
    }

    public void setInnerFrame(ProtocolFrame innerFrame) {
        this.innerFrame = innerFrame;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
}
