package com.mg.plugin.dialog;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.messages.MessageDialog;
import com.mg.git.provider.GitIntegrationProvider;
import com.mg.git.utils.GitConnectionUtils;
import com.mg.git.utils.HostURLModel;
import com.mg.git.provider.GitLabMergeRequestProvider;
import com.mg.plugin.ProvideSwingComponentsUtilsKt;
import org.gitlab.api.models.GitlabMergeRequest;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class GitMRDialog extends MessageDialog {

    private PanelWrapperCreator panelWrapperCreator;
    private static String[] optionsUser = {};
    private String currentBranch = "master";
    public static Project project;
    private List<GitlabMergeRequest> gitlabMergeRequests;

    public GitMRDialog(boolean canBeParent) {
        super(project, "Merge Request Handler", "Merge Requests", optionsUser, 1, null, canBeParent);
    }

    @Override
    protected JComponent createCenterPanel() {
        super.createCenterPanel();
        panelWrapperCreator = new PanelWrapperCreator();
        GitIntegrationProvider gitLabMergeRequestController = new GitLabMergeRequestProvider();
        HostURLModel hostURLModel = getHostURLModel();
        gitLabMergeRequestController.initializeGitConnection(hostURLModel.getHost(), hostURLModel.getAlias(), hostURLModel.getProjectName(), System.getenv("ENV_GIT_TOKEN"));
        gitlabMergeRequests = gitLabMergeRequestController.getAllMergeRequests();
        addComponentsToPanel();
        return panelWrapperCreator.getPanelWrapper();
    }

    private void addComponentsToPanel() {
        Optional<GitlabMergeRequest> mergeRequestForSelectedBranch = gitlabMergeRequests.stream().filter(mergeRequest -> mergeRequest.getSourceBranch().equalsIgnoreCase(currentBranch)).findFirst();

        if (!mergeRequestForSelectedBranch.isPresent())
            panelWrapperCreator.addComponentToPanelWrapper(ProvideSwingComponentsUtilsKt.makeJLabelComponent("No Merge Request for branch : " + currentBranch, ""));
        else {
            final DefaultListModel<String> mergeRequestModelJList = new DefaultListModel<>();
            mergeRequestModelJList.addElement(mergeRequestForSelectedBranch.get().getTitle());
            final JList<String> mergeRequestsJList = ProvideSwingComponentsUtilsKt.makeJListComponent(mergeRequestModelJList, "");
            addListActionListener(mergeRequestsJList, mergeRequestForSelectedBranch.get());
            ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", "");
            panelWrapperCreator.addComponentToPanelWrapper(ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", ""));
            panelWrapperCreator.addComponentToPanelWrapper(mergeRequestsJList);
        }
    }

    private void addListActionListener(JList<String> mergeRequestsList, GitlabMergeRequest mergeRequestForSelectedBranch) {
        mergeRequestsList.addListSelectionListener(l ->
                new DiscussionPanel(panelWrapperCreator.getPanelWrapper(), mergeRequestForSelectedBranch, this)
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
            return GitConnectionUtils.Companion.consultProjectGitURL(myRepo.getVcs().getProject().getBasePath());
        }
        return new HostURLModel("", "", "");
    }

}
