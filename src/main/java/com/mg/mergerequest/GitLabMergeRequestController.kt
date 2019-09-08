package com.mg.mergerequest

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.discussion.gitLabMergeRequests
import com.mg.git.merge.MergeRequestModel
import com.mg.git.merge.getMergeRequests

class GitLabMergeRequestController {

    fun getMergeRequests(
        hostUrl: String,
        nameSpace: String,
        projectName: String,
        token: String
    ): ArrayList<MergeRequestModel> {
        GitConnectionProvider.getGitConnectionInstance(hostUrl, token)
        GitConnectionProvider.fetchProjectId(nameSpace, projectName)
        var listOfMergeRequest = getMergeRequests(GitConnectionProvider.projectId)
        return gitLabMergeRequests(listOfMergeRequest)
    }
}

fun main(args: Array<String>) {
    val token = System.getenv("ENV_GIT_TOKEN")
    var testMainMerges = GitLabMergeRequestController()
    var listOfMergeRequestsModels =
        testMainMerges.getMergeRequests("https://innersource.soprasteria.com/", "sat", "suits", token)
    println(listOfMergeRequestsModels.size)
}
