package local.htss.apgo.net.protocol.chat;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.FrameMeta;
import local.htss.apgo.net.protocol.data.AccountProfile;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;
import local.htss.apgo.net.protocol.ErrorCodes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@FrameMeta.Identifiers(prefix = 21)
public class KeyDbPartial21 extends ProtocolFrame {
    private AccountProfile accountProfile;
    private String code;
    public KeyDbPartial21(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(code);
        accountProfile.encode(dataOutputStream);
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        code = dataInputStream.readUTF();
        if (code.contentEquals(ErrorCodes.KeySearchCodes.DENIED) || code.contentEquals(ErrorCodes.KeySearchCodes.INVALID)) {
            accountProfile = null;
        } else {
            accountProfile = new AccountProfile(getNetworkThread());
            accountProfile.decode(dataInputStream);
        }
    }
    public AccountProfile getAccountProfile() {
        return accountProfile;
    }

    public void setAccountProfile(AccountProfile accountProfile) {
        this.accountProfile = accountProfile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
