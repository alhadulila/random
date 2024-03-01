package local.htss.apgo.net.clients;

import local.htss.apgo.net.protocol.shared.EncryptedFrame;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;
import local.htss.apgo.lib.crypto.AES;
import local.htss.apgo.net.protocol.ProtocolUtils;

import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.HashMap;

public abstract class NetworkThread implements Runnable {

    private boolean exec = true;
    private DataOutputStream output;
    private DataInputStream input;
    private Socket socket;
    private HashMap<String, Object> customData;
    private KeyPair networkRsa;
    private SecretKey networkAes;

    public NetworkThread() {
        customData = new HashMap<>();
        exec = true;
    }
    public NetworkThread(Socket socket) throws IOException {
        this();
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            handleConnected();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (isExec()) {
            try {
                System.out.println("received frame");
                ProtocolFrame protocolFrame = ProtocolUtils.decodeIntermediate(getInput(), this);
                if(protocolFrame == null) {
                    handleDisconnect(1);
                    System.out.println("decode intermediate/err");
                    break;
                }
                System.out.println("received frame/d " + protocolFrame);
                handleIntermediate(protocolFrame);
            } catch (IOException e) {
                //TODO handle errors to log
                e.printStackTrace();
                handleDisconnect(1);
                break;
            }
        }
        setExec(false);
    }

    public void handleConnected() throws IOException {

    }

    public void dispatchPacketOut(ProtocolFrame protocolFrame) throws IOException {
        if(ProtocolUtils.encryptOutcome(protocolFrame.getClass())) {
            EncryptedFrame encryptedFrame = new EncryptedFrame(this);
            encryptedFrame.setInnerFrame(protocolFrame);
            encryptedFrame.setIv(
                    AES.createIv().getIV()
            );
            encryptedFrame.encodeIntermediate(getOutput());
            System.out.println(encryptedFrame);
        } else {
            protocolFrame.encodeIntermediate(getOutput());
            System.out.println(protocolFrame);
        }
    }

    public abstract void handleIntermediate(ProtocolFrame protocolFrame) throws IOException;

    public boolean isExec() {
        return exec;
    }

    public abstract void handleDisconnect(long error);

    public void setExec(boolean exec) {
        this.exec = exec;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public HashMap<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(HashMap<String, Object> customData) {
        this.customData = customData;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public KeyPair getNetworkRsa() {
        return networkRsa;
    }

    public void setNetworkRsa(KeyPair networkRsa) {
        this.networkRsa = networkRsa;
    }

    public SecretKey getNetworkAes() {
        return networkAes;
    }

    public void setNetworkAes(SecretKey networkAes) {
        this.networkAes = networkAes;
    }
}
