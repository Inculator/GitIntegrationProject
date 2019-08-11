package com.mg.plugin.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.messages.MessageDialog;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class GitMRDialog extends MessageDialog {

    private JPanel panelWrapper;

    public GitMRDialog(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        super.createCenterPanel();
        JButton jButton = new JButton("This is my button");
        panelWrapper.add(jButton);
        return panelWrapper;
    }
}
