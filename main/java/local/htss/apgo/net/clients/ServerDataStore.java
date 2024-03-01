package local.htss.apgo.net.clients;

import local.htss.apgo.net.protocol.data.TextChannel;
import local.htss.apgo.net.protocol.data.TextChannelPermissionAttachement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ServerDataStore {
    private Session session;
    public ServerDataStore() {
        Configuration config = new Configuration();
        config.addAnnotatedClass(TextChannel.class);
        config.addAnnotatedClass(TextChannelPermissionAttachement.class);
        config.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
