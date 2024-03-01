package local.htss.apgo.net.protocol.auth;

import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.data.NetworkPublicKey;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class LoginPacket24 extends ProtocolFrame {
    public static final String LOGIN_CHALLENGE_PENDING_LIST = "loginChallengeList";
    private NetworkPublicKey account;
    private byte[] signature;
    private boolean login;
    //0 for none
    private transient int challenge = 0;
    private byte[] challengeData;
    public LoginPacket24(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        account.encode(dataOutputStream);
        dataOutputStream.write(signature, 0, 512);
        dataOutputStream.writeBoolean(login);
        if(challenge != 0) {
            dataOutputStream.writeInt(challenge);
            dataOutputStream.writeInt(challengeData.length);
            dataOutputStream.write(challengeData);
        }
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        account = new NetworkPublicKey(getNetworkThread());
        account.decode(dataInputStream);
        signature = new byte[512];
        dataInputStream.readFully(signature);
        login = dataInputStream.readBoolean();
    }

    @Override
    public void processServer() throws IOException {
        if(login) {

        }
    }

    public void serverCreateChallengeMap() {
        getNetworkThread().getCustomData().put(LOGIN_CHALLENGE_PENDING_LIST, new HashMap<String, String>());
    }

    public boolean challengeMapExists() {
        return getNetworkThread().getCustomData().containsKey(LOGIN_CHALLENGE_PENDING_LIST);
    }

    public NetworkPublicKey getAccount() {
        return account;
    }

    public void setAccount(NetworkPublicKey account) {
        this.account = account;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public int getChallenge() {
        return challenge;
    }

    public void setChallenge(int challenge) {
        this.challenge = challenge;
    }

    public byte[] getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(byte[] challengeData) {
        this.challengeData = challengeData;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
