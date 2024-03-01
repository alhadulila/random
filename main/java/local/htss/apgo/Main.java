package local.htss.apgo;

import local.htss.apgo.lib.Server;
import local.htss.apgo.lib.client.Client;
import local.htss.apgo.net.protocol.data.TextChannel;
import local.htss.apgo.net.protocol.data.TextChannelPermissionAttachement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, SQLException, URISyntaxException {
        if (args.length > 0 && args[0].contentEquals("client")) {
            new Client();
        } else if (args.length > 0 && args[0].contentEquals("test")) {
            Configuration config = new Configuration();
            config.addAnnotatedClass(TextChannel.class);
            config.addAnnotatedClass(TextChannelPermissionAttachement.class);
            config.configure("hibernate.cfg.xml");
            // local SessionFactory bean created
            SessionFactory sessionFactory = config.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession();
        } else {
            Server server = new Server();
            Server.setServer(server);
            server.exec();
        }
    }
}