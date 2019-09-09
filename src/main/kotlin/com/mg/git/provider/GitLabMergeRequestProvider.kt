package com.mg.git.provider

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.discussion.gitLabMergeRequestsWithDiscussions
import com.mg.git.merge.MergeRequestModel
import com.mg.git.merge.getAllMergeRequests
import org.gitlab.api.models.GitlabMergeRequest

class GitLabMergeRequestProvider : GitIntegrationProvider {

    override fun getAllMergeRequests(): List<GitlabMergeRequest> {
        return getAllMergeRequests(GitConnectionProvider.projectId)
    }

    override fun getMergeRequestsWithDiscussions(mergeRequest: GitlabMergeRequest): MergeRequestModel {
        return gitLabMergeRequestsWithDiscussions(mergeRequest)
    }
}
