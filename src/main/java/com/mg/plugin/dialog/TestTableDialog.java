package com.mg.plugin.dialog;

import com.intellij.ui.table.JBTable;
import com.mg.mergerequest.DiscussionPositionModel;
import com.mg.mergerequest.GitLabDiscussionsModel;
import com.mg.mergerequest.GitLabUserNotesModel;
import com.mg.plugin.dialog.listeners.ButtonRenderer;
import com.mg.plugin.dialog.listeners.JTableActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TestTableDialog {

    public static void addComponent(JPanel panel, List<GitLabUserNotesModel> discussionList) {
        JButton button = new JButton("Resolve Discussion");
        Object[] columnNames = {"Comment", "File", "Resolve"};
        Object[][] data = new Object[discussionList.size()][];
        for(int i = 0 ; i < discussionList.size() ; i++){
            Object discussion = discussionList.get(i);
            Object newPath = discussionList.get(i).getPosition().getNew_path();
            Object[] o = {discussion, newPath, button};
            data[i] = o;
        }
        JTable table = new JBTable(new DefaultTableModel(data, columnNames));
        table.getColumn("Resolve").setCellRenderer(new ButtonRenderer());
        table.setAutoResizeMode(4);
        JTableActionListener.addActionListener(table);
        panel.add(table);
    }
}
