package com.mg.git.provider

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.merge.MergeRequestModel
import org.gitlab.api.models.GitlabMergeRequest

interface GitIntegrationProvider {

    fun initializeGitConnection(hostUrl: String, nameSpace: String, projectName: String, token: String) {
        GitConnectionProvider.createGitConnectionInstance(hostUrl, token)
        GitConnectionProvider.fetchProjectId(nameSpace, projectName)
    }

    fun getAllMergeRequests(): List<GitlabMergeRequest>

    fun getMergeRequestsWithDiscussions(mergeRequest: GitlabMergeRequest): MergeRequestModel
}