package com.mg.plugin.dialog.listeners;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton) value;
        button.setForeground(table.getForeground());
        button.setBackground(UIManager.getColor("Button.background"));
        return button;
    }
}