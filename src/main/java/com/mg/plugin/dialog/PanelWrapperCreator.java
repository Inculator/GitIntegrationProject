package com.mg.plugin.dialog;

import javax.swing.*;
import java.awt.*;

public class PanelWrapperCreator {

    private JPanel panelWrapper;

    public PanelWrapperCreator() {
        panelWrapper = new JPanel();
        panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
        panelWrapper.add(Box.createHorizontalStrut(20));
    }

    public JPanel getPanelWrapper() {
        return panelWrapper;
    }

    public void addComponentToPanelWrapper(Component component){
        panelWrapper.add(component);
    }
}
