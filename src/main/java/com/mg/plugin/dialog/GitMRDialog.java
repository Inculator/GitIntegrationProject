package com.mg.plugin.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.messages.MessageDialog;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GitMRDialog extends MessageDialog {

    private JPanel panelWrapper;
    private static String[] optionsUser = {};

    public GitMRDialog(@Nullable Project project, boolean canBeParent) {
        super(project, "", "", optionsUser, 1, null, canBeParent);
    }

    @Override
    protected JComponent createCenterPanel() {
        super.createCenterPanel();
        panelWrapper = new JPanel();
        JButton jButton = new JButton("This is my button");
        panelWrapper.add(jButton);
        return panelWrapper;
    }
}
