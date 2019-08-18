package com.mg.plugin.action.impl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.mg.plugin.dialog.GitMRDialog;

public class MergeRequestPlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        GitMRDialog.project = anActionEvent.getProject();
        GitMRDialog dialog = new GitMRDialog(true);
        dialog.show();
    }
}
