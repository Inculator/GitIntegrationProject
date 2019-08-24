package com.mg.plugin.dialog;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mg.git.merge.MergeRequestModel;
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
    private DefaultListModel<String> discussionModelList;
    private List<GitLabUserNotesModel> gitLabUserNotesModelList;

    public DiscussionPanel(JPanel panelWrapper, String selectedValue, List<MergeRequestModel> myMergeRequestModelList) {
        this.panelWrapper = panelWrapper;
        this.selectedValue = selectedValue;
        this.myMergeRequestModelList = myMergeRequestModelList;
        List<Component> userRemovalComponent = new ArrayList<>();
        for (Component comp : panelWrapper.getComponents())
            if ("DiscussionList".equalsIgnoreCase(comp.getName()) || "DiscussionLabel".equalsIgnoreCase(comp.getName()))
                userRemovalComponent.add(comp);
        userRemovalComponent.forEach(comp -> panelWrapper.remove(comp));
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
            gitLabUserNotesModelList.forEach(ele -> discussionModelList.addElement(ele.getBody() + " : File -> " + ele.getPosition().getOld_path()));
        else
            discussionModelList.addElement("There is no discussion done on this merge request yet !!");
        final JLabel discussionLabel = makeJLabel();
        final JList discussionsJList = makeJListForDiscussion();
        addListActionListener(discussionsJList);
        panelWrapper.add(discussionLabel);
        panelWrapper.add(discussionsJList);
        panelWrapper.updateUI();
    }

    private void addListActionListener(JList<String> discussionsJList) {
        discussionsJList.addListSelectionListener(l -> {
                    VirtualFile file = LocalFileSystem.getInstance().findFileByPath(getFilePathToNavigate(discussionsJList));
                    new OpenFileDescriptor(GitMRDialog.project, file, getLogicalLine(discussionsJList), 0).navigate(true);
                }
        );
    }

    private Integer getLogicalLine(JList<String> discussionsJList) {
        return Integer.parseInt(gitLabUserNotesModelList.get(discussionsJList.getSelectedIndex()).getPosition().getNew_line());
    }

    @NotNull
    private String getFilePathToNavigate(JList<String> discussionsJList) {
        return GitMRDialog.project.getBasePath() + "/" + gitLabUserNotesModelList.get(discussionsJList.getSelectedIndex()).getPosition().getNew_path();
    }

    @NotNull
    private JLabel makeJLabel() {
        return ProvideSwingComponentsUtilsKt.makeJLabelComponent("Discussions on Merge Request", "DiscussionLabel");
    }

    private JList makeJListForDiscussion() {
        return ProvideSwingComponentsUtilsKt.makeJListComponent(discussionModelList, "DiscussionList");
    }
}
