package com.mg.plugin.dialog;

import com.mg.git.merge.MergeRequestModel;
import com.mg.mergerequest.GitLabDiscussionsModel;
import com.mg.mergerequest.GitLabUserNotesModel;
import com.mg.plugin.ProvideSwingComponentsUtilsKt;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscussionPanel {

    private String selectedValue;
    private List<MergeRequestModel> myMergeRequestModelList;
    private JPanel panelWrapper;
    private List<GitLabDiscussionsModel> gitLabUserDiscussionsModelList;

    public DiscussionPanel(JPanel panelWrapper, String selectedValue, List<MergeRequestModel> myMergeRequestModelList) {
        this.panelWrapper = panelWrapper;
        this.selectedValue = selectedValue;
        this.myMergeRequestModelList = myMergeRequestModelList;
        List<Component> userRemovalComponent = new ArrayList<>();
        for (Component comp : panelWrapper.getComponents())
            if ("DiscussionPanelTable".equalsIgnoreCase(comp.getName()) || "DiscussionLabel".equalsIgnoreCase(comp.getName()))
                userRemovalComponent.add(comp);
        userRemovalComponent.forEach(comp -> panelWrapper.remove(comp));
        gitLabUserDiscussionsModelList = new ArrayList<>();
        panelWrapper.updateUI();
    }

    public void createDiscussionPanel() {
        final JLabel discussionLabel = makeJLabel();
        panelWrapper.add(discussionLabel);
        Optional<MergeRequestModel> mergeRequestModelOptional =
                myMergeRequestModelList.stream().filter(model -> model.getMergeRequest().getTitle().equalsIgnoreCase(selectedValue)).findFirst();
        if (mergeRequestModelOptional.isPresent()) {
            gitLabUserDiscussionsModelList = mergeRequestModelOptional.get().getListOfMRDiscussions();
            if (!gitLabUserDiscussionsModelList.isEmpty())
                new DiscussionTableDialog().addComponent(panelWrapper, gitLabUserDiscussionsModelList);
            else
                panelWrapper.add(new JLabel("There is no discussion on this merge request !!!"));
        }
        panelWrapper.updateUI();
    }

    @NotNull
    private JLabel makeJLabel() {
        return ProvideSwingComponentsUtilsKt.makeJLabelComponent("Discussions on Merge Request", "DiscussionLabel");
    }

}
