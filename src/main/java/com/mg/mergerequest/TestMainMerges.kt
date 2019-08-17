package com.mg.mergerequest

import com.mg.git.connection.MakeGitConnection
import com.mg.git.connection.getProjectId
import com.mg.git.discussion.gitLabMergeRequests
import com.mg.git.merge.MergeRequestModel
import com.mg.git.merge.getMergeRequests

class TestMainMerges {

    var projectId: Int = 0

    fun gitLabMergeRequestsController(
        hostUrl: String,
        nameSpace: String,
        projectName: String,
        token: String
    ): ArrayList<MergeRequestModel> {
        MakeGitConnection.getGitConnectionInstance(hostUrl, token)
        projectId = getProjectId(nameSpace, projectName)
        var listOfMergeRequest = getMergeRequests(projectId)
        var listOfMergeRequestsModels = gitLabMergeRequests(listOfMergeRequest)
        return listOfMergeRequestsModels
    }
}

fun main(args: Array<String>) {
    val token = System.getenv("ENV_GIT_TOKEN")
    var testMainMerges = TestMainMerges()
    var listOfMergeRequestsModels =
        testMainMerges.gitLabMergeRequestsController("https://innersource.soprasteria.com/", "sat", "suits", token)
    println(listOfMergeRequestsModels.size)
}
