package com.mg.plugin.dialog;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
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
    private DefaultListModel<GitLabUserNotesModel> discussionModelList;
    private List<GitLabDiscussionsModel> gitLabUserDiscussionsModelList;

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
        }
        if (!gitLabUserDiscussionsModelList.isEmpty()) {
            TestTableDialog.addComponent(panelWrapper, gitLabUserDiscussionsModelList.get(0).getNotes());
        }
//            gitLabUserDiscussionsModelList.forEach(ele -> ele.getNotes().forEach(note -> discussionModelList.addElement(note)));
        else {
            /*GitLabUserNotesModel model = new GitLabUserNotesModel();
            model.setBody("There is no discussion done on this merge request yet !!");
            discussionModelList.addElement(model);*/
            panelWrapper.add(new JLabel("There is no discussion on this merge request !!!"));
        }
//        final JList discussionsJList = makeJListForDiscussion();
//        addListActionListener(discussionsJList);
//        panelWrapper.add(discussionsJList);
        panelWrapper.updateUI();
    }

  /*  private void addListActionListener(JList<GitLabUserNotesModel> discussionsJList) {
        discussionsJList.addListSelectionListener(l -> {
                    if (!discussionsJList.getSelectedValue().getBody().contains("There is no discussion done on this merge request")) {
                        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(getFilePathToNavigate(discussionsJList));
                        new OpenFileDescriptor(GitMRDialog.project, file, getLogicalLine(discussionsJList), 0).navigate(true);
                    }
                }
        );
    }

    private Integer getLogicalLine(JList<GitLabUserNotesModel> discussionsJList) {
        return Integer.parseInt(discussionModelList.get(discussionsJList.getSelectedIndex()).getPosition().getNew_line());
    }

    @NotNull
    private String getFilePathToNavigate(JList<GitLabUserNotesModel> discussionsJList) {
        return GitMRDialog.project.getBasePath() + "/" + discussionModelList.get(discussionsJList.getSelectedIndex()).getPosition().getNew_path();
    }*/

    @NotNull
    private JLabel makeJLabel() {
        return ProvideSwingComponentsUtilsKt.makeJLabelComponent("Discussions on Merge Request", "DiscussionLabel");
    }

   /* private JList makeJListForDiscussion() {
        JList<GitLabUserNotesModel> jList = new JList<>(discussionModelList);
        jList.setVisibleRowCount(4);
        jList.setFixedCellHeight(20);
        jList.setFixedCellWidth(600);
        jList.setName("DiscussionList");
        return jList;
    }*/
}
