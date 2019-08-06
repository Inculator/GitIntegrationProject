package com.mg.git.merge

import com.mg.git.connection.MakeGitConnection

fun getMergeRequests(projectId: Int)  = MakeGitConnection.gitlabAPI.getOpenMergeRequests(projectId)