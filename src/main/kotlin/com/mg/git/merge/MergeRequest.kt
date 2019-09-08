package com.mg.git.merge

import com.mg.git.connection.GitConnectionProvider
import org.gitlab.api.models.GitlabMergeRequest

fun getMergeRequests(projectId: Int?): List<GitlabMergeRequest> = GitConnectionProvider.gitlabAPI.getOpenMergeRequests(projectId)