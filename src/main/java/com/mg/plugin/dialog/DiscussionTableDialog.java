package com.mg.plugin.dialog;

import com.intellij.ui.table.JBTable;
import com.mg.git.merge.MergeRequestModel;
import com.mg.mergerequest.GitLabDiscussionsModel;
import com.mg.mergerequest.GitLabUserNotesModel;
import com.mg.plugin.dialog.listeners.ButtonRenderer;
import com.mg.plugin.dialog.listeners.JTableActionListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiscussionTableDialog {

    private MergeRequestModel mergeRequestModel;
    private GitMRDialog gitMRDialog;

    public void addComponent(JPanel panel, MergeRequestModel mergeRequestModelOptional, GitMRDialog gitMRDialog) {
        JButton button = new JButton("Resolve Discussion");
        Object[] columnNames = {"Comment", "File", "Resolve"};
        mergeRequestModel = mergeRequestModelOptional;
        this.gitMRDialog = gitMRDialog;
        List<GitLabUserNotesModel> notesList = new ArrayList<>();
        List<GitLabDiscussionsModel> discussionList = mergeRequestModelOptional.getListOfMRDiscussions();

        discussionList.forEach(discussion ->
            discussion.getNotes().forEach(note -> {
                if (note.isResolvable())
                    notesList.add(note);
            })
        );

        Object[][] data = new Object[notesList.size()][];
        Integer counterForNotes = 0;

        for (int i = 0; i < discussionList.size(); i++) {
            for (int j = 0; j < discussionList.get(i).getNotes().size(); j++) {
                if (discussionList.get(i).getNotes().get(j).isResolvable()) {
                    Object discussionNotes = discussionList.get(i).getNotes().get(j);
                    Object position = discussionList.get(i).getNotes().get(j).getPosition();
                    Object[] o = {discussionNotes, position, button};
                    data[counterForNotes++] = o;
                }
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
        table.setRowSelectionAllowed(false);
        new JTableActionListener().addActionListener(table, mergeRequestModel, gitMRDialog);
        return table;
    }
}
