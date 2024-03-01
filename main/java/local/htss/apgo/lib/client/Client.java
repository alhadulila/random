package local.htss.apgo.lib.client;

import local.htss.apgo.lib.lang.Lang;
import local.htss.apgo.net.clients.ClientNetwork;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static Client client;
    private JFrame frame;
    private Panel central;
    private Lang lang;
    private StatusBar statusBar;
    public Client() {
        try {
            lang = new Lang("en", Lang.LangSource.RESSOURCE_DEF);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setClient(this);
        frame = new JFrame();
        frame.setContentPane(new JPanel(new MigLayout("fill, debug")));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() / 2), (int) (screenSize.getHeight() / 2));
        frame.setVisible(true);

        central = new Panel(new MigLayout("fill, debug"));
        frame.getContentPane().add(central);

        statusBar = new StatusBar();
        statusBar.init();
        statusBar.addSelf(getFrame().getContentPane());
    }

    public void connectApgo() throws IOException {
        Socket socket = new Socket("127.0.0.1", 25599);
        new Thread(
                new ClientNetwork(
                        socket
                )
        ).start();
    }

    public void log(String data) {
        getStatusBar().getLog().setText(data);
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Client.client = client;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    public Panel getCentral() {
        return central;
    }

    public void setCentral(Panel central) {
        this.central = central;
    }
}
