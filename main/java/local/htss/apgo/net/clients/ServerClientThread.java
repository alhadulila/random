package local.htss.apgo.net.clients;

import local.htss.apgo.lib.Server;
import local.htss.apgo.net.protocol.handshake.Frame00PublicKey;
import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

public class ServerClientThread extends NetworkThread {
    private HashSet<Class<?extends ProtocolFrame>> allowedFrames;

    public ServerClientThread() {
        super();
        allowedFrames = new HashSet<>();
        //allowedFrames.add(EncryptionBegin01.class);
        setNetworkRsa(Server.getServer().getGlobalKp());
    }

    public ServerClientThread(Socket socket) throws IOException {
        this();
        setSocket(socket);
        setInput(new DataInputStream(socket.getInputStream()));
        setOutput(new DataOutputStream(socket.getOutputStream()));
    }

    @Override
    public void handleIntermediate(ProtocolFrame protocolFrame) throws IOException {
        protocolFrame.processServer();
    }

    @Override
    public void handleDisconnect(long error) {
        try {
            getSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setExec(false);
    }

    @Override
    public void handleConnected() throws IOException {
        Frame00PublicKey frame00PublicKey = new Frame00PublicKey(this);
        frame00PublicKey.setPublicKey(getNetworkRsa().getPublic());
        dispatchPacketOut(frame00PublicKey);
    }

    public void setAllowedFrames(HashSet<Class<? extends ProtocolFrame>> allowedFrames) {
        this.allowedFrames = allowedFrames;
    }

    public HashSet<Class<? extends ProtocolFrame>> getAllowedFrames() {
        return allowedFrames;
    }
}
