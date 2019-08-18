package com.mg.plugin.dialog;

import com.mg.git.merge.MergeRequestModel;
import com.mg.mergerequest.GitLabUserNotesModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscussionPanel {

    private String selectedValue;
    private List<MergeRequestModel> myMergeRequestModelList;
    private JPanel panelWrapper;
    private DefaultListModel<String> discussionModelList;
    private List<GitLabUserNotesModel> gitLabUserNotesModelList;

    public DiscussionPanel(JPanel panelWrapper, String selectedValue, List<MergeRequestModel> myMergeRequestModelList) {
        this.panelWrapper = panelWrapper;
        this.selectedValue = selectedValue;
        this.myMergeRequestModelList = myMergeRequestModelList;
        List<Component> userRemovalComponent = new ArrayList<>();
        for (Component comp : panelWrapper.getComponents())
            if ("DiscussionList".equalsIgnoreCase(comp.getName()))
                userRemovalComponent.add(comp);
        userRemovalComponent.forEach(comp-> panelWrapper.remove(comp));
        discussionModelList = new DefaultListModel<>();
        gitLabUserNotesModelList = new ArrayList<>();
        panelWrapper.updateUI();
    }

    public void createDiscussionPanel() {
        Optional<MergeRequestModel> mergeRequestModelOptional =
                myMergeRequestModelList.stream().filter(model -> model.getMergeRequest().getTitle().equalsIgnoreCase(selectedValue)).findFirst();
        if (mergeRequestModelOptional.isPresent())
            gitLabUserNotesModelList = mergeRequestModelOptional.get().getListOfMRDiscussions();
        if (!gitLabUserNotesModelList.isEmpty())
            gitLabUserNotesModelList.forEach(ele -> discussionModelList.addElement(ele.getBody()));
        else
            discussionModelList.addElement("There is no discussion done on this merge request yet !!");
        final JList jList = new JList<>(discussionModelList);
        jList.setName("DiscussionList");
        panelWrapper.add(jList);
        panelWrapper.updateUI();
    }
}
