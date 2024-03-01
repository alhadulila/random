package local.htss.apgo.lib.client;

import net.miginfocom.swing.MigLayout;

import java.awt.*;

public class GuiUtil {
    public static void addComponent(Component component, Container panel, String constraints, boolean redraw) {
        panel.add(component, constraints);
        if(redraw) {
            panel.revalidate();
        }
    }
    public static Component getComponentByName(String name, Panel panel) {
        for (Component component : panel.getComponents()) {
            if(component.getName().contentEquals(name)) {
                return component;
            }
        }
        return null;
    }
    public static void removeComponent(Component component, Container container, boolean redraw) {
        container.remove(component);
        if(redraw) {
            container.revalidate();
        }
    }
    public static Panel defaultPanel(boolean fillx, boolean filly, boolean debug, String additionalConstraints) {
        String constraints = "";
        if(fillx) {
            constraints += "fillx, ";
        }
        if (debug) {
            constraints += "debug, ";
        }
        constraints += additionalConstraints;
        return new Panel(new MigLayout(constraints));
    }
}
