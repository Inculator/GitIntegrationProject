package com.mg.plugin.action.impl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.JBUI;
import com.mg.plugin.dialog.GitMRDialog;

import javax.swing.*;

import static com.intellij.openapi.ui.Messages.CANCEL_BUTTON;
import static com.intellij.openapi.ui.Messages.OK_BUTTON;

public class MergeRequestPlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        GitMRDialog dialog = new GitMRDialog(project, true);
        dialog.show();
    }
}
