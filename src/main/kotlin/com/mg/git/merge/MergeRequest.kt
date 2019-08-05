package com.mg.git.merge

import com.mg.git.connection.MakeGitConnection
import com.mg.git.connection.getProjectId

fun getMergeRequests(projectId: Int)  = MakeGitConnection.gitlabAPI.getOpenMergeRequests(projectId)