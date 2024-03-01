package local.htss.apgo.net.protocol.shared;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.net.protocol.ProtocolUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class NetworkData implements SupportCodec {
    private NetworkThread networkThread;

    public NetworkData(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }

    public void encodeIntermediate(DataOutputStream dataOutputStream) throws IOException {
        FrameMeta.Identifiers identifiers = ProtocolUtils.getIds(getClass());
        if(identifiers == null) {
            //reserved id for "NO ID"
            dataOutputStream.writeLong(10);
        } else {
            dataOutputStream.writeLong(identifiers.prefix());
        }
        encode(dataOutputStream);
    }
    public void decodeIntermediate(DataInputStream dataInputStream) throws IOException {
        dataInputStream.readLong(); //ignore, to verify: use decodeIntermediateUnknownType
        decode(dataInputStream);
    }
    public static NetworkData decodeUnknownType(DataInputStream dataInputStream, NetworkThread networkThread) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        long id = dataInputStream.readLong();
        Class<?extends NetworkData> type = (Class<? extends NetworkData>) ProtocolUtils.NetworkRegistry.lookupId(id);
        NetworkData networkData = type.getDeclaredConstructor(NetworkThread.class).newInstance(networkThread);
        networkData.decode(dataInputStream);
        return networkData;
    }
    public abstract void encode(DataOutputStream dataOutputStream) throws IOException;
    public abstract void decode(DataInputStream dataInputStream) throws IOException;

    public NetworkThread getNetworkThread() {
        return networkThread;
    }

    public void setNetworkThread(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }
}
