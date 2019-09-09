package com.mg.git

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.discussion.gitLabMergeRequestsWithDiscussions
import com.mg.git.merge.getAllMergeRequests

val hostUrl = "https://innersource.soprasteria.com/"
val token = System.getenv("ENV_GIT_TOKEN")
val nameSpace = "t-tool"
val projectName = "t-tool"

fun main() {
    GitConnectionProvider.createGitConnectionInstance(hostUrl, token)
    GitConnectionProvider.fetchProjectId(nameSpace, projectName)

    var listOfMergeRequest = getAllMergeRequests(GitConnectionProvider.projectId)
    var listOfMergeRequestsModels = gitLabMergeRequestsWithDiscussions(listOfMergeRequest.get(0))

    println(listOfMergeRequestsModels.mergeRequest.assignee)
    println(listOfMergeRequestsModels.listOfMRDiscussions.size)

}