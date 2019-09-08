package com.mg.git.discussion

import com.mg.git.connection.GitConnectionProvider
import com.mg.git.merge.MergeRequestModel
import com.mg.mergerequest.GitLabDiscussionsModel
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

fun getMRDiscussions(mergeRequest: GitlabMergeRequest): List<GitLabDiscussionsModel> {
    val tailUrl = GitlabProject.URL + "/" + mergeRequest.projectId +
            GitlabMergeRequest.URL + "/" + mergeRequest.iid +
            GitLabDiscussionsModel.URL + Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).toString()

    var listOfGitLabDiscussionsModel = GitConnectionProvider.gitlabAPI.retrieve()
        .getAll(tailUrl, Array<GitLabDiscussionsModel>::class.java)
        .filter { models -> models.notes.get(0).type == "DiffNote" && !models.notes.get(0).isResolved }

    listOfGitLabDiscussionsModel.forEach { model-> model.notes.forEach { note-> note.discussionId = model.id }}
    return listOfGitLabDiscussionsModel
}