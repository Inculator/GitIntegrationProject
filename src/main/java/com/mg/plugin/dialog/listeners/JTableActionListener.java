package com.mg.plugin.dialog.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JTableActionListener {

    public static void addActionListener(JTable table) {

        ListSelectionModel select= table.getSelectionModel();
        select.addListSelectionListener(e-> {
            Integer selectedRow =  table.getSelectedRow();
            Integer selectedColumn = table.getSelectedColumn();
            Object selectedValue = table.getValueAt(selectedRow, selectedColumn);
            if (selectedValue instanceof JButton)
                System.out.println("Resolve the discussion");
            else if(selectedColumn == 2)
                System.out.println("Open the file with comment...");
            else
                System.out.println("do nothing");
        });
    }
}
