package com.mg.plugin.dialog;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.messages.MessageDialog;
import com.mg.git.merge.MergeRequestModel;
import com.mg.git.utils.GitConnectionUtils;
import com.mg.git.utils.HostURLModel;
import com.mg.mergerequest.TestMainMerges;
import com.mg.plugin.ProvideSwingComponentsUtilsKt;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GitMRDialog extends MessageDialog {

    private PanelWrapperCreator panelWrapperCreator;
    private static String[] optionsUser = {};
    private String currentBranch = "master";
    public static Project project;
    private List<MergeRequestModel> myMergeRequestModelList;
    private List<MergeRequestModel> myMergeRequestModelListForCurrentBranch;

    public GitMRDialog(boolean canBeParent) {
        super(project, "Merge Request Handler", "Merge Requests", optionsUser, 1, null, canBeParent);
    }

    @Override
    protected JComponent createCenterPanel() {
        super.createCenterPanel();
        panelWrapperCreator = new PanelWrapperCreator();
        HostURLModel hostURLModel = getHostURLModel();
        myMergeRequestModelList = new TestMainMerges().
                gitLabMergeRequestsController(hostURLModel.getHost(), hostURLModel.getAlias(), hostURLModel.getProjectName(), System.getenv("ENV_GIT_TOKEN"));
        addComponentsToPanel();
        return panelWrapperCreator.getPanelWrapper();
    }

    private void addComponentsToPanel() {
        myMergeRequestModelListForCurrentBranch = myMergeRequestModelList.stream().filter(mergeRequestModel -> mergeRequestModel.getMergeRequest().getSourceBranch().equalsIgnoreCase(currentBranch))
                .collect(Collectors.toList());

        if (myMergeRequestModelListForCurrentBranch.isEmpty())
            panelWrapperCreator.addComponentToPanelWrapper(ProvideSwingComponentsUtilsKt.makeJLabelComponent("No Merge Request for branch : " + currentBranch, ""));
        else
            myMergeRequestModelListForCurrentBranch.forEach(mergeRequestModel -> {
                final DefaultListModel<String> l1 = new DefaultListModel<>();
                l1.addElement(mergeRequestModel.getMergeRequest().getTitle());
                final JList<String> mergeRequestsJList = ProvideSwingComponentsUtilsKt.makeJListComponent(l1, "");
                addListActionListener(mergeRequestsJList);
                ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", "");
                panelWrapperCreator.addComponentToPanelWrapper(ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", ""));
                panelWrapperCreator.addComponentToPanelWrapper(mergeRequestsJList);
            });
    }

    private void addListActionListener(JList<String> mergeRequestsList) {
        mergeRequestsList.addListSelectionListener(l ->
                new DiscussionPanel(panelWrapperCreator.getPanelWrapper(), mergeRequestsList.getSelectedValue(), myMergeRequestModelList)
                        .createDiscussionPanel()
        );
    }

    private HostURLModel getHostURLModel() {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        Repository myRepo = null;
        Optional<Repository> repoOptional = vcsRepositoryManager.getRepositories().stream().filter(repo -> repo.getProject() == project).findFirst();
        if (repoOptional.isPresent()) {
            myRepo = repoOptional.get();
            currentBranch = myRepo.getCurrentBranchName();
            return GitConnectionUtils.Companion.consultGitURL(myRepo.getVcs().getProject().getBasePath());
        }
        return new HostURLModel("", "", "");
    }

}
