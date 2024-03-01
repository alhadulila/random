package local.htss.apgo.lib.client;

import local.htss.apgo.lib.BuildProperties;

import javax.swing.*;
import java.awt.*;

public class AuthForm {
    public static final String ID_LOGIN_BUTTON = "authExistingAccount";
    public static final String ID_REGISTER_BUTTON = "authNewAccount";
    public static final String ID_AUTH_FORM = "authFormPanel";
    private Panel panel;

    public AuthForm() {

    }

    public void init() {
        panel = GuiUtil.defaultPanel(true, false, BuildProperties.DEBUG_MODE, "");
        panel.setName(ID_AUTH_FORM);

        JButton register = new JButton(Client.getClient().getLang().getTranslation("auth.form.register.button"));
        register.setName(ID_REGISTER_BUTTON);
        GuiUtil.addComponent(register, panel, "wrap", false);

        JButton login = new JButton(Client.getClient().getLang().getTranslation("auth.form.login.button"));
        register.setName(ID_LOGIN_BUTTON);
        GuiUtil.addComponent(register, panel, "wrap", true);
    }

    public void addSelf() {
        Client.getClient().getCentral().removeAll();
        GuiUtil.addComponent(getPanel(), Client.getClient().getCentral(), "", true);
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}
