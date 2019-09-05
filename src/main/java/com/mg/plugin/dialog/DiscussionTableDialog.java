package com.mg.plugin.dialog;

import com.intellij.ui.table.JBTable;
import com.mg.mergerequest.GitLabDiscussionsModel;
import com.mg.plugin.dialog.listeners.ButtonRenderer;
import com.mg.plugin.dialog.listeners.JTableActionListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DiscussionTableDialog {

    public void addComponent(JPanel panel, List<GitLabDiscussionsModel> discussionList) {
        JButton button = new JButton("Resolve Discussion");
        Object[] columnNames = {"Comment", "File", "Resolve"};

        ArrayList<Integer> sizeOfNotes = new ArrayList<>();
        discussionList.forEach(discussion -> sizeOfNotes.add(discussion.getNotes().size()));
        Object[][] data = new Object[sizeOfNotes.size()][];
        Integer countOfNotes = 0;

        for (int i = 0; i < discussionList.size(); i++) {
            for(int j = 0; j < discussionList.get(i).getNotes().size(); j++) {
                Object discussion = discussionList.get(i).getNotes().get(j);
                Object position = discussionList.get(i).getNotes().get(j).getPosition();
                Object[] o = {discussion, position, button};
                data[countOfNotes++] = o;
            }
        }

        JTable table = makeJTable(columnNames, data);
        panel.add(table);
    }

    @NotNull
    private JTable makeJTable(Object[] columnNames, Object[][] data) {
        JTable table = new JBTable(new DefaultTableModel(data, columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setName("DiscussionPanelTable");
        table.getColumn("Resolve").setCellRenderer(new ButtonRenderer());
        table.setAutoResizeMode(4);
        new JTableActionListener().addActionListener(table);
        return table;
    }
}
