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
@Table(name = "channelPermissions")
public class TextChannelPermissionAttachement extends NetworkData {
    //example: !apgo/announcements
    @Id
    @Column(name = "channelId", nullable = false, length = 128)
    private String channelId;
    @Id
    @Column(name = "userHash", length = 512, nullable = false)
    private byte[] userHash;
    @Column(name = "flags0")
    private long perms0;
    public TextChannelPermissionAttachement(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(channelId);
        dataOutputStream.write(userHash, 0, 512);
        dataOutputStream.writeLong(perms0);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        channelId = dataInputStream.readUTF();
        userHash = new byte[512];
        dataInputStream.readFully(userHash);
        perms0 = dataInputStream.readLong();
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public byte[] getUserHash() {
        return userHash;
    }

    public void setUserHash(byte[] userHash) {
        this.userHash = userHash;
    }

    public long getPerms0() {
        return perms0;
    }

    public void setPerms0(long perms0) {
        this.perms0 = perms0;
    }

    public static class Permissions {
        //member of a channel
        public static final long MEMBERSHIP = 1 << 1;
        public static final long READ_DIRECT = 1 << 2;
    }
}
