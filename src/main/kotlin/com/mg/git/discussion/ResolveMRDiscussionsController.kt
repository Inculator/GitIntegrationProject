package com.mg.git.discussion

import com.mg.git.connection.MakeGitConnection
import com.mg.mergerequest.GitLabDiscussionsModel
import org.gitlab.api.Pagination
import org.gitlab.api.http.GitlabHTTPRequestor
import org.gitlab.api.models.GitlabMergeRequest
import org.gitlab.api.models.GitlabProject

class ResolveMRDiscussionsController {

    private val resolved = "&resolved="

    fun resolveDiscussion(
        discussionId: String,
        projectId: Int,
        mergeRequest: GitlabMergeRequest,
        resolveFlag: Boolean
    ): Boolean? {
        val tailUrl = GitlabProject.URL + "/" + mergeRequest.projectId +
                GitlabMergeRequest.URL + "/" + mergeRequest.iid +
                GitLabDiscussionsModel.URL + "/" + discussionId + Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).toString() + resolved + resolveFlag
        var httpRequestor: GitlabHTTPRequestor = MakeGitConnection.gitlabAPI.retrieve()
        httpRequestor = httpRequestor.method("PUT")
        var model: GitLabDiscussionsModel? = httpRequestor.to(tailUrl, GitLabDiscussionsModel::class.java)
        return model?.notes?.get(0)?.isResolved!!
    }

}