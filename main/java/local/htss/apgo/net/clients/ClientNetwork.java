package local.htss.apgo.net.clients;

import local.htss.apgo.net.protocol.shared.ProtocolFrame;

import java.io.IOException;
import java.net.Socket;

public class ClientNetwork extends NetworkThread {
    public ClientNetwork() {
        super();
    }

    public ClientNetwork(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public void handleIntermediate(ProtocolFrame protocolFrame) throws IOException {
        protocolFrame.processClient();
    }

    @Override
    public void handleDisconnect(long error) {

    }
}
