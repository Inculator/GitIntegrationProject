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
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class GitMRDialog extends MessageDialog {

    private JPanel panelWrapper;
    private static String[] optionsUser = {};
    private String currentBranch = "master";
    public static Project project;
    List<MergeRequestModel> myMergeRequestModelList;

    public GitMRDialog(boolean canBeParent) {
        super(project, "Merge Request Handler", "Merge Requests", optionsUser, 1, null, canBeParent);
    }

    @Override
    protected JComponent createCenterPanel() {
        super.createCenterPanel();
        panelWrapper = new JPanel();
        panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
        panelWrapper.add(Box.createHorizontalStrut(20));
        HostURLModel hostURLModel = getHostURLModel();
        myMergeRequestModelList = new TestMainMerges().
                gitLabMergeRequestsController(hostURLModel.getHost(), hostURLModel.getAlias(), hostURLModel.getProjectName(), System.getenv("ENV_GIT_TOKEN"));
        addComponentsToPanel();
        return panelWrapper;
    }

    private void addComponentsToPanel() {
        myMergeRequestModelList.forEach(mergeRequestModel -> {
            final DefaultListModel<String> l1 = new DefaultListModel<>();
            if (mergeRequestModel.getMergeRequest().getSourceBranch().equalsIgnoreCase(currentBranch)) {
                l1.addElement(mergeRequestModel.getMergeRequest().getTitle());
                if (l1.size() == 0)
                    l1.addElement("No Merge Request for branch : " + currentBranch);
                final JList<String> mergeRequestsJList = ProvideSwingComponentsUtilsKt.makeJListComponent(l1, "");
                addListActionListener(mergeRequestsJList);
                ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", "");
                panelWrapper.add(ProvideSwingComponentsUtilsKt.makeJLabelComponent(" Merge Requests ", ""));
                panelWrapper.add(mergeRequestsJList);
            }
        });
    }

    private void addListActionListener(JList<String> mergeRequestsList) {
        mergeRequestsList.addListSelectionListener(l ->
                new DiscussionPanel(panelWrapper, mergeRequestsList.getSelectedValue(), myMergeRequestModelList)
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
