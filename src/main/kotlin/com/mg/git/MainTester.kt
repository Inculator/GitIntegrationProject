package com.mg.git

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.discussion.gitLabMergeRequests
import com.mg.git.merge.getMergeRequests

val hostUrl = "https://innersource.soprasteria.com/"
val token = System.getenv("ENV_GIT_TOKEN")
val nameSpace = "t-tool"
val projectName = "t-tool"

fun main() {
    GitConnectionProvider.getGitConnectionInstance(hostUrl, token)
    GitConnectionProvider.fetchProjectId(nameSpace, projectName)

    var listOfMergeRequest = getMergeRequests(GitConnectionProvider.projectId)
    var listOfMergeRequestsModels = gitLabMergeRequests(listOfMergeRequest)

    for (model in listOfMergeRequestsModels) {
        println(model.mergeRequest.id)
        println(model.listOfMRDiscussions.size)
    }

}