package com.mg.git.discussion

import com.mg.git.connection.MakeGitConnection
import com.mg.git.merge.MergeRequestModel
import com.mg.mergerequest.GitLabUserNotesModel
import org.gitlab.api.Pagination
import org.gitlab.api.models.GitlabMergeRequest
import org.gitlab.api.models.GitlabProject

fun gitLabMergeRequests(listOfMergeRequest: List<GitlabMergeRequest>): ArrayList<MergeRequestModel> {
    var listOfMergeRequestsModels = ArrayList<MergeRequestModel>()
    for (gitlabMergeRequest in listOfMergeRequest) {
        var discussionsList = getMRDiscussions(gitlabMergeRequest)
        var mergeRequestModel = MergeRequestModel(gitlabMergeRequest, discussionsList)
        listOfMergeRequestsModels.add(mergeRequestModel)
    }
    return listOfMergeRequestsModels
}

fun getMRDiscussions(mergeRequest: GitlabMergeRequest): List<GitLabUserNotesModel> {
    val tailUrl = GitlabProject.URL + "/" + mergeRequest.projectId +
            GitlabMergeRequest.URL + "/" + mergeRequest.iid +
            GitLabUserNotesModel.URL + Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).toString()
    return MakeGitConnection.gitlabAPI.retrieve().getAll(tailUrl, Array<GitLabUserNotesModel>::class.java)
        .filter { note -> note.type == "DiffNote" && !note.isResolved }
}