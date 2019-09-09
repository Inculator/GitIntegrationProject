package com.mg.plugin.dialog;

import com.mg.git.merge.MergeRequestModel;
import com.mg.git.provider.GitLabMergeRequestProvider;
import com.mg.plugin.ProvideSwingComponentsUtilsKt;
import org.gitlab.api.models.GitlabMergeRequest;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiscussionPanel {

    private String selectedValue;
    private MergeRequestModel myMergeRequestModel;
    private JPanel panelWrapper;
    private GitMRDialog gitMRDialog;

    public DiscussionPanel(JPanel panelWrapper, String selectedValue, GitlabMergeRequest mergeRequestForSelectedBranch, GitMRDialog gitMRDialog) {
        this.panelWrapper = panelWrapper;
        this.selectedValue = selectedValue;
        this.myMergeRequestModel = new GitLabMergeRequestProvider().getMergeRequestsWithDiscussions(mergeRequestForSelectedBranch);
        this.gitMRDialog = gitMRDialog;
        List<Component> userRemovalComponent = new ArrayList<>();
        for (Component comp : panelWrapper.getComponents())
            if ("DiscussionPanelTable".equalsIgnoreCase(comp.getName()) || "DiscussionLabel".equalsIgnoreCase(comp.getName())
            || "NoDiscussionLabel".equalsIgnoreCase(comp.getName()))
                userRemovalComponent.add(comp);
        userRemovalComponent.forEach(comp -> panelWrapper.remove(comp));
        panelWrapper.updateUI();
    }

    public void createDiscussionPanel() {
        final JLabel discussionLabel = makeJLabel();
        panelWrapper.add(discussionLabel);
        if (!myMergeRequestModel.getListOfMRDiscussions().isEmpty())
            new DiscussionTableDialog().addComponent(panelWrapper, myMergeRequestModel, gitMRDialog);
        else
            panelWrapper.add(ProvideSwingComponentsUtilsKt.makeJLabelComponent("There is no discussion on this merge request !!!", "NoDiscussionLabel"));
    }

    @NotNull
    private JLabel makeJLabel() {
        return ProvideSwingComponentsUtilsKt.makeJLabelComponent("Discussions on Merge Request", "DiscussionLabel");
    }

}
