package local.htss.apgo.lib;

import local.htss.apgo.net.clients.ServerClientThread;
import local.htss.apgo.lib.crypto.RSA;
import local.htss.apgo.net.clients.ServerDataStore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private static Server server;
    private ServerSocket socket;
    private KeyPair globalKp;
    private boolean exec;
    private ServerDataStore serverDataStore;
    private ArrayList<ServerClientThread> clients;
    public Server() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        exec = true;
        socket = new ServerSocket(25599);
        clients = new ArrayList<>();
        serverDataStore = new ServerDataStore();
        File kp = new File("srv_kp.bin");
        if(!kp.exists()) {
            globalKp = RSA.gen(4096);
            kp.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(kp);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            dataOutputStream.writeInt(globalKp.getPublic().getEncoded().length);
            dataOutputStream.write(globalKp.getPublic().getEncoded());
            dataOutputStream.writeInt(globalKp.getPrivate().getEncoded().length);
            dataOutputStream.write(globalKp.getPrivate().getEncoded());
            dataOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Generated key because it was not found");
        } else {
            FileInputStream fileInputStream = new FileInputStream(kp);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            byte[] pbk = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(pbk);
            byte[] pvk = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(pvk);
            dataInputStream.close();
            fileInputStream.close();
            globalKp = RSA.joinKeys(
                    (PrivateKey) RSA.loadKey(pvk, false),
                    (PublicKey) RSA.loadKey(pbk, true)
            );
        }
    }

    public void exec() throws IOException {
        while (isExec()) {
            Socket client = socket.accept();
            ServerClientThread serverClientThread = new ServerClientThread(client);
            int i = clients.size();
            clients.add(i, serverClientThread);
            Thread thread = new Thread(getClients().get(i));
            thread.start();
        }
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        Server.server = server;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public boolean isExec() {
        return exec;
    }

    public void setExec(boolean exec) {
        this.exec = exec;
    }

    public ArrayList<ServerClientThread> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ServerClientThread> clients) {
        this.clients = clients;
    }

    public KeyPair getGlobalKp() {
        return globalKp;
    }

    public void setGlobalKp(KeyPair globalKp) {
        this.globalKp = globalKp;
    }

    public ServerDataStore getDataStore() {
        return serverDataStore;
    }

    public void setDataStore(ServerDataStore serverDataStore) {
        this.serverDataStore = serverDataStore;
    }
}
