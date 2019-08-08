package com.mg.git

import com.mg.git.connection.MakeGitConnection
import com.mg.git.connection.getProjectId
import com.mg.git.discussion.gitLabMergeRequests
import com.mg.git.merge.getMergeRequests

var projectId: Int = 0
val hostUrl = "https://innersource.soprasteria.com/"
val token = System.getenv("ENV_GIT_TOKEN")
val nameSpace = "t-tool"
val projectName = "t-tool"

fun main() {
    MakeGitConnection.getGitConnectionInstance(hostUrl, token)
    projectId = getProjectId(nameSpace, projectName)

    var listOfMergeRequest = getMergeRequests(projectId)
    var listOfMergeRequestsModels = gitLabMergeRequests(listOfMergeRequest)

    for (model in listOfMergeRequestsModels) {
        println(model.mergeRequest.id)
        println(model.listOfMRDiscussions.size)
    }

}