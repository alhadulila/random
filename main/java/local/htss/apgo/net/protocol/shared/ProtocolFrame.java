package local.htss.apgo.net.protocol.shared;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ProtocolFrame implements SupportCodec {
    private NetworkThread networkThread;

    public ProtocolFrame(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }

    public abstract void encode(DataOutputStream dataOutputStream) throws IOException;
    public abstract void decode(DataInputStream dataInputStream) throws IOException;
    public void processServer() throws IOException {

    }
    public void processClient() throws IOException {

    }
    public void encodeIntermediate(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeLong(getClass().getAnnotation(FrameMeta.Identifiers.class).prefix());
        encode(dataOutputStream);
    }

    public NetworkThread getNetworkThread() {
        return networkThread;
    }

    public void setNetworkThread(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }
}
