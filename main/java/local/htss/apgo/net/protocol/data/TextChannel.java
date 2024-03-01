package local.htss.apgo.net.protocol.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.NetworkData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Entity
@Table(name = "textChannels")
public class TextChannel extends NetworkData {
    @Column(name = "pbk")
    private NetworkPublicKey networkPublicKey;
    @Id
    @Column(name = "channelIdentifier", nullable = false, unique = true)
    private String id;
    @Id
    @Column(name = "channelDisplay", nullable = false)
    private String display;
    @Column(name = "flags0")
    private long flags0 = 0;

    public TextChannel(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(id);
        dataOutputStream.writeUTF(display);
        dataOutputStream.writeLong(flags0);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        id = dataInputStream.readUTF();
        display = dataInputStream.readUTF();
        flags0 = dataInputStream.readLong();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public long getFlags0() {
        return flags0;
    }

    public void setFlags0(long flags0) {
        this.flags0 = flags0;
    }

    public NetworkPublicKey getNetworkPublicKey() {
        return networkPublicKey;
    }

    public void setNetworkPublicKey(NetworkPublicKey networkPublicKey) {
        this.networkPublicKey = networkPublicKey;
    }

    public static class Flags {
        public static final long CAN_LEAVE = 1 << 10;
    }
}
