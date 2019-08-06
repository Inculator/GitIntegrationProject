package com.mg.git.merge

import com.mg.git.connection.MakeGitConnection
import org.gitlab.api.models.GitlabMergeRequest

fun getMergeRequests(projectId: Int?): List<GitlabMergeRequest> = MakeGitConnection.gitlabAPI.getOpenMergeRequests(projectId)