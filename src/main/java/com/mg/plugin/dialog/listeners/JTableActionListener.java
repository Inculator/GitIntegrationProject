package com.mg.plugin.dialog.listeners;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mg.git.connection.ConnectionGitlabAPI;
import com.mg.git.discussion.ResolveMRDiscussionsController;
import com.mg.git.merge.MergeRequestModel;
import com.mg.mergerequest.GitLabUserNotesModel;
import com.mg.plugin.dialog.GitMRDialog;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JTableActionListener {

    private MergeRequestModel mergeRequestModel;
    private GitMRDialog gitMRDialog;

    public void addActionListener(JTable table, MergeRequestModel mergeRequestModel, GitMRDialog gitMRDialog) {
        this.mergeRequestModel = mergeRequestModel;
        this.gitMRDialog = gitMRDialog;
        addTableMouseListener(table);
    }

    private void addTableMouseListener(JTable table) {
        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Integer selectedRow = table.getSelectedRow();
                Integer selectedColumn = table.getSelectedColumn();
                Object selectedValue = table.getValueAt(selectedRow, selectedColumn);

                if (selectedColumn == 1)
                    openFileWithComment(table, selectedRow);
                if (selectedValue instanceof JButton)
                    resolveDiscussion(table.getValueAt(selectedRow, 0));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Do Nothing
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Do Nothing
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Do Nothing
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Do Nothing̥
            }
        });
    }

    private void resolveDiscussion(Object discussionInstance) {
        Integer projectId = ConnectionGitlabAPI.Companion.getProjectId();
        Boolean isResolved = new ResolveMRDiscussionsController().resolveDiscussion(((GitLabUserNotesModel) discussionInstance).getDiscussionId(), projectId, mergeRequestModel.getMergeRequest(), true);
        if (isResolved) gitMRDialog.getDisposable().dispose();
    }

    private void openFileWithComment(JTable table, Integer selectedRow) {
        GitLabUserNotesModel model = (GitLabUserNotesModel) table.getValueAt(selectedRow, 0);
        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(getFilePathToNavigate(model));
        new OpenFileDescriptor(GitMRDialog.project, file, getLogicalLine(model), 0).navigate(true);
    }

    private String getFilePathToNavigate(GitLabUserNotesModel model) {
        return GitMRDialog.project.getBasePath() + "/" + model.getPosition().getNew_path();
    }

    private Integer getLogicalLine(GitLabUserNotesModel model) {
        return Integer.parseInt(model.getPosition().getNew_line());
    }
}
