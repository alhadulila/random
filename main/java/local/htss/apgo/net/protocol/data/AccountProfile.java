package local.htss.apgo.net.protocol.data;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.NetworkData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PublicKey;

public class AccountProfile extends NetworkData {
    private String displayName;
    private PublicKey publicKey;
    private char[] rankId;
    private long permissions;
    private long flags;
    private boolean full;

    public AccountProfile(NetworkThread networkThread) {
        super(networkThread);
    }


    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {

    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public char[] getRankId() {
        return rankId;
    }

    public void setRankId(char[] rankId) {
        this.rankId = rankId;
    }

    public long getPermissions() {
        return permissions;
    }

    public void setPermissions(long permissions) {
        this.permissions = permissions;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(long flags) {
        this.flags = flags;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public static class Flags {
        public static final long SYSTEM_USER = 1 << 1;
        public static final long LOGIN_ENABLED = 1 << 2;
        public static final long DELETE_ENABLED = 1 << 3;
        public static final long DELETE_FULL = 1 << 4;
    }
}
