package com.mg.plugin.dialog.listeners;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mg.mergerequest.GitLabUserNotesModel;
import com.mg.plugin.dialog.GitMRDialog;

import javax.swing.*;

public class JTableActionListener {

    public void addActionListener(JTable table) {

        ListSelectionModel select = table.getSelectionModel();
        select.addListSelectionListener(e -> {
            Integer selectedRow = table.getSelectedRow();
            Integer selectedColumn = table.getSelectedColumn();
            Object selectedValue = table.getValueAt(selectedRow, selectedColumn);

            if (selectedColumn == 1) {
                GitLabUserNotesModel model = (GitLabUserNotesModel) table.getValueAt(selectedRow, 0);
                VirtualFile file = LocalFileSystem.getInstance().findFileByPath(getFilePathToNavigate(model));
                new OpenFileDescriptor(GitMRDialog.project, file, getLogicalLine(model), 0).navigate(true);
            }
            if (selectedValue instanceof JButton)
                System.out.println("Resolve the discussion");
        });
    }

    private String getFilePathToNavigate(GitLabUserNotesModel model) {
        return GitMRDialog.project.getBasePath() + "/" + model.getPosition().getNew_path();
    }

    private Integer getLogicalLine(GitLabUserNotesModel model) {
        return Integer.parseInt(GitMRDialog.project.getBasePath() + "/" + model.getPosition().getNew_line());
    }
}
