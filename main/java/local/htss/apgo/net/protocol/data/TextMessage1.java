package local.htss.apgo.net.protocol.data;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.NetworkData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TextMessage1 extends NetworkData {
    private AccountProfile author;
    private ArrayList<MessageSessionKey> destKeys;
    private byte[] content;
    private byte[] contentSignature;
    public TextMessage1(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {

    }

    public ArrayList<MessageSessionKey> getDestKeys() {
        return destKeys;
    }

    public void setDestKeys(ArrayList<MessageSessionKey> destKeys) {
        this.destKeys = destKeys;
    }

    public AccountProfile getAuthor() {
        return author;
    }

    public void setAuthor(AccountProfile author) {
        this.author = author;
    }

    public byte[] getContentSignature() {
        return contentSignature;
    }

    public void setContentSignature(byte[] contentSignature) {
        this.contentSignature = contentSignature;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
