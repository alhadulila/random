package local.htss.apgo.lib.client;

import local.htss.apgo.lib.BuildProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StatusBar {
    public static final String ID_LOG = "statusBarLog";
    public static final String ID_CONNECT_BUTTON = "statusBarConnect";
    public static final String ID_CONNECTION_INFO = "statusBarConnectionInfo";
    public static final String ID_STATUS_BAR = "statusBarPanel";
    private Panel panel;

    public StatusBar() {

    }

    public void init() {
        panel = GuiUtil.defaultPanel(true, false, BuildProperties.DEBUG_MODE, "");
        panel.setName(ID_STATUS_BAR);
        JLabel statusLog = new JLabel(Client.getClient().getLang().getTranslation("status.log.empty"));
        statusLog.setName(ID_LOG);
        GuiUtil.addComponent(statusLog, panel, "span 2 2", false);

        JButton connectButton = new JButton(Client.getClient().getLang().getTranslation("status.connect.button"));
        connectButton.setName(ID_CONNECT_BUTTON);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.remove(connectButton);
                try {
                    Client.getClient().connectApgo();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GuiUtil.addComponent(connectButton, panel, "align right", false);
    }

    public void setMessage(String message) {
        getLog().setText(message);
    }

    public void addSelf(Container panel) {
        GuiUtil.addComponent(getPanel(), panel, "dock south", true);
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public JLabel getLog() {
        return (JLabel) GuiUtil.getComponentByName(ID_LOG, panel);
    }
    public JButton getConnectButton() {
        return (JButton) GuiUtil.getComponentByName(ID_CONNECT_BUTTON, panel);
    }
    public JPanel getConnectionInfo() {
        return (JPanel) GuiUtil.getComponentByName(ID_CONNECTION_INFO, panel);
    }
}
